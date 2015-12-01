package com.android.research.researchproject.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.android.research.researchproject.R;


public class CustomButton extends Button {
	public CustomButton(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		this.init(attrs);
	}

	public CustomButton(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.init(attrs);
	}

	public CustomButton(final Context context) {
		super(context);
		this.init(null);
	}

	private void init(final AttributeSet attrs) {
		if (null != attrs) {
			final TypedArray a = this.getContext().obtainStyledAttributes(
					attrs, R.styleable.AirtelTextView);
			final String fontName = a
					.getString(R.styleable.AirtelTextView_fontName);
			if (null != fontName) {
				final Typeface myTypeface = Typeface.createFromAsset(this
						.getContext().getAssets(), "fonts/" + fontName);
				this.setTypeface(myTypeface);
			}
			a.recycle();
		}
	}
}