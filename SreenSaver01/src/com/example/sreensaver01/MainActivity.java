package com.example.sreensaver01;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity implements IMainView
{
    private MainPresenter mPresenter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //重置计时
        mPresenter.resetTipsTimer();
        return false;
    }

    @Override
    protected void onResume()
    {
        //启动默认开始计时
        mPresenter.startTipsTimer();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        //有其他操作时结束计时
        mPresenter.endTipsTimer();
        super.onPause();
    }

    @Override
    public void showTipsView()
    {
        //展示屏保界面
        Intent intent = new Intent(MainActivity.this, MyTimeActivity.class);
        startActivity(intent);
    }

}