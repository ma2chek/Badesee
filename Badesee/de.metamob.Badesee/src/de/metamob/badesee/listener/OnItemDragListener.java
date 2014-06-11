package de.metamob.badesee.listener;

import android.view.View;

/**
 * @author Felix Matuschek MatNr. 798423, Hans-Christian Hanke MatNr. 798152
 * 
 *         Custom Listener Interface for ListItem-Drag Events
 */
public interface OnItemDragListener {
	/**
	 * Listener for dragged ListViewItem
	 * 
	 * @param position
	 *            Position of the Item inside the List
	 * @param hSwipe
	 *            Amount of horizontal swipe (based on ScreenWidth)
	 * @param view
	 *            View of the dragged Item
	 */
	public void onItemDrag(int position, float hSwipe, View view);

	/**
	 * Listener for clicked ListViewItem
	 * 
	 * @param position
	 *            Position of the Item inside the List
	 * @param view
	 *            View of the dragged Item
	 */
	public void onItemDragEnded(int position, View view);
}
