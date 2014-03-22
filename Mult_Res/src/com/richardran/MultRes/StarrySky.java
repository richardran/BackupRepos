package com.richardran.MultRes;

import java.util.Random;

import android.graphics.Path;

public class StarrySky {
	Path sky = new Path();	
	Path sky1 = new Path();
	Random random = new Random();
	
	public void create() {
		for(int i = 0; i< 1000; i++) {
			int x = random.nextInt(2000);
			int y = random.nextInt(2000);
			int sz = 1+random.nextInt(3);
			int sz1 = 1+random.nextInt(3);
			sky.addRect(x, y, x+sz, y+sz, Path.Direction.CCW);
			sky1.addRect(x, y, x+sz1, y+sz1, Path.Direction.CCW);
		}		
	}
}
