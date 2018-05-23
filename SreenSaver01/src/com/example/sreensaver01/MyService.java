package com.example.sreensaver01;

import java.io.IOException;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {

	private MediaPlayer mp;

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// 开始播放音乐
		mp.start();
		// 音乐播放完毕的事件处理
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// 循环播放
				try {
					mp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// 播放音乐时发生错误的事件处理
		mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// 释放资源
				try {
					mp.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		super.onStart(intent, startId);
	}

	@Override
	public void onCreate() {
		// 初始化音乐资源
		try {
			// 创建MediaPlayer对象
			mp = new MediaPlayer();
			// 将音乐保存在res/raw/musictest.mp3,R.java中自动生成{public static final int musictest=0x7f040000;}
			mp = MediaPlayer.create(MyService.this, R.raw.bgm);
			// 在MediaPlayer取得播放资源与stop()之后要准备PlayBack的状态前一定要使用MediaPlayer.prepeare()
			mp.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// 服务停止时停止播放音乐并释放资源
		mp.stop();
		mp.release();
		super.onDestroy();
	}

	public MyService() {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}