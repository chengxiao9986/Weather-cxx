package cn.edu.pku.chengxiaoxiao.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.pku.chengxiaoxiao.app.MyApplication;
import cn.edu.pku.chengxiaoxiao.bean.City;
import cn.edu.pku.chengxiaoxiao.bean.TodayWeather;
import cn.edu.pku.chengxiaoxiao.db.CityDB;

/**
 * Created by QiuYuan
 */
public class focus_city extends Activity implements View.OnClickListener{
    //private TextView edit_city;
    private ImageView edit_city;
    private TextView add_city;
    private ListView city_list;
    ArrayAdapter<String> adapter;
    List<String> cites;
    CityDB mCityDB =  new CityDB(MyApplication.getIntance(),"");
    private static HashMap<String,City> city_number_map = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_city);
//        initCity();
        edit_city = (ImageView) findViewById(R.id.edit_city);
        edit_city.setOnClickListener(this);
        add_city = (TextView)findViewById(R.id.add_city);
        add_city.setOnClickListener(this);
        city_list = (ListView)findViewById(R.id.city_list);
        cites = getCity();
        initCityMap();
        if(cites == null){
            initCity();
            cites = getCity();
        }

        adapter = new ArrayAdapter<String>(focus_city.this,android.R.layout.simple_dropdown_item_1line,cites);
        city_list.setAdapter(adapter);
        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(focus_city.this,"你点击了"+cites.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                String cityName = cites.get(position);
                City city = city_number_map.get(cityName);
                i.putExtra("city", String.valueOf(city));
                setResult(RESULT_OK,i);
                finish();
            }
        });

        registerForContextMenu(city_list);


    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            City city = (City) data.getSerializableExtra("city");
            String cityName = city.getCity();
            String cityNumber = city.getNumber();
            System.out.println("拿到的数据cityName：" + cityName);
            System.out.println("拿到的数据cityNumber:" + cityNumber);
            cites.add(cityName);
            adapter.notifyDataSetChanged();
            saveCity(cites);
        }
    }
    public void initCityMap(){
        city_number_map = new HashMap<String,City>();
        List<City> list = null;
        list = mCityDB.getAllCity();
        Iterator<City> iterator = list.iterator();
        while(iterator.hasNext())
        {
            Map<String,Object> listItem = new HashMap<String,Object>();
            City city = iterator.next();
            String cityName = city.getCity();
            String city_province = city.getProvince();
            city_number_map.put(cityName,city);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        String str = cites.get(position);
        Log.d("MainActivity", item.getItemId() + " ");
        switch (item.getItemId()) {
            case 0:
                Log.d("menuOutput", "详情" + str);
                break;
            case 1:
                Log.d("menuOutput", "删除" + str);
                cites.remove(info.position);
                saveCity(cites);
                break;
        }
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("对城市进行处理");

        menu.add(Menu.NONE, 0, 1,"细节");
        menu.add(Menu.NONE, 1, 1,"删除");
    }

    public void initCity(){
        SharedPreferences.Editor editor = getSharedPreferences("focus_city",MODE_PRIVATE).edit();
        String[] cities = {"北京"};
        Set<String> set = new HashSet<String>();
        Collections.addAll(set,cities);
        editor.putStringSet("cityName",set);
        editor.commit();
    }
    public void saveCity(List<String> list){
        SharedPreferences.Editor editor = getSharedPreferences("focus_city",MODE_PRIVATE).edit();
        Set<String> set = new HashSet<String>();
        for(int i = 0;i<list.size();i++){
            set.add(list.get(i));
        }
        editor.putStringSet("cityName",set);
        editor.commit();
    }

    public List<String> getCity(){
        SharedPreferences sharedPreferences = getSharedPreferences("focus_city",MODE_PRIVATE);
        Set<String> outSet = sharedPreferences.getStringSet("cityName",null);
        List<String> list = new ArrayList<String>();
        if (outSet == null){
            return null;
        }
        for(String city:outSet){
            list.add(city);
        }
        return  list;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_focus_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_city:
//                Intent intent = new Intent(this,SelectCity.class);
//                startActivity(intent);
                Intent intent = new Intent(this,SelectCity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.edit_city:
                finish();
                break;
        }
    }
}
