package com.richardran.myandriodtest3;

import java.util.ArrayList;

public class Ran2D_MultRes {
	public int res = 3;
	public ArrayList<Ran2D> data = new ArrayList<Ran2D>();
	
	public void create() {
		int sample = 10;
		for(int i = 0;i<res;i++) {
			Ran2D r = new Ran2D();
			r.onCreate(sample, 1000, 1000);
			data.add(r);
			sample = sample * 10;
		}
	}
	
	public void render_flat() {
		
	}
}
