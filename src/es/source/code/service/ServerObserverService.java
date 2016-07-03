package es.source.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * Created by this.zyq on 2016/7/1.
 *
 * @author this.zyq
 */
public class ServerObserverService extends Service {

    //用来联系活动和服务之间的通信
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //服务第一次创建时候调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //每次启动服务的时候都会调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler cMessageHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //关闭模拟接收服务器的多线程
                    break;
                case 1:
                    //启动多线程模拟接收服务器传回来菜品库存信息
                    //查看app进程的运行状态，运行那么发送meg.what=10
                    //并在该msg中携带收到的库存信息，多线程休眠300ms
                    break;
                default:
                    break;
            }
        }
    };
}
