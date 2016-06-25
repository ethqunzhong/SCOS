package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import es.source.code.model.User;

import java.util.Set;


public class LoginOrRegister extends Activity {

    private SharedPreferences.Editor userName;
    private SharedPreferences.Editor loginState;

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
//                Toast.makeText(LoginOrRegister.this, "返回主界面", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                intent.putExtra("string_data", "Return");
//                intent.putExtra("from_cancel_button", "Return");
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        Button login = (Button) findViewById(R.id.lay_login_button);
        Button register = (Button) findViewById(R.id.lay_register_button);
        EditText username = (EditText) findViewById(R.id.txt_username);
        EditText password = (EditText) findViewById(R.id.txt_password);

        /**
         * 点击登录按钮，验证EditView匹配规则
         * 1.符合规则，跳转MainSCreen，传递“LoginSuccess”，传递对象loginUser
         * 2.不符合规则，使用setError() 提示错误信息“输入内容不符合规则”
         */
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText_username = username.getText().toString();
                String inputText_password = password.getText().toString();
                if (isLetterorDigit(inputText_username) && isLetterorDigit(inputText_password)) {
                    User loginUser = new User();
                    loginUser.setUserName(inputText_username);
                    loginUser.setPassword(inputText_password);
                    loginUser.setOldUser(true);
                    Intent intent_login = new Intent(LoginOrRegister.this, MainScreen.class);
                    Bundle bundle_login = new Bundle();
                    bundle_login.putSerializable("login_user_info", loginUser);
                    intent_login.putExtras(bundle_login);
                    intent_login.putExtra("string_data", "LoginSuccess");
//                    intent.putExtra("from_login_button","LoginSuccess");
                    startActivity(intent_login);
                    /**
                     * SharedPreference写入状态
                     */
                    userName = getSharedPreferences("userName", MODE_PRIVATE).edit();
                    userName.putBoolean(username.getText().toString(), true);
                    userName.commit();
                    loginState = getSharedPreferences("loginState", MODE_PRIVATE).edit();
                    loginState.putInt(username.getText().toString(), 1);
                    loginState.commit();


                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    Toast.makeText(LoginOrRegister.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
                if (!isLetterorDigit(inputText_username)) {
                    username.setError("输入内容不符合规则");
                }
                if (!isLetterorDigit(inputText_password)) {
                    password.setError("输入内容不符合规则");
                }
            }
        });
        /**
         * 点击注册按钮，验证匹配规则
         * 1.符合规则，跳转MainSCreen，传递“RegisterSuccess”，传递对象loginUser
         * 2.不符合规则，使用setError() 提示错误信息“输入内容不符合规则”
         */
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText_username = username.getText().toString();
                String inputText_password = password.getText().toString();
                if (isLetterorDigit(inputText_username) && isLetterorDigit(inputText_password)) {
                    User loginUser = new User();
                    loginUser.setUserName(inputText_username);
                    loginUser.setPassword(inputText_password);
                    loginUser.setOldUser(false);
                    Intent intent_register = new Intent(LoginOrRegister.this, MainScreen.class);
                    Bundle bundle_register = new Bundle();
                    bundle_register.putSerializable("register_user_info", loginUser);
                    intent_register.putExtras(bundle_register);
                    intent_register.putExtra("string_data", "RegisterSuccess");
                    startActivity(intent_register);
                    userName = getSharedPreferences("userName", MODE_PRIVATE).edit();
                    userName.putBoolean(inputText_username, true);
                    userName.commit();
                    loginState = getSharedPreferences("loginState", MODE_PRIVATE).edit();
                    loginState.putInt(inputText_username, 1);
                    loginState.commit();

                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//                    Toast.makeText(LoginOrRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                if (!isLetterorDigit(inputText_username)) {
                    username.setError("输入内容不符合规则");
                }
                if (!isLetterorDigit(inputText_password)) {
                    password.setError("输入内容不符合规则");
                }
                /**
                 * 点击注册之后将用户信息保存到数据库里面
                 */

            }
        });

        SharedPreferences pref = getSharedPreferences("userinfo_data", MODE_PRIVATE);
        if (pref.getBoolean(username.getText().toString(), false)) {
            Toast.makeText(this, "此用户已经存在", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 用来判断输入内容是否只包含英文字母和数字
     *
     * @param str 输入字符串
     * @return 返回结果
     */
    public static boolean isLetterorDigit(String str) {
        String strRegex = "^[A-Za-z0-9]+$";
        return str.matches(strRegex);
    }
}
