package cn.edu.pku.chengxiaoxiao.weather;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import cn.edu.pku.chengxiaoxiao.bean.City;
import cn.edu.pku.chengxiaoxiao.bean.DayWeather;
import cn.edu.pku.chengxiaoxiao.bean.TodayWeather;

/**
 * Created by Jason on 16/12/26.
 */

public class MyActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MyActivity";
    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv,
            temperatureTv, climateTv, windTv, titleCity;
    private ImageView weatherImg, pmImg;
    private ImageView mCitySelect;
    private static String currentCity = "北京";
    private static String currentCityNumber = "101010100";
    private ProgressBar pb;
    private ImageView title_share;
    private ImageView title_location;
    private ViewPager forcastWeather;
    private View view1, view2;
    private List<View> viewList;
    TodayWeather todayWeather = null;


    private TextView morningTv, car_washTV, clothesTV, coldTV, comfortTv, dateTV, air_cureTV, sportTV, tourTV, uvTV, umbrellaTV;

    private TextView day1_week, day1_wind, day1_weather, day1_temperature,
            day2_week, day2_wind, day2_weather, day2_temperature,
            day3_week, day3_wind, day3_weather, day3_temperature,
            day4_week, day4_wind, day4_weather, day4_temperature,
            day5_week, day5_wind, day5_weather, day5_temperature,
            day6_week, day6_wind, day6_weather, day6_temperature;
    private ImageView day1_picture, day2_picture, day3_picture,
            day4_picture, day5_picture, day6_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "mainActivity->onCreate");
        setContentView(R.layout.activity_main);
        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);
        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);
        titleCity = (TextView) findViewById(R.id.title_city_name);
        title_share = (ImageView) findViewById(R.id.title_share);
        title_location = (ImageView) findViewById(R.id.title_location);
        initForcastView();
        initView();
        mUpdateBtn.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);


    }

    void initForcastView() {
        forcastWeather = (ViewPager) findViewById(R.id.forcastWeather);
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.forcast_weather1, null);
        view2 = inflater.inflate(R.layout.forcast_weather2, null);
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewList, this);
        forcastWeather.setAdapter(viewPagerAdapter);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            City city = (City) data.getSerializableExtra("city");
            String cityName = city.getCity();
            String cityNumber = city.getNumber();
            System.out.println("拿到的数据cityName：" + cityName);
            System.out.println("拿到的数据cityNumber:" + cityNumber);
            currentCity = cityName;
            currentCityNumber = cityNumber;
        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case UPDATE_TODAY_WEATHER:
                    System.out.println("进入消息传输函数");
                    updateTodayWeather((TodayWeather) message.obj);
                    break;
                default:
                    break;
            }
        }
    };

    void initView() {
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);


