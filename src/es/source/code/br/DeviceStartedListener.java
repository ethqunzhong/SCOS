package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import es.source.code.service.UpdateService;

/**
 * Created by this.zyq on 2016/7/1.
 * @author this.zyq
 */
public class DeviceStartedListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,UpdateService.class));
    }
}
