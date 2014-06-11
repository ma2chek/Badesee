package de.metamob.badesee.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.metamob.badesee.R;
import de.metamob.badesee.model.Badestelle;
import de.metamob.badesee.view.LinearLayoutView;

/**
 * @author Felix Matuschek MatNr. 798423, Hans-Christian Hanke MatNr. 798152
 * 
 *         Implements a ListAdapter to Display custom behavior ListItems
 */
public class BadestellenAdapter extends ArrayAdapter<Badestelle> {

	private Context context;
	private Badestelle[] values;
	private int mItemSelected = -1;
	private LinearLayoutView actualRowView;

	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public BadestellenAdapter(Context context, int resource,
			List<Badestelle> objects) {
		super(context, resource, objects);
		this.context = context;

		this.values = new Badestelle[objects.size()];
		for (int i = 0; i < objects.size(); i++) {
			this.values[i] = objects.get(i);
		}
	}

	/**
	 * @param position
	 */
	public void setItemSelected(int position) {
		mItemSelected = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayoutView rowView = (LinearLayoutView) inflater.inflate(
				R.layout.layout_listrow, parent, false);
		rowView.setText(values[position].getName());
		rowView.setWasserqualitaet(values[position].getWasserqualitaet());
		rowView.setVisibilityState(mItemSelected == position);
		this.actualRowView = (mItemSelected == position) ? rowView
				: this.actualRowView;
		return rowView;
	}

	/**
	 * @return
	 */
	public LinearLayoutView getActualRowView() {
		return actualRowView;
	}
}
