package de.metamob.badesee;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class DetailActivity extends Activity {
	Badestelle aktuelleBadestelle;
	Intent mainIntent; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainIntent = new Intent(this, MainActivity.class);
		
		setContentView(R.layout.activity_detail);
		
		
		final TableRow l = (TableRow) findViewById(R.id.tableRow1);
		l.setClickable(true);
		l.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	startActivity(mainIntent);
				finish();
				overridePendingTransition (R.anim.open_main, R.anim.close_next);
			}
		   	
		});
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		setData();
	}
	
	private void setData(){
		Bundle extras = getIntent().getExtras();
		
		while (aktuelleBadestelle == null){
			super.onStart();
			
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
			
			aktuelleBadestelle = (Badestelle)extras.getSerializable("badestelle");
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(aktuelleBadestelle.getCoordinates(), 12));
			
			TextView profil = (TextView) findViewById(R.id.TextView_profil);
		
			TextView badestelle = (TextView) findViewById(R.id.TextView_badestelle);
			TextView bezirk = (TextView) findViewById(R.id.TextView_bezirk);
			TextView datum = (TextView) findViewById(R.id.TextView_datum);
			TextView ecoli = (TextView) findViewById(R.id.TextView_ecoli);
			TextView enterokokken = (TextView) findViewById(R.id.TextView_enterokokken);
			TextView sichttiefe = (TextView) findViewById(R.id.TextView_sichttiefe);
			
			ImageView wasserqualitaet1 = (ImageView) findViewById(R.id.imageView_wasserqualitaet1);
			ImageView wasserqualitaet2 = (ImageView) findViewById(R.id.ImageView_wasserqualitaet2);
			
			profil.setText(aktuelleBadestelle.getProfil());		
			badestelle.setText(aktuelleBadestelle.getName());
			bezirk.setText(aktuelleBadestelle.getBezirk());
			datum.setText("vom "+aktuelleBadestelle.getDatum());
			ecoli.setText(aktuelleBadestelle.getEcoli());
			enterokokken.setText(aktuelleBadestelle.getEnterokokken());
			sichttiefe.setText(aktuelleBadestelle.getSichttiefe()+" cm");
			
			int id = this.getResources().getIdentifier(aktuelleBadestelle.getWasserqualitaet(), "drawable", "de.metamob.badesee");
			wasserqualitaet1.setImageResource(id);   
			wasserqualitaet2.setImageResource(id);   
			
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(aktuelleBadestelle.getCoordinates(), 15), 2000, null); 
			
			map.addMarker(new MarkerOptions()
	          .position( aktuelleBadestelle.getCoordinates())
	          .title(aktuelleBadestelle.getName())
	          .icon(BitmapDescriptorFactory.defaultMarker(aktuelleBadestelle.getMarker())));
		}		
	}
}
