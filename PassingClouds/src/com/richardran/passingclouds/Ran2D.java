package com.richardran.passingclouds;

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
	public int xm;
	public int ym;
	
	public void onCreate(int sz, int w, int h){
		if(isCreated) return;
		
		size = sz;
		xm = w;
		ym = h;
		
		x = new int[size];
		y = new int[size];
		Random ran = new Random();
		
		for(int i = 0;i<size;i++){
			x[i] = ran.nextInt(xm);
			y[i] = ran.nextInt(ym);
		}
		
		isCreated = true;
		ran = null;
	}
	
	public int shift_y(int i, int shift) {
		int ty = y[i] + shift;
		if (ty>ym) ty = ty%ym;
		
		return ty;
	}
	
	public void persRender(Canvas canvas, int timer, int canvas_h, double ratio) {
		   double rad2pix = 3* ratio * canvas_h/Math.PI; // this will ensure 2/3 as horizon, but with twice coverage
		   int shift = (int)(0.5 * rad2pix);
		   
		   Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		   paint.setStyle(Paint.Style.FILL);
		   
		   for(int i = 0; i< this.size; i++) {
			   int x = this.x[i];
			   int y = this.y[i]+timer;
			   if(y>this.ym) {
				   y = y%this.ym;
			   }
			   
			   double yy = rad2pix*Math.atan(y/100.1);
			   double r = 1;
			   if(y<1) { r = yy/ 1.0; }
			   else { r = yy/y; }
			   double ty = yy-shift;

			   float left = (float)(x*r);
			   float top = (float)(ty);
			   int sz = (int) ( 5*r);
			   
			   int color = Color.rgb(255, 132, 0);
			   paint.setColor(color);
			   canvas.drawRect(left, top, left+sz, top+sz, paint);
			   /*
			   // bitmap version
			   int sz = (int) ( 5*r);
			   Bitmap b = sb.bmps.get(2*sz); 
			   if(b==null) continue;
			   canvas.drawBitmap(b, (float)(x*r), (float)ty-shift, null);
			   */
		   }		
	}
	
}
