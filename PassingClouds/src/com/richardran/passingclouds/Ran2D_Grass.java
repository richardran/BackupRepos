package com.richardran.passingclouds;

import java.util.Arrays;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ran2D_Grass {
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
		   int shift = (int)(1.905* rad2pix);
		   
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

			   float left = (float)(x);
			   float top = (float)(ty);
			   int sz = (int) (this.s[i]*r);
			   
			   int cloudc = 80 + this.v[i];
			   int color = Color.rgb(cloudc, 85+cloudc, cloudc);
			   paint.setColor(color);
			   canvas.drawRect(left, top, left+sz, top+sz, paint);
		   }	
	}
	
}
