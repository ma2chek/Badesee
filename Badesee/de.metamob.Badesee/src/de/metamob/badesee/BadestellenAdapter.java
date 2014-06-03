package de.metamob.badesee;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BadestellenAdapter extends ArrayAdapter<Badestelle> {

	private Context context;
	private Badestelle [] values;
	private int mItemSelected = -1 ;
	
	public BadestellenAdapter(Context context, int resource,
			List<Badestelle> objects) {
		super(context, resource, objects);
		this.context = context;
		
		this.values = new Badestelle[objects.size()];
		for (int i = 0; i<objects.size(); i++){
			this.values[i] = objects.get(i);
		}
	}
	
	public void setItemSelected(int position){
		mItemSelected=position;
		System.out.println("Selected!!!!!!");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.zeilenlayout, parent, false);
	   

	    
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    Typeface custom_font = Typeface.createFromAsset(context.getAssets(),"fonts/titilliumweb-extralight-webfont.ttf");
	    textView.setTypeface(custom_font);
	    textView.setTextSize(20);
	    textView.setGravity(Gravity.CENTER_VERTICAL);

	    
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(values[position].getName());
	    
	    int id = this.context.getResources().getIdentifier(values[position].getWasserqualitaet(), "drawable", "de.metamob.badesee");
	    System.out.println("UPDATE");
	    imageView.setImageResource(id);   
	    //imageView.setImageResource(R.drawable.gelb);   
	    System.out.println("TARGET "+mItemSelected+" // "+position);
	    if(mItemSelected==position){
	    	
	    	//rowView.setBackgroundColor(context.getResources().getColor( R.color.selected_color));
	    	//rowView.setBackgroundResource(R.layout.listview);
	    //	rowView.setActivated(true);
	    //	
	    	rowView.setBackgroundColor(context.getResources().getColor( R.color.selected_color));
	    	rowView.refreshDrawableState();
	    	System.out.println("ERKANNT");
	    }else{
	    	rowView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
	    	rowView.refreshDrawableState();
	    //	rowView.setSelected(false);
	    //	rowView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
	    }
	    return rowView;
	}
}
