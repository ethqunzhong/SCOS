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
         * ������ذ�ťʱ����Ļ��ת��MainScreen
         * ����MainScreen��Stringֵ��Return��
         */
        Button cancel = (Button) findViewById(R.id.lay_cancel_button);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(LoginOrRegister.this, "����������", Toast.LENGTH_SHORT).show();

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
         * �����¼��ť����֤EditViewƥ�����
         * 1.���Ϲ�����תMainSCreen�����ݡ�LoginSuccess�������ݶ���loginUser
         * 2.�����Ϲ���ʹ��setError() ��ʾ������Ϣ���������ݲ����Ϲ���
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
                     * SharedPreferenceд��״̬
                     */
                    userName = getSharedPreferences("userName", MODE_PRIVATE).edit();
                    userName.putBoolean(username.getText().toString(), true);
                    userName.commit();
                    loginState = getSharedPreferences("loginState", MODE_PRIVATE).edit();
                    loginState.putInt(username.getText().toString(), 1);
                    loginState.commit();


                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    Toast.makeText(LoginOrRegister.this, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
                }
                if (!isLetterorDigit(inputText_username)) {
                    username.setError("�������ݲ����Ϲ���");
                }
                if (!isLetterorDigit(inputText_password)) {
                    password.setError("�������ݲ����Ϲ���");
                }
            }
        });
        /**
         * ���ע�ᰴť����֤ƥ�����
         * 1.���Ϲ�����תMainSCreen�����ݡ�RegisterSuccess�������ݶ���loginUser
         * 2.�����Ϲ���ʹ��setError() ��ʾ������Ϣ���������ݲ����Ϲ���
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
//                    Toast.makeText(LoginOrRegister.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
                }
                if (!isLetterorDigit(inputText_username)) {
                    username.setError("�������ݲ����Ϲ���");
                }
                if (!isLetterorDigit(inputText_password)) {
                    password.setError("�������ݲ����Ϲ���");
                }
                /**
                 * ���ע��֮���û���Ϣ���浽���ݿ�����
                 */

            }
        });

        SharedPreferences pref = getSharedPreferences("userinfo_data", MODE_PRIVATE);
        if (pref.getBoolean(username.getText().toString(), false)) {
            Toast.makeText(this, "���û��Ѿ�����", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * �����ж����������Ƿ�ֻ����Ӣ����ĸ������
     *
     * @param str �����ַ���
     * @return ���ؽ��
     */
    public static boolean isLetterorDigit(String str) {
        String strRegex = "^[A-Za-z0-9]+$";
        return str.matches(strRegex);
    }
}
