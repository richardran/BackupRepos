package com.richardran.passingclouds;

import java.util.TreeMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ScaledBitmapArray {
	TreeMap<Integer, Bitmap> bmps = new TreeMap<Integer, Bitmap>();
	 
	public void setBitmap(Bitmap bmp, int max, int min) {
		int osz = bmp.getWidth();

		for(int i = max; i>= min; i--) {
			Matrix m = new Matrix();
			float sx = ((float)i)/osz;
			m.postScale(sx, sx);
			Bitmap sbmp = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
			bmps.put(i, sbmp);
			Canvas canvas = new Canvas(sbmp);
			canvas.drawBitmap(bmp, m, null);
			canvas = null;
			m = null;
		}
	}
	
	/*
	public void draw(Canvas canvas) {
		for (Map.Entry<Integer,Bitmap> entry : bmps.entrySet()) {
	        Bitmap value = entry.getValue();
	        Integer key = entry.getKey();
	        canvas.drawBitmap(value, 20* key.floatValue(),0, null);
		}	
	}
	*/
	
	private Integer lookupKey(int key){
		Integer before = bmps.floorKey(key);
		Integer after = bmps.ceilingKey(key);
		if (before == null) return after;
		if (after == null) return before;
		return (key - before < after - key || after - key < 0) && key - before > 0 ? before : after;
	}
	
	public Bitmap lookup(int key) {
		Integer k = lookupKey(key);
		return bmps.get(k);
	}
 }
