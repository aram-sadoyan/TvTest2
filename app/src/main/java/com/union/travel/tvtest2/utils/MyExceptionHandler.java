package com.union.travel.tvtest2.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.union.travel.tvtest2.ApplicationMain;
import com.union.travel.tvtest2.MainActivity;

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
	private Activity activity;
	public MyExceptionHandler(Activity a) {
		activity = a;
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Intent intent = new Intent(activity, MainActivity.class);
		intent.putExtra("crash", true);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(ApplicationMain.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager mgr = (AlarmManager) ApplicationMain.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1, pendingIntent);
		activity.finish();
		System.exit(2);
	}
}
