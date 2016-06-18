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
 * SCOS系统屏幕布局设计
 * 主功能导航包括：
 * 点菜，查看订单，登录/注册，系统帮助（图标文字&可点击）
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
            Toast.makeText(MainScreen.this, "欢迎您成为SCOS新用户", Toast.LENGTH_SHORT).show();
            user=(User) getIntent().getSerializableExtra("register_user_info");
        }
        /**
         * 主界面，点菜按钮，点击进入 点菜界面
         */
        Button orderdishs=(Button) findViewById(R.id.txt_orderdishs);
        orderdishs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainScreen.this, "进入点菜界面", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainScreen.this,FoodView.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        /**
         * 主界面，主页按钮，点击进入 登录/注册界面
         */
        Button home = (Button) findViewById(R.id.txt_home);
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainScreen.this, "进入登录界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        /**
         * 主界面，查看订单按钮，点击进入 查看订单界面
         */
//        Button findorder=(Button) findViewById(R.id.txt_findorder);
//        findorder.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainScreen.this, "进入查看订单界面", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(MainScreen.this,OrderView.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//            }
//        });

    }
}

