package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;
import es.source.code.model.User;

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
                || getIntent().getStringExtra("string_data").equals("LoginSuccess")||
                getIntent().getStringExtra("string_data").equals("RegisterSuccess")) {
            setContentView(R.layout.mainscreen);
        }else {
            setContentView(R.layout.mainscreen_hide);
        }
        User user=new User();
        String user_string=getIntent().getStringExtra("string_data");
        if (user_string.equals("LoginSuccess")){
            user=(User) getIntent().getSerializableExtra("login_user_info");
        }
        else if(user_string.equals("RegisterSuccess")){
            Toast.makeText(MainScreen.this, "��ӭ����ΪSCOS���û�", Toast.LENGTH_SHORT).show();
            user=(User) getIntent().getSerializableExtra("register_user_info");
        }
        /**
         * �����棬��˰�ť��������� ��˽���
         */
        Button orderdishs=(Button) findViewById(R.id.txt_orderdishs);
        orderdishs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainScreen.this, "�����˽���", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainScreen.this,FoodView.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        /**
         * �����棬��ҳ��ť��������� ��¼/ע�����
         */
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

        /**
         * �����棬�鿴������ť��������� �鿴��������
         */
//        Button findorder=(Button) findViewById(R.id.txt_findorder);
//        findorder.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainScreen.this, "����鿴��������", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(MainScreen.this,OrderView.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//            }
//        });

    }
}

