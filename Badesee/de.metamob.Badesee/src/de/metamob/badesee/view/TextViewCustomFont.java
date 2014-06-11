package de.metamob.badesee.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import de.metamob.badesee.R;

/**
 * @author Felix Matuschek MatNr. 798423, Hans-Christian Hanke MatNr. 798152
 * 
 *         Implements user defined Fonts for TextViews
 */
public class TextViewCustomFont extends TextView {

	public TextViewCustomFont(Context context) {
		super(context);
	}

	public TextViewCustomFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public TextViewCustomFont(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	/**
	 * @param ctx
	 * @param attrs
	 */
	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray a = ctx.obtainStyledAttributes(attrs,
				R.styleable.TextViewCustomFont);
		String customFont = a
				.getString(R.styleable.TextViewCustomFont_customFont);
		setCustomFont(ctx, customFont);
		a.recycle();
	}

	/**
	 * @param ctx
	 * @param asset
	 *            Filename of the Font
	 * @return
	 */
	public boolean setCustomFont(Context ctx, String asset) {
		Typeface tf = null;
		try {
			tf = Typeface.createFromAsset(ctx.getAssets(), asset);
		} catch (Exception e) {
			return false;
		}

		setTypeface(tf);
		return true;
	}
}