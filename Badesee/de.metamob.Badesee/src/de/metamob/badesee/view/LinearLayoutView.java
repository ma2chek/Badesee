package de.metamob.badesee.view;

import de.metamob.badesee.R;
import de.metamob.badesee.R.color;
import de.metamob.badesee.R.id;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearLayoutView extends LinearLayout{
	
	private TextView textView;
	private Typeface custom_font;
	private ImageView arrow;
	private TextView hintDetail;
	private ImageView imageView;
	
	private Context context;
	private boolean initDone = false;
	
	private int grey_selected = 51;
	private int grey_notSelected = 255;


	public LinearLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	private void init(){
		if (!initDone){
			initDone = true;
			arrow = (ImageView) findViewById(R.id.arrow);
		    hintDetail = (TextView) findViewById(R.id.mainActivity_hintDetail);
		    
		    textView = (TextView) findViewById(R.id.entry);
		    custom_font = Typeface.createFromAsset(context.getAssets(),"fonts/titilliumweb-extralight-webfont.ttf");
		    textView.setTypeface(custom_font);
		    textView.setTextSize(20);
		    textView.setGravity(Gravity.CENTER_VERTICAL);
		    
		    hintDetail.setTypeface(custom_font);
		    hintDetail.setTextSize(14);
		    
		    imageView = (ImageView) findViewById(R.id.icon);
		}
	}
	
	public void setText(String text){
		init();
		textView.setText(text);
	}
	
	public void setWasserqualitaet(String wasserqualitaet){
		init();
		int id = getResources().getIdentifier(wasserqualitaet, "drawable", "de.metamob.badesee");
	    imageView.setImageResource(id);  
	}
	
	public void setState(boolean state){
		init();
		if(state){    	
	    	setBackgroundColor(getResources().getColor( R.color.selected_color));
	    	textView.setTextColor(getResources().getColor(R.color.default_color));
	    	refreshDrawableState();
	    	arrow.setVisibility(View.VISIBLE);
	    	hintDetail.setVisibility(View.VISIBLE);
	    }else{
	    	setBackgroundColor(getResources().getColor(R.color.default_color));
	    	textView.setTextColor(getResources().getColor(R.color.selected_color));
	    	refreshDrawableState();
	    }
	}
	
	public void setStepplessVisibility (float visibility){
		init();
		int colorBackground = (int)( visibility * grey_selected + (1f-visibility) * grey_notSelected);
		int colorForeground = (int)( visibility * grey_notSelected + (1f-visibility) * grey_selected);
				
		setBackgroundColor( Color.rgb(colorBackground, colorBackground, colorBackground)  );
		textView.setTextColor(Color.rgb(colorForeground, colorForeground, colorForeground) );
	}

}
