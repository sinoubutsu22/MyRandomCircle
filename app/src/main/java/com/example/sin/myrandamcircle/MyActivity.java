package com.example.sin.myrandamcircle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MyActivity extends Activity {

    MyCircleView view;
    Handler handler = new Handler();
    MyCountDownTimer cdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cdt = new MyCountDownTimer(60000,1000);

        final MyCircleView view =new MyCircleView(getApplication(), cdt);
        setContentView(view);

        Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run () {
                        view.invalidate();
                    }
                });
            }
        },0,1000);
    }
}


class MyCountDownTimer extends CountDownTimer{

    public long millisUntilFinished;

    public MyCountDownTimer(long millisInFuture, long countDownInterval){
        super(millisInFuture, countDownInterval);
    }


    @Override
    public  void onFinish(){
        millisUntilFinished = 0;

    }
    @Override
    public  void onTick(long millisUntilFinished){
        this.millisUntilFinished = millisUntilFinished;
    }
}





class MyCircleView extends View{

        int dx;
        int dy;

        MyCountDownTimer cdt;

        public MyCircleView(Context c, MyCountDownTimer cdt){
            super(c);
            setFocusable(true);
            this.cdt= cdt;
            cdt.start();
        }

        public int displayWidth;
        public int displayHeight;

        public int score = 0;




    protected  void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);

            float cx = (float)Math.random()*displayWidth;
            float cy = (float)Math.random()*displayHeight;

            dx =(int)cx;
            dy =(int)cy;

            canvas.drawCircle(cx,cy,100,paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(36);
            String msg = "Score:"+ score ;
            canvas.drawText(msg,2,30,paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(36);
            String msg2 = "Time:" + cdt.millisUntilFinished/1000;
            canvas.drawText(msg2,2,90,paint);


        }

        protected  void onSizeChanged(int w ,int h,int oldw,int oldh){
            displayWidth  = w;
            displayHeight = h;

        }

        public boolean onTouchEvent(MotionEvent event){
            int ex =(int)event.getX();
            int ey =(int)event.getY();

            if((dx-ex)*(dx-ex)+(dy-ey)*(dy-ey)<100*100){
                score = score +1;
            }


            return true;
        }




    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
