package com.bawi.zmm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bawi.zmm.R;

/**
 * Created by 1 on 2018/7/7.
 */

public class CountDownView extends View {
    //申请画笔
    private TypedArray typedArray;
    Paint paint;
    int time;
    float radius;
    int textColor;
    int bgColor;

    int cx;
    int cy;

    MyListener listener;
    Handler handler;
    //new CountDownView()的时候会调用这个构造器
    public CountDownView(Context context) {
        super(context);
        init(context, null);
    }
     //在xml中的时候会调用这个构造器
    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
     //AttributeSet  attrs 封装了该view xml中的所有属性，我们依据这些属性去绘制View
    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //初始化属性
        if (attrs != null) {
            //获取属性列表
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
            //TODO 初始化自定义属性
            time = typedArray.getInteger(R.styleable.CountDownView_CountDownTime, 3);
            radius = typedArray.getDimension(R.styleable.CountDownView_CountDownRadius, 25);
            textColor = typedArray.getColor(R.styleable.CountDownView_CountDownTextColor, Color.BLACK);
            bgColor = typedArray.getColor(R.styleable.CountDownView_CountDownBgColor, Color.WHITE);
            //释放
            typedArray.recycle();
        } else {
            time = 3;
            radius = 30;
            textColor = Color.BLACK;
            bgColor = Color.WHITE;
        }

        //初始化画笔
        paint = new Paint();
        //设置是空心还是实心 空心就是只画轮廓
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(bgColor);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //设置画笔的粗细
        paint.setStrokeWidth(2);
    }
   //测量自己的宽和高的方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    //当view的宽和高发生改变，会回调这个方法，我们可以拿到最新的宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx=w/2;
        cy=h/2;
    }

    //绘制的方法
    //canvas 是画布  他决定我们绘制什么形状
    //paint  是画笔  他决定我们绘制的样式和颜色 ，不建议在onDraw中创建会造成资源浪费
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆行
        Path path=new Path();
        path.addCircle(cx,cy,radius,Path.Direction.CW);
        canvas.drawPath(path,paint);
        //绘制文字
        paint.setColor(textColor);
        paint.setTextSize(18);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(""+time,cx,cy,paint);
    }
//设置倒计时的时间
    public void setTime(int time) {
        this.time = time;
        //更新界面
        invalidate();
    }
  //启动倒计时
    public  void start(final Activity cur,final  Class<?>next){
       //定义Hander
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //结束条件
                if (time==0){
                    //跳转到MainActiivty
                    Intent intent=new Intent(cur,next);
                    cur.startActivity(intent);
                    cur.finish();
                }else {
                    time--;
                    setTime(time);
                    sendEmptyMessageDelayed(1,1000);
                }
            }
        };
        handler.sendEmptyMessageDelayed(1,1000);
    }
    //停止倒计时
    public  void stop(){
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (listener !=null){
                listener.onClick(this);
            }
        }
        return super.onTouchEvent(event);
    }

    public  void setListener(MyListener listener){
        this.listener=listener;
   }

    //设置点击事件
    public  interface  MyListener{
        void onClick(View view);
    }
}
