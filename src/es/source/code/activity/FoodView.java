package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by this.zyq on 2016/6/15.
 *
 * @author this.zyq
 */
public class FoodView extends Activity {
    private ViewPager viewPager;  //页卡内容
    private ImageView imageView; //动画图片
    private TextView textView1, textView2, textView3, textView4;
    private List<View> views; //tab页面列表
    private int offset = 0;
    private int currIndex = 0; //当前页卡编号
    private int bmpW;
    private View view1, view2, view3, view4;//各个页卡
    private int button_status = 0;
//    private List<Food> foodList = new ArrayList<Food>();

//    private String[] colddish_data = {"apple", "banana", "orange", "pear", "cherry"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.food_view);
//        InitListView();

//        initFood();
//        FoodAdapter adapter = new FoodAdapter(FoodView.this, R.layout.food_item, foodList);
//        ListView listView = (ListView) findViewById(R.id.listview_colddish);
//        listView.setAdapter(adapter);
        InitImageView();
        InitTextView();
        InitViewPager();
    }

//    private void initFood() {
//        //优化下，好输入
//        Food apple = new Food(R.id.food_detail_button, "apple", "$:10");
//        foodList.add(apple);
//        Food banana = new Food(R.id.food_detail_button, "banana", "$:12");
//        foodList.add(banana);
//    }


    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.dish_viewpager);
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.lay1, null, false);
        view2 = inflater.inflate(R.layout.lay2, null, false);
        view3 = inflater.inflate(R.layout.lay3, null, false);
        view4 = inflater.inflate(R.layout.lay4, null, false);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化顶部标签
     */
    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.food_txt_colddish);
        textView2 = (TextView) findViewById(R.id.food_txt_hotdish);
        textView3 = (TextView) findViewById(R.id.food_txt_seafood);
        textView4 = (TextView) findViewById(R.id.food_txt_drinks);

        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
        textView4.setOnClickListener(new MyOnClickListener(3));
    }

    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也跟着滑动的效果，这里需要计算一些数据
     * 可以通过fragment教程那看到
     * 这部分有待计算
     */
    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.img_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }


    /**
     * 顶部标签点击监听
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }

    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        //直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int one = offset * 2 + bmpW;  //移动一页的偏移量,比如1->2,或者2->3
        int two = one * 2;   //移动两页的偏移量,比如1直接跳3
        int three = one * 3;  //

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one * index, 0, 0);//显然这个比较简洁，只有一行代码。

            currIndex = index;
            try {
                animation.setFillAfter(true);// True:图片停在动画结束位置
            } catch (Exception e) {
                Toast.makeText(FoodView.this, "error find", Toast.LENGTH_SHORT).show();
            }
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(FoodView.this, "您选择了" + (viewPager.getCurrentItem() + 1) + "页卡", Toast.LENGTH_SHORT).show();
        }
    }

//    /**
//     * 自定义装配器，方便传入foodName,foodPrice,FoodButton
//     */
//    public class FoodAdapter extends ArrayAdapter<Food> {
//        private int resourceId;
//
//        public FoodAdapter(Context context, int textViewResourceId, List<Food> objects) {
//            super(context, textViewResourceId, objects);
////            this.resourceId = resourceId;
//            resourceId = textViewResourceId;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            Food food = getItem(position); //获取当前项的Food实例
//            View view;
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//                viewHolder = new ViewHolder();
//                viewHolder.foodName = (TextView) view.findViewById(R.id.food_detail_name);
//                viewHolder.foodPrice = (TextView) view.findViewById(R.id.food_detail_price);
//                viewHolder.foodButton = (Button) view.findViewById(R.id.food_detail_button);
//                view.setTag(viewHolder); //将ViewHolder存储在View中
//            } else {
//                view = convertView;
//                viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
//            }
//            viewHolder.foodName.setText(food.getName());
//            viewHolder.foodPrice.setText(food.getPrice());
//            viewHolder.foodButton.setTag(food.getButtonId());
//            // add listener for button
//
//            viewHolder.foodButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (button_status == 0) {
//                        Toast.makeText(FoodView.this, "点菜成功", Toast.LENGTH_SHORT).show();
//                        viewHolder.foodButton.setText("退点");
//                        //再点完退点后，变成点菜
//                        button_status = 1;
//                    } else if (button_status == 1) {
//                        Toast.makeText(FoodView.this, "退点成功", Toast.LENGTH_SHORT).show();
//                        viewHolder.foodButton.setText("点菜");
//                        button_status = 0;
//                    }
//                }
//            });
//            return view;
//        }
//
//        class ViewHolder {
//            TextView foodName;
//            TextView foodPrice;
//            Button foodButton;
//        }
//    }
}

///**
// * 定义的Food类，用来实例化菜品
// */
//class Food {
//    private String name;
//    private String price;
//    private int buttonId;
//
//    public Food(int buttonId, String name, String price) {
//        this.buttonId = buttonId;
//        this.name = name;
//        this.price = price;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public int getButtonId() {
//        return buttonId;
//    }
//
//    public String getName() {
//        return name;
//    }
//}



