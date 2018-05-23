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
		// ��ʼ��������
		mp.start();
		// ���ֲ�����ϵ��¼�����
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// ѭ������
				try {
					mp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// ��������ʱ����������¼�����
		mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// �ͷ���Դ
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
		// ��ʼ��������Դ
		try {
			// ����MediaPlayer����
			mp = new MediaPlayer();
			// �����ֱ�����res/raw/musictest.mp3,R.java���Զ�����{public static final int musictest=0x7f040000;}
			mp = MediaPlayer.create(MyService.this, R.raw.bgm);
			// ��MediaPlayerȡ�ò�����Դ��stop()֮��Ҫ׼��PlayBack��״̬ǰһ��Ҫʹ��MediaPlayer.prepeare()
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
		// ����ֹͣʱֹͣ�������ֲ��ͷ���Դ
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