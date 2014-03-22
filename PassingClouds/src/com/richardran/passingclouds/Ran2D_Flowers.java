package com.richardran.passingclouds;

import java.util.Arrays;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ran2D_Flowers {
	public boolean isCreated = false;
	public int size;
	public int[] x;
	public int[] y;
	public int[] v;
	public int[] s;
	public int xm;
	public int ym;
	
	public void onCreate(int sz, int w, int h){
		if(isCreated) return;
		
		size = sz;
		xm = w;
		ym = h;
		
		x = new int[size];
		y = new int[size];
		v = new int[size];
		s = new int[size];
		
		Random ran = new Random();
		
		for(int i = 0;i<size;i++){
			x[i] = ran.nextInt(xm)-100;
			y[i] = ran.nextInt(ym);
			v[i] = ran.nextInt(30);
			s[i] = 10+ran.nextInt(5);
		}
		
		Arrays.sort(v);
		//Arrays.sort(y);
		
		isCreated = true;
		ran = null;
	}
	
	public void persRender(Canvas canvas, int timer, int canvas_h, double ratio) {
		   double rad2pix = 8* ratio * canvas_h/Math.PI; // this will ensure 2/3 as horizon, but with twice coverage
		   int shift = (int)(1.9* rad2pix);
		   
		   Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		   paint.setStyle(Paint.Style.FILL);
		   
		   for(int i = 0; i< this.size; i++) {
			   int x = this.x[i];
			   int y = this.y[i]+timer;
			   if(y>this.ym) {
				   y = y%this.ym;
			   }
			   
			   double yy = rad2pix*Math.atan((y)/200.1);
			   double r = 1;
			   if(y<1) { r = yy/ 1.0; }
			   else { r = yy/y; }
			   double ty = shift-yy;

			   double sz =  (5*r);
			   if (sz<3) sz = 3;
			   
			   int color = Color.rgb(255, 150, 0);
			   paint.setColor(color);

			   int isz = (int)(sz);
			   RectF oval = new RectF();
			   oval.left = x;
			   oval.top = (float)ty;
			   oval.right = x + 2*isz;
			   oval.bottom = oval.top + isz;
			   canvas.drawOval(oval, paint);
		   }
	}
	
}
