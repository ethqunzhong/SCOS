package es.source.code.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by this.zyq on 2016/6/18.
 * @author this.zyq
 */
public class FoodDetailed extends Activity {
    private ImageView imageView;

    private TextView detailedName;

    private TextView detailedPrice;

    private TextView detailedAddition;

    private Button detailedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //����������֪ͨ����֪ͨͼ�껹���ڶ���������
        manager.cancel(1);
//        imageView = (ImageView)findViewById(R.id.detailed_image);
//        detailedName = (TextView)findViewById(R.id.food_detail_name);
//        detailedPrice = (TextView)findViewById(R.id.food_detailed_price);
//        detailedAddition = (TextView)findViewById(R.id.food_detailed_others);
//        detailedButton = (Button)findViewById(R.id.food_detailed_button);
//
//        detailedName.setText("���м�");
//        detailedPrice.setText("�۸�20");
//        detailedAddition.setText("ʵ�ݺó�");
//        detailedButton.setText("�˵�");
    }
}
