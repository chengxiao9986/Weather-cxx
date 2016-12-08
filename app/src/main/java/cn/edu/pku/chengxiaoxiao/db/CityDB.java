package cn.edu.pku.chengxiaoxiao.db;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;


import cn.edu.pku.chengxiaoxiao.bean.City;
/**
 * Created by Jason on 16/11/8.
 */

public class CityDB {
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";
    private SQLiteDatabase db;

    public CityDB(Context context, String path) {
        db = context.openOrCreateDatabase(CITY_DB_NAME,Context.MODE_PRIVATE,null);

    }
    public List<City>getAllCity(){
        List<City>list=new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * FROM "+ CITY_TABLE_NAME,null);
        while (c.moveToNext()){
            String province=c.getString(c.getColumnIndex("province"));
            String city=c.getString(c.getColumnIndex("city"));
            String number=c.getString(c.getColumnIndex("number"));
            String allPY=c.getString(c.getColumnIndex("allpy"));
            String allFirstPY=c.getString(c.getColumnIndex("allfirstpy"));
            String firstPY=c.getString(c.getColumnIndex("firstpy"));
            City item=new City(province,city,number,firstPY,allPY,allFirstPY);
            list.add(item);
        }
        c.close();
        return list;
    }
    public List<City> getSelectCity(String selectedCity){
        List<City> list = new ArrayList<City>();
        //System.out.println("进入筛选选择城市函数");
        //System.out.println("select * from "+CITY_TABLE_NAME+" where city like '%"+selectedCity+"%'");
        Cursor c = db.rawQuery("select * from "+CITY_TABLE_NAME+" where city like '%"+selectedCity+"%'",null);
        while (c.moveToNext())
        {
            //System.out.println("模糊查询查到了数据");
            String province = c.getString(c.getColumnIndex("province"));
            String city = c.getString(c.getColumnIndex("city"));
            String number = c.getString(c.getColumnIndex("number"));
            String allPY = c.getString(c.getColumnIndex("allpy"));
            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));
            String firstPY = c.getString(c.getColumnIndex("firstpy"));
            City cityTemp = new City(province,city,number,allPY,allFirstPY,firstPY);
            list.add(cityTemp);
        }
        c.close();
        return list;
    }


}