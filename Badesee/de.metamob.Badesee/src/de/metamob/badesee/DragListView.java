package de.metamob.badesee;

import android.content.Context;   
import android.graphics.Point;
import android.util.AttributeSet;  
import android.view.Display;
import android.view.MotionEvent;  
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;  
import android.widget.ListView;  
  
public class DragListView extends ListView {  
       
    private int dragPosition;
         
    
    private OnItemDragListener onItemDragListener;
    private float screenWidth;
    
    private int offsetX;
    private View row;
    
    private float sensitivity = 0.1f;
    
    public DragListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);        
        this.screenWidth = size.x;
    }  
      
    public void setOnItemDragListener(OnItemDragListener listener){
    	this.onItemDragListener = listener;
    }    
    
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
        if(ev.getAction()==MotionEvent.ACTION_DOWN){  
        	 
        	
            int x = (int)ev.getX();  
            int y = (int)ev.getY();          
            
            
            
            dragPosition = pointToPosition(x, y);  
            
            ViewGroup itemView = (ViewGroup) getChildAt(dragPosition-getFirstVisiblePosition());  
        	row = itemView.findViewById(R.id.zeile); 
        	
            offsetX = x;            
            System.out.println("POSITION "+dragPosition);         
         }  
         return super.onInterceptTouchEvent(ev);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
            int action = ev.getAction();  
            if (action == MotionEvent.ACTION_MOVE){
                float moveX = ((float)ev.getX() -offsetX) / this.screenWidth;  
                
                //onDrag(moveX);  
                    
                
                if (this.onItemDragListener != null){
                	System.out.println("mode "+moveX);
                	this.onItemDragListener.onItemDrag(dragPosition, moveX, row);
                }
            } else if (action == MotionEvent.ACTION_UP){
            	if (this.onItemDragListener != null){
                	this.onItemDragListener.onItemDragEnded(dragPosition, row);
                }
            }
             
        return super.onTouchEvent(ev);  
    }  
}  