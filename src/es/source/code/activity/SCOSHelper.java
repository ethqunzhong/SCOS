package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Picture;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by this.zyq on 2016/6/24.
 * @author this.zyq
 */
public class SCOSHelper extends Activity {
    private GridView gridview;
    private String[] titles=new String[]{"用户使用协议","关于系统","人工帮助","短信帮助","邮件帮助"};
    private int[] imageResource={R.mipmap.user_protocol,R.mipmap.about_system,
            R.mipmap.phone,R.mipmap.message,R.mipmap.email};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_helper);
        gridview=(GridView) findViewById(R.id.grid_helper_photo);
        MyGridViewAdapter adapter=new MyGridViewAdapter(imageResource,titles,this);
        gridview.setAdapter(adapter);
    }

}
class MyGridViewAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<Picture> pictures;

    public MyGridViewAdapter(int[] images,String[] titles,Context context){
        super();
        pictures=new ArrayList<Picture>();
        inflater=LayoutInflater.from(context);
        for (int i=0;i<images.length;i++){
            Picture picture=new Picture(images[i],titles[i]);
            pictures.add(picture);
        }
    }


    @Override
    public int getCount() {
        if (null!=pictures){
            return pictures.size();
        }else{
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_grid_icon,null);
            viewHolder=new ViewHolder();
            viewHolder.image=(ImageView) convertView.findViewById(R.id.img_icon);
            viewHolder.title=(TextView) convertView.findViewById(R.id.txt_icon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(pictures.get(position).getImageId());
        viewHolder.title.setText(pictures.get(position).getTitle());
        return convertView;
    }

    class ViewHolder{
        public ImageView image;
        public TextView title;
    }
    class Picture
    {
        private String title;
        private int imageId;

        public Picture()
        {
            super();
        }

        public Picture(int imageId, String title)
        {
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public int getImageId()
        {
            return imageId;
        }

        public void setImageId(int imageId)
        {
            this.imageId = imageId;
        }
    }
}


