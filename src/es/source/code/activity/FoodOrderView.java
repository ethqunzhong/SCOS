package es.source.code.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import es.source.code.model.User;

import java.util.ArrayList;

/**
 * Created by this.zyq on 2016/6/18.
 *
 * @author this.zyq
 */
public class FoodOrderView extends Activity {
    final int NO_ORDER = 0;
    final int YES_ORDER = 1;
    private int bmpW;
    private int offset = 0;
    private int currIndex = 0; //当前页卡编号
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
        View pageNOorder = inflater.inflate(R.layout.lay1, null);
        View pageYESorder = inflater.inflate(R.layout.lay2, null);
        pageViews.add(pageNOorder);
        pageViews.add(pageYESorder);

        foodorderview = (ViewGroup) inflater.inflate(R.layout.food_order_view, null);
        viewPager = (ViewPager) foodorderview.findViewById(R.id.dishorder_viewpager);
        setContentView(foodorderview);
        InitDishOrderImageView();
        InitTextView();
        viewPager.setAdapter(new MyPagerAdapter());
//        if (getIntent().getIntExtra("page_select",0)==1){
//            viewPager.setCurrentItem(YES_ORDER);
//        }else{
//            viewPager.setCurrentItem(NO_ORDER);
//        }
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

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
}
