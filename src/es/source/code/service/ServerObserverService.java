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

    //������ϵ��ͷ���֮���ͨ��
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //�����һ�δ���ʱ�����
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //ÿ�����������ʱ�򶼻����
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
                    //�ر�ģ����շ������Ķ��߳�
                    break;
                case 1:
                    //�������߳�ģ����շ�������������Ʒ�����Ϣ
                    //�鿴app���̵�����״̬��������ô����meg.what=10
                    //���ڸ�msg��Я���յ��Ŀ����Ϣ�����߳�����300ms
                    break;
                default:
                    break;
            }
        }
    };
}
