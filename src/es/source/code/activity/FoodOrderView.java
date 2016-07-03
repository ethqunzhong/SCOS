package es.source.code.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import es.source.code.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by this.zyq on 2016/6/18.
 *
 * @author this.zyq
 */
public class FoodOrderView extends Activity {
    private List<ArrayList<HashMap<String, Object>>> listData;
    final int NO_ORDER = 0;
    final int YES_ORDER = 1;
    private int bmpW;
    private int offset = 0;
    private int currIndex = 0; //当前页卡编号
    private TextView orderNumber;
    private TextView orderPrice;
    private Button orderButton;

    private TextView orderedNumber;
    private TextView orderedPrice;
    private Button orderedButton;
    private ListView lvNOorder;
    private ListView lvYESorder;
    private TextView textview1, textview2;
    private ViewGroup foodorderview;
    public ArrayList<View> pageViews;
    private ViewPager viewPager;
    private ImageView imageView; //动画图片
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("from_findorder");
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        View pageYESorder = inflater.inflate(R.layout.page_ordered, null);
        View pageNOorder = inflater.inflate(R.layout.page_order, null);
        pageViews.add(pageYESorder);
        pageViews.add(pageNOorder);

        lvYESorder = (ListView) pageYESorder.findViewById(R.id.list_ordered);
        lvNOorder = (ListView) pageNOorder.findViewById(R.id.list_order);
        listData = getListData();
        lvYESorder.setAdapter(new CustomAdapter(this, LayoutInflater.from(this), YES_ORDER));
        lvNOorder.setAdapter(new CustomAdapter(this, LayoutInflater.from(this), NO_ORDER));

        orderedNumber = (TextView) pageYESorder.findViewById(R.id.ordered_totoal_number);
        orderedNumber.setText("菜单总数：12");
        orderedPrice = (TextView) pageYESorder.findViewById(R.id.ordered_total_price);
        orderedPrice.setText("订单总价：22");
        orderedButton = (Button) pageYESorder.findViewById(R.id.ordered_total_button);
        orderedButton.setText("结账");
        orderedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodOrderView.this, "您好，老顾客，本次你可享受7折优惠", Toast.LENGTH_SHORT).show();
                new PayTask().execute();
            }
        });

        orderNumber = (TextView) pageNOorder.findViewById(R.id.order_totoal_number);
        orderNumber.setText("菜单总数： 8");
        orderPrice = (TextView) pageNOorder.findViewById(R.id.order_total_price);
        orderPrice.setText("订单总价：240");
        orderButton = (Button) pageNOorder.findViewById(R.id.order_total_button);
        orderButton.setText("提交订单");

        foodorderview = (ViewGroup) inflater.inflate(R.layout.food_order_view, null);
        viewPager = (ViewPager) foodorderview.findViewById(R.id.dishorder_viewpager);
        setContentView(foodorderview);
        InitDishOrderImageView();
        InitTextView();
        viewPager.setAdapter(new MyPagerAdapter());
