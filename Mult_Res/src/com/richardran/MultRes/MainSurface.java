package com.richardran.MultRes;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;


public class MainSurface extends SurfaceView implements Runnable{
    
   Thread thread = null;
   SurfaceHolder surfaceHolder;
   volatile boolean running = false;
   
   Ran2D_MultRes mr = new Ran2D_MultRes();
   int cw = 1700;
   int ch = 1700;
   
   volatile boolean touched = false;
   volatile float touched_x, touched_y;
     
   public MainSurface(Context context) {
	   super(context);
	   // TODO Auto-generated constructor stub
	   surfaceHolder = getHolder();
   }
    
   public void onResumeMySurfaceView(){	   
	   if(surfaceHolder.getSurface().isValid()){
		   Canvas canvas = surfaceHolder.lockCanvas();
		   cw = canvas.getWidth();
		   ch = canvas.getHeight();
		   surfaceHolder.unlockCanvasAndPost(canvas);
	   }
	   
	   if(!mr.isCreated) {
		   mr.create(cw, ch);
		   mr.createBmp();
	   }
	   
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
		   if(mr==null) return;
		   
		   if(surfaceHolder.getSurface().isValid()){
			   Canvas canvas = surfaceHolder.lockCanvas();
			   
			   /*
			   Bitmap bmp = mr.bitmap;
			   Matrix matrix = new Matrix();
			   matrix.postScale(1, 1);
			   canvas.drawBitmap(bmp, matrix, null);
			   */
			   
			   //if(mr.isCreated) mr.render_flat(canvas);

			   if(mr.isCreated) {
				   mr.drawBmp(canvas); 
			   }
			   
			   
			   if(touched){  }

			   surfaceHolder.unlockCanvasAndPost(canvas);
			   
			   try {
				   thread.sleep(30);
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
