package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class LoginOrRegister extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_login);
        /**
         * 点击返回按钮时，屏幕跳转到MainScreen
         * 并像MainScreen传String值“Return”
         */
        Button cancel = (Button) findViewById(R.id.lay_cancel_button);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginOrRegister.this, "返回主界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                intent.putExtra("string_data", "Return");
//                intent.putExtra("from_cancel_button", "Return");
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
        /**
         * 点击登陆按钮，验证EditView匹配规则
         * 1.符合规则，跳转MainSCreen，传递“LoginSuccess”
         * 2.不符合规则，使用setError() 提示错误信息“输入内容不符合规则”
         */
        Button login = (Button) findViewById(R.id.lay_login_button);
        EditText username = (EditText) findViewById(R.id.txt_username);
        EditText password = (EditText) findViewById(R.id.txt_password);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText_username = username.getText().toString();
                String inputText_password = password.getText().toString();
                if (isLetterorDigit(inputText_username) && isLetterorDigit(inputText_password)) {
                    Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                    intent.putExtra("string_data","LoginSuccess");
//                    intent.putExtra("from_login_button","LoginSuccess");
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                }
                if (!isLetterorDigit(inputText_username)){
                    username.setError("输入内容不符合规则");
                }
                if (!isLetterorDigit(inputText_password)){
                    password.setError("输入内容不符合规则");
                }
            }
        });
    }

    public static boolean isLetterorDigit(String str) {
        String strRegex = "^[A-Za-z0-9]+$";
        return str.matches(strRegex);
    }
}
