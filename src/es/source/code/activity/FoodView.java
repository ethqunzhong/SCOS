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
    private ViewPager viewPager;  //ҳ������
    private ImageView imageView; //����ͼƬ
    private TextView textView1, textView2, textView3, textView4;
    private List<View> views; //tabҳ���б�
    private int offset = 0;
    private int currIndex = 0; //��ǰҳ�����
    private int bmpW;
    private View view1, view2, view3, view4;//����ҳ��
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
//        //�Ż��£�������
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
     * ��ʼ��������ǩ
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
     * ��ʼ���������������ҳ������ʱ������ĺ���Ҳ���Ż�����Ч����������Ҫ����һЩ����
     * ����ͨ��fragment�̳��ǿ���
     * �ⲿ���д�����
     */
    private void InitImageView() {
        imageView = (ImageView) findViewById(R.id.img_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.line).getWidth();// ��ȡͼƬ���
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
        offset = (screenW / 4 - bmpW) / 2;// ����ƫ����
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// ���ö�����ʼλ��
    }


    /**
     * ������ǩ�������
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
            this.mListViews = mListViews;//���췽�������������ǵ�ҳ���������ȽϷ��㡣
        }

        //ֱ�Ӽ̳�PagerAdapter�����ٱ�����д������ĸ�����������ᱨ��
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//ɾ��ҳ��
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //�����������ʵ����ҳ��
            container.addView(mListViews.get(position), 0);//���ҳ��
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//����ҳ��������
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//�ٷ���ʾ����д
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int one = offset * 2 + bmpW;  //�ƶ�һҳ��ƫ����,����1->2,����2->3
        int two = one * 2;   //�ƶ���ҳ��ƫ����,����1ֱ����3
        int three = one * 3;  //

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one * index, 0, 0);//��Ȼ����Ƚϼ�ֻ࣬��һ�д��롣

            currIndex = index;
            try {
                animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
            } catch (Exception e) {
                Toast.makeText(FoodView.this, "error find", Toast.LENGTH_SHORT).show();
            }
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(FoodView.this, "��ѡ����" + (viewPager.getCurrentItem() + 1) + "ҳ��", Toast.LENGTH_SHORT).show();
        }
    }

//    /**
//     * �Զ���װ���������㴫��foodName,foodPrice,FoodButton
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
//            Food food = getItem(position); //��ȡ��ǰ���Foodʵ��
//            View view;
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//                viewHolder = new ViewHolder();
//                viewHolder.foodName = (TextView) view.findViewById(R.id.food_detail_name);
//                viewHolder.foodPrice = (TextView) view.findViewById(R.id.food_detail_price);
//                viewHolder.foodButton = (Button) view.findViewById(R.id.food_detail_button);
//                view.setTag(viewHolder); //��ViewHolder�洢��View��
//            } else {
//                view = convertView;
//                viewHolder = (ViewHolder) view.getTag();//���»�ȡViewHolder
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
//                        Toast.makeText(FoodView.this, "��˳ɹ�", Toast.LENGTH_SHORT).show();
//                        viewHolder.foodButton.setText("�˵�");
//                        //�ٵ����˵�󣬱�ɵ��
//                        button_status = 1;
//                    } else if (button_status == 1) {
//                        Toast.makeText(FoodView.this, "�˵�ɹ�", Toast.LENGTH_SHORT).show();
//                        viewHolder.foodButton.setText("���");
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
// * �����Food�࣬����ʵ������Ʒ
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



