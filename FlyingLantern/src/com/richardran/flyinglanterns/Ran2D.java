package com.richardran.flyinglanterns;

import java.util.Random;

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
	
}
