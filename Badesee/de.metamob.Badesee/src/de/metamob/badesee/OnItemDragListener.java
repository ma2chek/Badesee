package de.metamob.badesee;

import android.view.View;

public interface OnItemDragListener {
	
	public void onItemDrag(int position, float hSwipe, View view);
	public void onItemDragEnded(int position, View view);
}
