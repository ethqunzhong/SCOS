package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by this.zyq on 2016/6/5.
 * SCOSϵͳ��Ļ�������
 * �����ܵ���������
 * ��ˣ��鿴��������¼/ע�ᣬϵͳ������ͼ������&�ɵ����
 */
public class MainScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loginorregister);
        if (getIntent().getStringExtra("string_data").equals("FromEntry") ||
                getIntent().getStringExtra("string_data").equals("Return")
                || getIntent().getStringExtra("string_data").equals("LoginSuccess")) {
            setContentView(R.layout.mainscreen);
        }else {
            setContentView(R.layout.mainscreen_hide);
        }

//        if (getIntent().getStringExtra("from_entry").equals("FromEntry")||
//                getIntent().getStringExtra("from_login_button").equals("LoginSuccess")||
//                getIntent().getStringExtra("from_cancel_button").equals("Return")){
//            setContentView(R.layout.mainscreen);
//        }else{
//            setContentView(R.layout.mainscreen_hide);
//        }

        Button home = (Button) findViewById(R.id.txt_home);
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainScreen.this, "�����¼����", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }
}

