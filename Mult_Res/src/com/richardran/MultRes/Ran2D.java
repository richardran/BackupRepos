package com.richardran.MultRes;

import java.util.Arrays;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ran2D {
	public boolean isCreated = false;
	public int size;
	public int[] x;
	public int[] y;
	public int[] v;
	public int xm;
	public int ym;
	public Bitmap bmp = null;
	
	public void onCreate(int sz, int w, int h){
		if(isCreated) return;
		
		size = sz;
		xm = w;
		ym = h;
		
		x = new int[size];
		y = new int[size];
		v = new int[size];
		
		Random ran = new Random();
		
		for(int i = 0;i<size;i++){
			x[i] = ran.nextInt(xm);
			y[i] = ran.nextInt(ym);
			v[i] = ran.nextInt(100);
		}
		
		sortByValue();
		
		isCreated = true;
		ran = null;
	}
	
	public void sortByValue() {
		Arrays.sort(v);
	}
	
	public void draw(Canvas canvas) {
		//Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		//Bitmap bmp = Bitmap.createBitmap(xm, ym, conf); // this creates a MUTABLE bitmap
		
		int sqrt = (int)Math.sqrt(size);
		int w = xm/sqrt;
		int h = ym/sqrt;
		if(w<1) w = 1;
		if(h<1) h = 1;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);

		for(int i = 0; i < size; i++) {
			int color = Color.rgb(v[i]+155, v[i]+155, v[i]+155);
			paint.setColor(color);
			canvas.drawRect(x[i], y[i], x[i]+w, y[i]+h, paint);
		}
		
		//canvas = null;
	}
	
	public void draw() {
		bmp = Bitmap.createBitmap(xm, ym, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		draw(canvas);
	}
	
	
}
