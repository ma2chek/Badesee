package de.metamob.badesee;

import java.util.ArrayList;
import java.util.List;

import de.metamob.badesee.Badestelle.EWasserqualitaet;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		final List <Badestelle> badestellen = new ArrayList<Badestelle>();
		badestellen.add(new Badestelle("name1", "profil", "bezirk", "datum",
			"sichttiefe", "enterokokken", "ecoli",
			Badestelle.EWasserqualitaet.gruen, "profilurl"));
		
		badestellen.add(new Badestelle("name2", "profil", "bezirk", "datum",
				"sichttiefe", "enterokokken", "ecoli",
				Badestelle.EWasserqualitaet.gelb, "profilurl"));
		
		badestellen.add(new Badestelle("name3", "profil", "bezirk", "datum",
				"sichttiefe", "enterokokken", "ecoli",
				Badestelle.EWasserqualitaet.rot, "profilurl"));
		
		final ListView l = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<Badestelle> badestellenAdapter = new BadestellenAdapter(this, android.R.layout.simple_list_item_1, badestellen);
		l.setAdapter(badestellenAdapter);
		l.setOnItemClickListener(new OnItemClickListener() {

			@Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                final int position, long id) {

	          Badestelle bs = badestellen.get(position);
	          System.out.println("select "+ bs.getName()+" // "+bs.getWasserqualitaet()) ;
	        }
	    });
		
		l.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view,
	                final int position, long id) {

	          Badestelle bs = badestellen.get(position);
	          System.out.println("LOOOONG select "+ bs.getName()+" // "+bs.getWasserqualitaet()) ;
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
}
