package es.source.code.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;


//实现接口和抽象类方法：快捷键 ctrl+I
public class SCOSEntry extends Activity implements OnGestureListener, OnTouchListener {
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.entry);
        ImageView iv = (ImageView) findViewById(R.id.scosentry);
        iv.setOnTouchListener(this);
        detector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return detector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if ((e1.getX()-e2.getX())>35){
            Intent intent=new Intent();
            intent.setClass(this,MainScreen.class);
            intent.putExtra("string_data","FromEntry");
//            intent.putExtra("from_entry","FromEntry");
            startActivity(intent);
            //设置动画，下一个画面从右边进入，当前画面从左边退出
            overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            return true;
        }else{
            return false;
        }
    }

}

//http://blog.csdn.net/harvic880925/article/details/39520901 用户手势检测