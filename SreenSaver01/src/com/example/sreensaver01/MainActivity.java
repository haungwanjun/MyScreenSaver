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
        //���ü�ʱ
        mPresenter.resetTipsTimer();
        return false;
    }

    @Override
    protected void onResume()
    {
        //����Ĭ�Ͽ�ʼ��ʱ
        mPresenter.startTipsTimer();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        //����������ʱ������ʱ
        mPresenter.endTipsTimer();
        super.onPause();
    }

    @Override
    public void showTipsView()
    {
        //չʾ��������
        Intent intent = new Intent(MainActivity.this, MyTimeActivity.class);
        startActivity(intent);
    }

}