package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

/**
 * Created by this.zyq on 2016/6/24.
 *
 * @author this.zyq
 */
public class SCOSHelper extends Activity {
    private GridView gridview;
    private static final int mailsend_status = 1;
    private String[] titles = new String[]{"用户使用协议", "关于系统", "人工帮助", "短信帮助", "邮件帮助"};
    private int[] imageResource = {R.mipmap.user_protocol, R.mipmap.about_system,
            R.mipmap.phone, R.mipmap.message, R.mipmap.email};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_helper);
        gridview = (GridView) findViewById(R.id.grid_helper_photo);
        MyGridViewAdapter adapter = new MyGridViewAdapter(imageResource, titles, this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(SCOSHelper.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
                //点击人工帮助，实现自动拨号，拨打 "5554"
                if (position == 2) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:5554"));
                    startActivity(intent);
                }
                // 短信帮助
                if (position == 3) {
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage("5554", null, "test scos helper", null, null);
                    Toast.makeText(getApplicationContext(), "求助短信发送成功", Toast.LENGTH_SHORT).show();
                }
                // 邮件帮助 多线程MailSender
                if (position == 4) {
                    Handler handler = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case mailsend_status:
                                    Toast.makeText(getApplicationContext(), "求助邮件已发送成功", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
                    class MailSender extends Thread {
                        @Override
                        public void run() {
                            Intent data = new Intent(Intent.ACTION_SENDTO);
                            data.setData(Uri.parse("mailto:zyqbit@qq.com"));
                            data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                            data.putExtra(Intent.EXTRA_TEXT, "这是内容");
                            Message message = new Message();
                            message.what = mailsend_status;
                            handler.sendMessage(message);
                            startActivity(data);
                        }
                    }
                    new MailSender().start();
                }
            }
        });
    }

}

class MyGridViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Picture> pictures;

    public MyGridViewAdapter(int[] images, String[] titles, Context context) {
        super();
        pictures = new ArrayList<Picture>();
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < images.length; i++) {
            Picture picture = new Picture(images[i], titles[i]);
            pictures.add(picture);
        }
    }

    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_icon, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.txt_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(pictures.get(position).getImageId());
        viewHolder.title.setText(pictures.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        public ImageView image;
        public TextView title;
    }

    class Picture {
        private String title;
        private int imageId;

        public Picture() {
            super();
        }

        public Picture(int imageId, String title) {
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }
}