//        air_cureTV=(TextView)findViewById(R.id.air_cure);
//        car_washTV=(TextView)findViewById(R.id.car_wash);
//        clothesTV=(TextView)findViewById(R.id.clothes);
//        coldTV=(TextView)findViewById(R.id.cold);
//        comfortTv=(TextView)findViewById(R.id.comfort_level);
//        morningTv=(TextView)findViewById(R.id.morning_exercise);
//        sportTV=(TextView)findViewById(R.id.sport);
//        tourTV=(TextView)findViewById(R.id.tour);
//        umbrellaTV=(TextView)findViewById(R.id.umbrella);
//        dateTV=(TextView)findViewById(R.id.date);
//        uvTV=(TextView)findViewById(R.id.uv);
        morningTv.setOnClickListener(this);
        comfortTv.setOnClickListener(this);
        air_cureTV.setOnClickListener(this);
        car_washTV.setOnClickListener(this);
        clothesTV.setOnClickListener(this);
        coldTV.setOnClickListener(this);
        dateTV.setOnClickListener(this);
        sportTV.setOnClickListener(this);
        tourTV.setOnClickListener(this);
        umbrellaTV.setOnClickListener(this);
        uvTV.setOnClickListener(this);


        //下面6天天气模块内容
        //LayoutInflater inflater = getLayoutInflater();
        //View view1 = inflater.inflate(R.layout.forcast_weather1,null);
        View view1 = viewList.get(0);
        day1_week = (TextView) view1.findViewById(R.id.day1_week);
        day1_temperature = (TextView) view1.findViewById(R.id.day1_temperature);
        day1_weather = (TextView) view1.findViewById(R.id.day1_weather);
        day1_wind = (TextView) view1.findViewById(R.id.day1_wind);
        day1_picture = (ImageView) view1.findViewById(R.id.day1_picture);

        View view2 = viewList.get(0);
        day2_week = (TextView) view2.findViewById(R.id.day2_week);
        day2_temperature = (TextView) view2.findViewById(R.id.day2_temperature);
        day2_weather = (TextView) view2.findViewById(R.id.day2_weather);
        day2_wind = (TextView) view2.findViewById(R.id.day2_wind);
        day2_picture = (ImageView) view2.findViewById(R.id.day2_picture);

        View view3 = viewList.get(0);
        day3_week = (TextView) view3.findViewById(R.id.day3_week);
        day3_temperature = (TextView) view3.findViewById(R.id.day3_temperature);
        day3_weather = (TextView) view3.findViewById(R.id.day3_weather);
        day3_wind = (TextView) view3.findViewById(R.id.day3_wind);
        day3_picture = (ImageView) view3.findViewById(R.id.day3_picture);

        View view4 = viewList.get(1);
        day4_week = (TextView) view4.findViewById(R.id.day4_week);
        day4_temperature = (TextView) view4.findViewById(R.id.day4_temperature);
        day4_weather = (TextView) view4.findViewById(R.id.day4_weather);
        day4_wind = (TextView) view4.findViewById(R.id.day4_wind);
        day4_picture = (ImageView) view4.findViewById(R.id.day4_picture);

        View view5 = viewList.get(1);
        day5_week = (TextView) view5.findViewById(R.id.day5_week);
        day5_temperature = (TextView) view5.findViewById(R.id.day5_temperature);
        day5_weather = (TextView) view5.findViewById(R.id.day5_weather);
        day5_wind = (TextView) view5.findViewById(R.id.day5_wind);
        day5_picture = (ImageView) view5.findViewById(R.id.day5_picture);

        View view6 = viewList.get(1);
        day6_week = (TextView) view6.findViewById(R.id.day6_week);
        day6_temperature = (TextView) view6.findViewById(R.id.day6_temperature);
        day6_weather = (TextView) view6.findViewById(R.id.day6_weather);
        day6_wind = (TextView) view6.findViewById(R.id.day6_wind);
        day6_picture = (ImageView) view6.findViewById(R.id.day6_picture);


        weatherImg = (ImageView) findViewById(R.id.weather_img);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);

        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");


    }

    void updateTodayWeather(TodayWeather todayWeather) {
        System.out.println("进入UpdateTodayWeather:");

        System.out.println("todayWeather.getPM25:  " + todayWeather.getPm25());
        String pmImgStr = "0_50";
        if (todayWeather.getPm25() != null) {
            int pmValue = Integer.parseInt(todayWeather.getPm25().trim());
            System.out.println("pmValue:  " + pmValue);


            if (pmValue > 50 && pmValue < 201) {
                int startV = (pmValue - 1) / 50 * 50 + 1;
                int endV = ((pmValue - 1) / 50 + 1) * 50;
                pmImgStr = Integer.toString(startV) + "_" + endV;
            } else if (pmValue >= 201 && pmValue < 301) {
                pmImgStr = "201_300";
            } else if (pmValue >= 301) {
                pmImgStr = "greater_300";
            }
        }


        Class aClass = R.drawable.class;
        int typeId = -1;
        int pmImgId = -1;
        try {
//一般尽量采用这种形式
            //Field field = aClass.getField(typeImg);
            // Object value = field.get(new Integer(0));
            //typeId = Integer.parseInt(value.toString());
            Field pmField = aClass.getField("biz_plugin_weather_" + pmImgStr);
            Object pmImgO = pmField.get(new Integer(0));
            pmImgId = Integer.parseInt(pmImgO.toString());
        } catch (Exception e) {
            if (-1 == typeId)
                typeId = R.drawable.biz_plugin_weather_qing;
            if (-1 == pmImgId)
                pmImgId = R.drawable.biz_plugin_weather_0_50;
        } finally {
            Drawable drawable = getResources().getDrawable(typeId);
            weatherImg.setImageDrawable(drawable);
            drawable = getResources().getDrawable(pmImgId);
            if (drawable != null) {
                pmImg.setImageDrawable(drawable);
            }

            // Toast.makeText(MyActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }

        Log.d("myWeather3--city", todayWeather.getCity());
        //System.out.println("我在updateTodayWeather这");
        cityTv.setText(todayWeather.getCity());
        //System.out.println("我在updateTodayWeather这11111111111111");


        humidityTv.setText("湿度： " + todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText("今日" + todayWeather.getDate());
        temperatureTv.setText(todayWeather.getWendu());
        climateTv.setText(todayWeather.getType());
        windTv.setText(todayWeather.getFengxiang());


    }


    @Override
    public void onClick(View v) {

    }
}
