package de.metamob.badesee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
	private GoogleMap googleMap;
	
	Intent detailIntent; // = new Intent(this, DetailActivity.class);
	ListView mainLayout;
	Badestelle bs;
	
	@Override
	protected void onStart(){
		super.onStart();
		mainLayout = (ListView) findViewById(R.id.listView1);
		mainLayout.setOnTouchListener(new OnSwipeTouchListener(this){
			public void onSwipeLeft() {
		        System.out.println("JIPPE");
		        detailIntent.putExtra("badestelle", bs);
		          startActivity(detailIntent);
		          overridePendingTransition (R.anim.open_next, R.anim.close_main);
		    }
		});
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
		initilizeMap();
		parseJSON();
		
		final ListView l = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<Badestelle> badestellenAdapter = new BadestellenAdapter(this, android.R.layout.simple_list_item_1, badestellen);
		l.setAdapter(badestellenAdapter);
		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                final int position, long id) {

	          bs = badestellen.get(position);
	          view.setSelected(true);
	          System.out.println("select "+ bs.getName()+" // "+bs.getWasserqualitaet()) ;
	        }
	    });
		
		
		l.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view,
	                final int position, long id) {

	          bs = badestellen.get(position);
	          System.out.println("LOOOONG select "+ bs.getName()+" // "+bs.getWasserqualitaet()) ;
	          detailIntent.putExtra("badestelle", bs);
	          startActivity(detailIntent);
	          overridePendingTransition (R.anim.open_next, R.anim.close_main);
	          
	          return false;
	        }
	    });		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
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
			JSONObject jObject = new JSONObject(readJSON("Badewasser_latin9.json"));
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
	        json = new String(buffer);//, "UTF-8");
	        //System.out.println("json "+json);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;
	}
	
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
