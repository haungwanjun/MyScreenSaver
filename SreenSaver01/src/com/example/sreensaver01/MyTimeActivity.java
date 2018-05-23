package com.example.sreensaver01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;


public class MyTimeActivity extends Activity
{

    private static final int MSGKEY = 0x10001;

    private long exitTime = 0;

    //屏保右上角的系统时间
    protected static final String TAG = "MyTimeActivity";
	private int[] mImgIds;
	private MyViewPager mViewPager;
    private TextView mTime;
//    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
   
//        imageView = (ImageView)findViewById(R.id.imageview);
        
        mImgIds = new int[] { R.drawable.a001, R.drawable.a002, R.drawable.a003,
				R.drawable.a004,R.drawable.a005,R.drawable.a006,R.drawable.a007 };
		mViewPager = (MyViewPager) findViewById(R.id.id_viewPager);
		
		mViewPager.setAdapter(new PagerAdapter()
		{

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object)
			{
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				ImageView imageView = new ImageView(MyTimeActivity.this);
				imageView.setImageResource(mImgIds[position]);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				container.addView(imageView);
				mViewPager.setObjectForPosition(imageView, position);
				return imageView;
			}

			@Override
			public int getCount()
			{
				return mImgIds.length;
			}
		});
		
		 mTime = (TextView)findViewById(R.id.mytime);
	      //启动线程刷新屏保界面右上角的时间
	    new TimeThread().start();
	    mTime.bringToFront();
	    
		Intent intent = new Intent(MyTimeActivity.this,MyService.class);  
		startService(intent); 
		
	   
    }

    public class TimeThread extends Thread
    {
        @Override
        public void run()
        {
            do
            {
                try
                {
                    //更新时间
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = MSGKEY;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    /**
     * 2秒内连续两次按下回车键退出屏保
     * 重写方法
     * @param keyCode
     * @param event
     * @return
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(getApplicationContext(), "再按一次解锁键即可进入应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else
        {
            finish();
        }
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MSGKEY:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);
                    mTime.setText(sysTimeStr);
                    break;

            }
        }
    };
    
    @Override
    protected void onStop() {
    	Intent intent = new Intent(MyTimeActivity.this,MyService.class);  
        stopService(intent); 
    	super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}