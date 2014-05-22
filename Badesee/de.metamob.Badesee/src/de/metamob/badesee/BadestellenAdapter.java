package de.metamob.badesee;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BadestellenAdapter extends ArrayAdapter<Badestelle> {

	private Context context;
	private Badestelle [] values;

	public BadestellenAdapter(Context context, int resource,
			List<Badestelle> objects) {
		super(context, resource, objects);
		this.context = context;
		
		this.values = new Badestelle[objects.size()];
		for (int i = 0; i<objects.size(); i++){
			this.values[i] = objects.get(i);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.zeilenlayout, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(values[position].getName());
	    
	    int id = this.context.getResources().getIdentifier(values[position].getWasserqualitaet(), "drawable", "de.metamob.badesee");
	    imageView.setImageResource(id);   
	    //imageView.setImageResource(R.drawable.gelb);   
	
	    return rowView;
	}
}
