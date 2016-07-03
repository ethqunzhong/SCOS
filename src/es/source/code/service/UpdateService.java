package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;

/**
 * Created by this.zyq on 2016/7/1.
 * @author this.zyq
 */
public class UpdateService extends IntentService {
    public UpdateService() {
        super("UpdateService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("UpdateService","Thread id is "+Thread.currentThread().getId());
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new Notification(R.mipmap.scoslogo,"��Ʒ�ϼ�",System.currentTimeMillis());

//        notification.setLatestEventInfo(this,"this is content title","this is content text",null);
        Intent notification_intent=new Intent(this, FoodDetailed.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,notification_intent,PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(this,"����С��Ϻ","�۸�:20,����:����",pi);
        manager.notify(1,notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("UpdateService","onDestroy executed");
    }
}
