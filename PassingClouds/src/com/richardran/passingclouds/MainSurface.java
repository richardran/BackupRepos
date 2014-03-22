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

import com.richardran.passingclouds.R;


public class MainSurface extends SurfaceView implements Runnable{
   Thread thread = null;
   SurfaceHolder surfaceHolder;
   volatile boolean running = false;
   private Paint paintwater = new Paint(Paint.ANTI_ALIAS_FLAG);
   private Paint paintgrass = new Paint(Paint.ANTI_ALIAS_FLAG);
   
   Random random;
   
   Ran2D ran2d = new Ran2D();
   Ran2D_Grass grass = new Ran2D_Grass();
   Ran2D_Flowers flowers = new Ran2D_Flowers();
   
   int timer = 0;
   int canvas_w = 0;
   int canvas_h = 0;
   
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
	   paintgrass.setStyle(Paint.Style.FILL);
	   
	   int color = Color.rgb(0, 0, 70);
	   paintwater.setColor(color);
	   color = Color.rgb(100, 185, 100);
	   paintgrass.setColor(color);
	   
	   Bitmap log = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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
				   ran2d.onCreate(800, 5000, 5000);
			   }
			   
			   if(!grass.isCreated) {
				   int skyh = canvas_h/4;
				   canvas.drawRect(0, skyh+1, canvas_w, canvas_h, paintgrass);
				   grass.onCreate(8000, 2000, 3000);
				   grass.persRender(canvas, 0, canvas_h, 0.25);				   
			   }
			   
			   if(!flowers.isCreated) {
				   flowers.onCreate(8000, 2000, 3000);
				   flowers.persRender(canvas, 0, canvas_h, 0.25);						   
			   }
			   
			   //drawing background
			   //canvas.drawColor(Color.BLUE);
			   //if(timer%4==0) canvas.drawPath(sky.sky, star);
			   //else canvas.drawPath(sky.sky1, star);
			   int skyh = canvas_h/4;
			   //canvas.drawRect(0, skyh+1, canvas_w, canvas_h, paintgrass);
			   
			   for(int i = 0;i<=skyh;i++) {
				   double light = 35/(double)(skyh);
				   int badj = (int)(light * i);
				   int radj = (int)(light * i* 6);
				   int color = Color.rgb(radj, radj, 220+badj);
				   paintwater.setColor(color);
				   canvas.drawRect(0, i, canvas_w, (i+1), paintwater);
			   }
			   
			   ran2d.persRender(canvas, timer, canvas_h, 0.25);
			   //grass.persRender(canvas, 0, canvas_h, 0.25);
			   
			   if(touched){  }

			   if(grass.isCreated&timer<4) {
				   //skyh = canvas_h/4;
				   canvas.drawRect(0, skyh+1, canvas_w, canvas_h, paintgrass);
				   //grass.onCreate(1000, 5000, 5000);
				   grass.persRender(canvas, 0, canvas_h, 0.25);
				   flowers.persRender(canvas, 0, canvas_h, 0.25);
			   }
			   
			   surfaceHolder.unlockCanvasAndPost(canvas);
			   timer=timer+1;
			   
			   try {
				   Thread.sleep(40);
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
