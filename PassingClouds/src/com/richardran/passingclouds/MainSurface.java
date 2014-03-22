package com.richardran.passingclouds;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

import com.richardran.myandriodtest3.R;


public class MainSurface extends SurfaceView implements Runnable{
   Thread thread = null;
   SurfaceHolder surfaceHolder;
   volatile boolean running = false;
   private Paint paintwater = new Paint(Paint.ANTI_ALIAS_FLAG);
   private Paint star = new Paint(Paint.ANTI_ALIAS_FLAG);

   Random random;
   
   Ran2D ran2d = new Ran2D();
   int timer = 0;
   int canvas_w = 0;
   int canvas_h = 0;
   int horizon = 0;
   int horizon1 = 0;
   double rad2pix = 1;
   int shift = 0;
   int topshift = 0;
   
   StarrySky sky = new StarrySky();
   ScaledBitmapArray sb = new ScaledBitmapArray();
   
  
   volatile boolean touched = false;
   volatile float touched_x, touched_y;
     
   public MainSurface(Context context) {
	   super(context);
	   // TODO Auto-generated constructor stub
	   surfaceHolder = getHolder();
	   random = new Random();
   }
    
   public void onResumeMySurfaceView(){
	   // water	color
	   paintwater.setStyle(Paint.Style.FILL);
	   int color = Color.rgb(0, 0, 70);
	   paintwater.setColor(color);
	   
	   // star color
	   color = Color.rgb(200, 200, 255);
	   star.setColor(color);
	   
	   sky.create();
	   
	   Bitmap log = BitmapFactory.decodeResource(getResources(), R.drawable.lantern);
	   sb.setBitmap(log, 100, 1);
	   
	   running = true;
	   thread = new Thread(this);
	   thread.start();
   }
  
   public void onPauseMySurfaceView(){
	   boolean retry = true;
	   running = false;
	   while(retry){
		   try {
			   thread.join();
			   retry = false;
		   } catch (InterruptedException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }
   }

   @Override
   public void run() {
	   // TODO Auto-generated method stub
	   while(running){
		   if(ran2d==null) return;
		   
		   if(surfaceHolder.getSurface().isValid()){
			   Canvas canvas = surfaceHolder.lockCanvas();
			   
			   if(!ran2d.isCreated){
				   canvas_w = canvas.getWidth();
				   canvas_h = canvas.getHeight();
				   rad2pix = 4* 0.667 * canvas_h/Math.PI; // this will ensure 2/3 as horizon
				   
				   horizon = (int)(0.667 * canvas_h);
				   double yy = rad2pix*Math.atan(4000/100.1);  // slightly less than pi/2
				   shift = (int)(0.667*canvas_h);
				   horizon1 = (int)(-0.5 *yy+ 3 * horizon - shift);  // negative horizon, slightly bellow infinity
				   
				   topshift = (int)(0.02*canvas_h);
				   ran2d.onCreate(1000, 3000, 4000);
			   }
			   
			   //drawing background
			   canvas.drawColor(Color.BLACK);
			   if(timer%4==0) canvas.drawPath(sky.sky, star);
			   else canvas.drawPath(sky.sky1, star);
			   
			   canvas.drawRect(0, horizon1, canvas_w, canvas_h, paintwater);
			   
			   for(int i = 0; i< ran2d.size; i++) {
				   int x = ran2d.x[i];
				   int y = ran2d.y[i]+timer;
				   if(y>ran2d.ym) {
					   y = y%ran2d.ym;
				   }
				   
				   double yy = rad2pix*Math.atan(y/100.1);
				   double r = 1;
				   if(y<1) { r = yy/ 1.0; }
				   else { r = yy/y; }
				   double ty = yy+topshift;

				   int sz = (int) ( 5*r);
				   Bitmap b = sb.bmps.get(2*sz); 
				   if(b==null) continue;
				   canvas.drawBitmap(b, (float)(x*r), (float)ty-shift, null);
				   double by = -0.5 *yy+ 3 * horizon;
				   canvas.drawBitmap(b, (float)(x*r), (float)by-shift, null);
			   }
			   
			   
			   if(touched){  }

			   surfaceHolder.unlockCanvasAndPost(canvas);
			   timer=timer+2;
			   
			   try {
				   Thread.sleep(30);
			   } catch (InterruptedException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			   
		   }
		   
	   }
   }
    

   @Override
   public boolean onTouchEvent(MotionEvent event) {
	   // TODO Auto-generated method stub
  
	   touched_x = event.getX();
	   touched_y = event.getY();
  
	   int action = event.getAction();
 
	   switch(action){
	   case MotionEvent.ACTION_DOWN:
		   touched = true;
		   break;
	   case MotionEvent.ACTION_MOVE:
		   touched = true;
		   break;
	   case MotionEvent.ACTION_UP:
		   touched = false;
		   break;
	   case MotionEvent.ACTION_CANCEL:
		   touched = false;
		   break;
	   case MotionEvent.ACTION_OUTSIDE:
		   touched = false;
		   break;
	   default:
	   }
	
	   return true; //processed
   }
   
}
