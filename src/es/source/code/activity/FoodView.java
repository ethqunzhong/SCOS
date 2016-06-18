package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by this.zyq on 2016/6/15.
 *
 * @author this.zyq
 */
public class FoodView extends Activity {
    private List<ArrayList<HashMap<String, Object>>> listData;
    public ArrayList<View> pageViews;

    private ViewPager viewPager;  //ҳ������
    private ImageView imageView; //����ͼƬ
    private TextView textView1, textView2, textView3, textView4;
    private int offset = 0;
    private int currIndex = 0; //��ǰҳ�����
    private int bmpW;

    //    private List<Food> lvHot = new ArrayList<Food>();
    private ListView lvHot;
    private ListView lvCold;
    private ListView lvSeaFood;
    private ListView lvWine;
    private ViewGroup foodview;

    final int HOT = 0;
    final int COLD = 1;
    final int SEAFOOD = 2;
    final int WINE = 3;

    /**
     * ����actionbar��ʾ
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        View pageCold = inflater.inflate(R.layout.colddish_list, null);
        View pageHot = inflater.inflate(R.layout.hotdish_list, null);
        View pageSeaFood = inflater.inflate(R.layout.seafood_list, null);
        View pageWine = inflater.inflate(R.layout.drinks_list, null);
        pageViews.add(pageHot);
        pageViews.add(pageCold);
        pageViews.add(pageSeaFood);
        pageViews.add(pageWine);

//        setContentView(R.layout.colddish_list);
//        setContentView(R.layout.hotdish_list);
//        setContentView(R.layout.seafood_list);
//        setContentView(R.layout.drinks_list);
//

        lvCold = (ListView) pageCold.findViewById(R.id.listview_colddish);
        lvHot = (ListView) pageHot.findViewById(R.id.listview_hotdish);
        lvSeaFood = (ListView) pageSeaFood.findViewById(R.id.listview_seafood);
        lvWine = (ListView) pageWine.findViewById(R.id.listview_drinks);

        if (lvCold == null) {
            Log.d("debug", "lvcold still null");
        }
        listData = getListData();
        lvHot.setAdapter(new FoodAdapter(this, LayoutInflater.from(this), HOT));
        lvSeaFood.setAdapter(new FoodAdapter(this, LayoutInflater.from(this), SEAFOOD));
        lvWine.setAdapter(new FoodAdapter(this, LayoutInflater.from(this), WINE));
        lvCold.setAdapter(new FoodAdapter(this, LayoutInflater.from(this), COLD));


//        lvCold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FoodView.this, (String)listData.get(COLD).get(i).get("name"),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        lvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if ("��¼/ע��".equals(listData.get(HOT).get(i).get("name"))) {
//                    Intent intent = new Intent(FoodView.this,
//                            LoginOrRegister.class);
//                    startActivity(intent);
//
//                }
//                Toast.makeText(FoodView.this, (String)listData.get(HOT).get(i).get("name"),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        lvSeaFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if ("��¼/ע��".equals(listData.get(HOT).get(i).get("name"))) {
//                    Intent intent = new Intent(FoodView.this,
//                            LoginOrRegister.class);
//                    startActivity(intent);
//
//                }
//                Toast.makeText(FoodView.this, (String)listData.get(SEAFOOD).get(i).get("name"),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        lvWine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FoodView.this, (String)listData.get(WINE).get(i).get("name"),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        foodview = (ViewGroup) inflater.inflate(R.layout.food_view, null);
        viewPager = (ViewPager) foodview.findViewById(R.id.dish_viewpager);
        setContentView(foodview);
        /**
         * ֱ��setContentView(R.layout.food_view)�Ļ��ᱨ��ָ�����
         */
//        setContentView(R.layout.food_view);
        InitImageView();
        InitTextView();
        if (pageViews == null) {
            Log.d("debug", "pageViews null");
        }
//        GuidePageAdapter adapter=new GuidePageAdapter();
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    public List<ArrayList<HashMap<String, Object>>> getListData() {
        String[][] itemName = {{"����������", "Ƥ��", "������"},
                {"ơ��Ѽ", "�ڽ�ţ��", "������Ѽ"}, {"����С��Ϻ", "��֭����", "��������"}, {"�ൺơ��", "����Һ", "�ɿڿ���"}};

        int[][] itemPrice = {{20, 15, 10}, {15, 10, 20}, {20, 20, 20}, {30, 20, 20}};

        List<ArrayList<HashMap<String, Object>>> listData = new ArrayList<ArrayList<HashMap<String, Object>>>();

        HashMap<String, Object> item = null;
        ArrayList<HashMap<String, Object>> itemList = null;
        for (int i = 0; i < 4; i++) {
            itemList = new ArrayList<HashMap<String, Object>>();
            for (int j = 0; j < 3; j++) {
                item = new HashMap<String, Object>();
                item.put("name", itemName[i][j]);
                item.put("price", "�۸�" + itemPrice[i][j]);
                itemList.add(item);
            }
            listData.add(itemList);
        }
        return listData;
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

    class GuidePageAdapter extends PagerAdapter {
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
//            Toast.makeText(FoodView.this, "��ѡ����" + (viewPager.getCurrentItem() + 1) + "ҳ��", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * �Զ���װ���������㴫��foodName,foodPrice,FoodButton
     */
    class FoodAdapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        int index;

        FoodAdapter(Context context, LayoutInflater layoutInflater, int index) {
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
                convertView = layoutInflater.inflate(R.layout.food_item, null);
                viewHolder = new ViewHolder();
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.food_detail_name);
                viewHolder.priceView = (TextView) convertView.findViewById(R.id.food_detail_price);
                viewHolder.button = (Button) convertView.findViewById(R.id.food_detail_button);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameView.setText((String) listData.get(index).get(i).get("name"));
            viewHolder.priceView.setText(listData.get(index).get(i).get("price").toString());
            viewHolder.button.setText("���");
            final Button nowButton = (Button) convertView.findViewById(R.id.food_detail_button);
            nowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("���" == nowButton.getText()) {
                        nowButton.setText("�˵�");
                        Toast.makeText(FoodView.this, "��˳ɹ�", Toast.LENGTH_SHORT).show();
                    } else {
                        nowButton.setText("���");
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView nameView;
            TextView priceView;
            Button button;
        }

    }
}




