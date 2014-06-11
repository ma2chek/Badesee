package de.metamob.badesee;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.metamob.badesee.adapter.BadestellenAdapter;
import de.metamob.badesee.listener.OnItemDragListener;
import de.metamob.badesee.model.Badestelle;
import de.metamob.badesee.view.DragListView;
import de.metamob.badesee.view.LinearLayoutView;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	final List <Badestelle> badestellen = new ArrayList<Badestelle>();
	private GoogleMap map;
	private int actualPosition = -1;
	private boolean hSwipeDone = true;
	private float horizontalSwipeGate = 0.3f;
	
	DragListView l;	
	Intent detailIntent; 
	ListView mainLayout;
	List <Marker> markers = new ArrayList<Marker>();
	Badestelle bs;
	
	@Override
	protected void onStart(){
		super.onStart();
		mainLayout = (ListView) findViewById(R.id.listView1);
		hSwipeDone = true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		detailIntent = new Intent(this, DetailActivity.class);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		parseJSON();
		initilizeMap();
		
		l  = (DragListView) findViewById(R.id.listView1);
		ArrayAdapter<Badestelle> badestellenAdapter = new BadestellenAdapter(this, android.R.layout.simple_list_item_1, badestellen);
		l.setAdapter(badestellenAdapter);
		
		l.setOnItemDragListener(new OnItemDragListener() {
			
			@Override
			public void onItemDrag(int position, float hSwipe, View view) {
								
				if (hSwipe<0 && hSwipeDone && position != actualPosition){					
					LinearLayoutView linearView = (LinearLayoutView)view;
					float stepAmount = (hSwipe/-horizontalSwipeGate);		
					
					linearView.setStepplessVisibility(stepAmount);
					
					if (actualPosition >= 0){
						((BadestellenAdapter) l.getAdapter()).getActualRowView().setStepplessVisibility(1-stepAmount);
						
						LatLng interpolatedCoordinate = new LatLng( (1-stepAmount) * badestellen.get(actualPosition).getCoordinates().latitude 
																	+ stepAmount*badestellen.get(position).getCoordinates().latitude,  
																	(1-stepAmount) * badestellen.get(actualPosition).getCoordinates().longitude 
																	+ stepAmount*badestellen.get(position).getCoordinates().longitude);					
						
						map.moveCamera(  CameraUpdateFactory.newLatLngZoom(interpolatedCoordinate, 15));
					}
					
					if (hSwipe<-horizontalSwipeGate ){						
						if (position != actualPosition){
							actualPosition = position;
						}
						
						((BadestellenAdapter) l.getAdapter()).setItemSelected( actualPosition);	  
				         l.invalidateViews();			     
						 detailIntent.putExtra("badestelle", badestellen.get(actualPosition));
					     startActivity(detailIntent);
					     overridePendingTransition (R.anim.open_next, R.anim.close_main);
					     hSwipeDone = false;
					} 
				}
			}

			@Override
			public void onItemDragEnded(int position, View view) {
				if (position == actualPosition && hSwipeDone){
					detailIntent.putExtra("badestelle", badestellen.get(actualPosition));
			        startActivity(detailIntent);
			        overridePendingTransition (R.anim.open_next, R.anim.close_main);
				} else {
					actualPosition = position;
					map.animateCamera(CameraUpdateFactory.newLatLngZoom( badestellen.get(actualPosition).getCoordinates(), 15), 2000, null); 					
				}
				
				view.setBackgroundColor(getResources().getColor( R.color.selected_color));
				((BadestellenAdapter) l.getAdapter()).setItemSelected( actualPosition);
				l.invalidateViews();
			}
		});		
	}
	
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void parseJSON(){		
		try {
			JSONObject jObject = Json.getJson("http://www.metamob.de/ema/Badewasser.json");
			jObject = (jObject==null)?new JSONObject(readJSON("Badewasser.json")):jObject;
			JSONArray jsonArray = jObject.getJSONArray("index");
			
			for (int i = 0; i < jsonArray.length(); i++) {			
		        JSONObject jsonObject = jsonArray.getJSONObject(i);
		        badestellen.add( new Badestelle( jsonObject.getString("id"), 
		        		 	jsonObject.getString("badname"), 
		        		 	jsonObject.getString("profil"), 
		        		 	jsonObject.getString("bezirk"), 
		        		 	jsonObject.getString("dat"),
		        		 	jsonObject.getString("sicht"), 
		        		 	jsonObject.getString("ente"), 
		        		 	jsonObject.getString("eco"),
		        		 	jsonObject.getString("farbe"),
		        		 	jsonObject.getString("profillink"),
		        		 	jsonObject.getString("coordinates"),
		        		 	jsonObject.getString("badestellelink"))
		        );
		     }
			 Collections.sort(badestellen);
			 
		} catch (JSONException e) {
			System.out.println("murks");
            e.printStackTrace();
        }
	}
	
	public String readJSON(String filename){		
		String json = null;
	    try {
	        InputStream is = getAssets().open(filename);
	        int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        json = new String(buffer);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;
	}
	
	private void initilizeMap() {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		if (map != null){			
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.5234051f, 13.4113999f), 10), 2000, null);
			map.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					l.invalidateViews();
					actualPosition =  markers.indexOf(marker);
					
					((BadestellenAdapter) l.getAdapter()).setItemSelected(actualPosition);
					l.smoothScrollToPosition(actualPosition);
					return false;
				}
			});				
				
	        if (map != null) {
	        	for (Badestelle thisBadestelle : badestellen){
	        		markers.add(map.addMarker(new MarkerOptions()
	  	          	.position( thisBadestelle.getCoordinates())
	  	          	.title(thisBadestelle.getName())
	  	          	.icon(BitmapDescriptorFactory.defaultMarker(thisBadestelle.getMarker()))));        		
	        	}
	        }        
		}
	}
}
