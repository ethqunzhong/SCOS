package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by this.zyq on 2016/6/17.
 *
 * @author this.zyq
 */
public class OrderView extends Activity {
    private int button_status=0;
    private List<Food> foodList=new ArrayList<Food>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.colddish_list);
//        InitListView();
        initFood();
        FoodAdapter adapter=new FoodAdapter(OrderView.this,R.layout.food_item,foodList);
        ListView listView=(ListView) findViewById(R.id.listview_colddish);
        listView.setAdapter(adapter);

    }

    private void initFood(){
        //优化下，好输入
        Food apple=new Food(R.id.food_detail_button,"apple","$:10");
        foodList.add(apple);
        Food banana =new Food(R.id.food_detail_button,"banana","$:12");
        foodList.add(banana);
    }

//    private void InitListView() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                OrderView.this, android.R.layout.simple_list_item_1, colddish_data);
//        ListView listView = (ListView) findViewById(R.id.listview_colddish);
//        listView.setAdapter(adapter);
//    }

    public class FoodAdapter extends ArrayAdapter<Food> {
        private int resourceId;

        public FoodAdapter(Context context, int textViewResourceId, List<Food> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Food food = getItem(position); //获取当前项的Food实例
            View view;
            ViewHolder viewHolder;
            if (convertView==null){
                view=LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder=new ViewHolder();
                viewHolder.foodName=(TextView) view.findViewById(R.id.food_detail_name);
                viewHolder.foodPrice=(TextView) view.findViewById(R.id.food_detail_price);
                viewHolder.foodButton=(Button) view.findViewById(R.id.food_detail_button);
                view.setTag(viewHolder); //将ViewHolder存储在View中
            }else{
                view = convertView;
                viewHolder=(ViewHolder) view.getTag();//重新获取ViewHolder
            }
            viewHolder.foodName.setText(food.getName());
            viewHolder.foodPrice.setText(food.getPrice());
            viewHolder.foodButton.setTag(food.getButtonId());
            // add listener for button

            viewHolder.foodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button_status==0){
                        Toast.makeText(OrderView.this, "点菜成功", Toast.LENGTH_SHORT).show();
                        viewHolder.foodButton.setText("退点");
                        //再点完退点后，变成点菜
                        button_status=1;
                    }else if (button_status==1){
                        Toast.makeText(OrderView.this, "退点成功", Toast.LENGTH_SHORT).show();
                        viewHolder.foodButton.setText("点菜");
                        button_status=0;
                    }
                }
            });
            return view;
        }
        class ViewHolder{
            TextView foodName;
            TextView foodPrice;
            Button foodButton;
        }
    }

    /**
     *
     * @param position
     */
    public void showInfo(int position){

    }
}

class Food {
    private String name;
    private String price;
    private int buttonId;

    public Food(int buttonId, String name, String price) {
        this.buttonId = buttonId;
        this.name = name;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public int getButtonId() {
        return buttonId;
    }

    public String getName() {
        return name;
    }
}