//        if (getIntent().getIntExtra("page_select", 0) == 1) {
//            viewPager.setCurrentItem(YES_ORDER);
//        } else {
//            viewPager.setCurrentItem(NO_ORDER);
//        }
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    public List<ArrayList<HashMap<String, Object>>> getListData() {
        String[][] itemName = {{"凉拌西红柿", "啤酒鸭", "麻辣小龙虾", "青岛啤酒"}, {"五粮液", "姜汁鲍鱼", "黑椒牛排", "皮蛋"}};

        int[][] itemPrice = {{18, 18, 22, 22}, {32, 32, 44, 44}};

        int[][] itemNumber = {{4, 4, 4, 4}, {2, 2, 2, 2}};

        String[][] itemAddition = {{"好吃", "好吃", "好吃", "很好吃"}, {"美味", "美味", "很美味", "很美味"}};

        List<ArrayList<HashMap<String, Object>>> listData = new ArrayList<ArrayList<HashMap<String, Object>>>();

        HashMap<String, Object> item = null;
        ArrayList<HashMap<String, Object>> itemList = null;
        for (int i = 0; i < 2; i++) {
            itemList = new ArrayList<HashMap<String, Object>>();
            for (int j = 0; j < 4; j++) {
                item = new HashMap<String, Object>();
                item.put("name", itemName[i][j]);
                item.put("price", "价格：" + itemPrice[i][j]);
                item.put("number", "数量：" + itemNumber[i][j]);
                item.put("addition", itemAddition[i][j]);
                itemList.add(item);
            }
            listData.add(itemList);
        }
        return listData;
    }

    class CustomAdapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        int index;

        CustomAdapter(Context context, LayoutInflater layoutInflater, int index) {
            this.context = context;
            this.layoutInflater = layoutInflater;
            this.index = index;
        }

        @Override
        public int getCount() {
            return listData.get(index).size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(index).get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                if (index == NO_ORDER) {
                    convertView = layoutInflater.inflate(R.layout.order_item, null);
                    viewHolder = new ViewHolder();
                    viewHolder.nameView = (TextView) convertView.findViewById(R.id.item_order_name);
                    viewHolder.priceView = (TextView) convertView.findViewById(R.id.item_order_price);
                    viewHolder.numberView = (TextView) convertView.findViewById(R.id.item_order_number);
                    viewHolder.additionView = (TextView) convertView.findViewById(R.id.item_order_addition);
                    convertView.setTag(viewHolder);
                } else if (index == YES_ORDER) {
                    convertView = layoutInflater.inflate(R.layout.ordered_item, null);
                    viewHolder = new ViewHolder();
                    viewHolder.nameView = (TextView) convertView.findViewById(R.id.item_ordered_name);
                    viewHolder.priceView = (TextView) convertView.findViewById(R.id.item_ordered_price);
                    viewHolder.numberView = (TextView) convertView.findViewById(R.id.item_ordered_number);
                    viewHolder.additionView = (TextView) convertView.findViewById(R.id.item_ordered_addition);
                    convertView.setTag(viewHolder);
                }
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameView.setText((String) listData.get(index).get(i).get("name"));
            viewHolder.priceView.setText(listData.get(index).get(i).get("price").toString());
            viewHolder.numberView.setText(listData.get(index).get(i).get("number").toString());
            viewHolder.additionView.setText((String) listData.get(index).get(i).get("addition"));
            return convertView;
        }

        class ViewHolder {
            TextView nameView;
            TextView priceView;
            TextView numberView;
            TextView additionView;
        }

    }

    private void InitTextView() {
        textview1 = (TextView) findViewById(R.id.foodorder_txt_no);
        textview2 = (TextView) findViewById(R.id.foodorder_txt_yes);

        textview1.setOnClickListener(new MyOnClickListener(NO_ORDER));
        textview2.setOnClickListener(new MyOnClickListener(YES_ORDER));

    }

    private void InitDishOrderImageView() {
        imageView = (ImageView) findViewById(R.id.img_findorder_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(pageViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(pageViews.get(position));
            return pageViews.get(position);
        }

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int one = offset * 2 + bmpW;
        int two = one * 2;

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one * index, 0, 0);
            currIndex = index;
            animation.setFillAfter(true);
            animation.setDuration(300);
            imageView.startAnimation(animation);
        }
    }

    class PayTask extends AsyncTask<Void, Integer, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(FoodOrderView.this);
            progressDialog.setTitle("正在付账");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < 6; i++) {
                int payPercent = 100 / 6 * i;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                publishProgress(payPercent);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setMessage(values[0] + "%");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                Toast.makeText(FoodOrderView.this, "付账成功,积分增加", Toast.LENGTH_SHORT).show();
                orderedButton.setEnabled(false);
            }
        }
    }
}
