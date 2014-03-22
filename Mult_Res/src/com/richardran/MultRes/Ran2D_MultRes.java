package com.richardran.MultRes;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Matrix;

public class Ran2D_MultRes {
	public int res = 3;
	public ArrayList<Ran2D> data = new ArrayList<Ran2D>();
	public boolean isCreated = false;
	
	public void create(int w, int h) {
		int sample = 10;
		for(int i = 0;i<res;i++) {
			Ran2D r = new Ran2D();
			r.onCreate(sample, w, h);
			data.add(r);
			sample = sample * 10;
		}
		
		isCreated = true;
	}
	
	public void render_flat(Canvas canvas) {
		for(int i = 0; i< res; i++) {
			data.get(i).draw(canvas);
		}
	}
	
	public void createBmp() {
		for(int i = 0; i< res; i++) {
			data.get(i).draw();
		}
	}

	public void drawBmp(Canvas canvas) {
		Matrix matrix = new Matrix();
		matrix.postScale(1, 1);
		for(int i = 0; i< res; i++) {
			if(data.get(i).bmp==null) continue;
			canvas.drawBitmap(data.get(i).bmp, matrix, null);
		}
	}
}

