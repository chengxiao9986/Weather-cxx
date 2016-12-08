package cn.edu.pku.chengxiaoxiao.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.chengxiaoxiao.bean.City;
import cn.edu.pku.chengxiaoxiao.db.CityDB;

/**
 * Created by Jason on 16/10/25.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApp";
    private static Application application;
    private List<City> mCityList = null;
    private CityDB mCityDB = null;
    public static String cityName = "";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mCityDB = openCityDB();
        initCityList();
        getLocationCity();
    }

    public static Application getInstance() {
        return application;
    }

    public CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG, path);
        if (!db.exists()) {
            Log.d("MyApp", "db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                db.getParentFile().mkdir();
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);

    }

    //初始化城市列表
    private void initCityList() {
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }


    public List<City> prepareCityList() {
        mCityList = mCityDB.getAllCity();
        for (City city : mCityList) {
            String province = city.getProvince();
            String cityName = city.getCity();
            // Log.d(TAG,province+"省 "+cityName);
        }
        return mCityList;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void getLocationCity() {
        try {
            String provider = "";
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providerList = locationManager.getProviders(true);
            if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
            } else if (providerList.contains(locationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;
            } else {
                Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();

            }

            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                String currentPosition = "latitude is" + location.getLatitude() + "\n"
                        + " longitude is " + location.getLongitude();
                Log.d("info", currentPosition);
                //构建url过程
                StringBuilder url = new StringBuilder();
                url.append("http://api.map.baidu.com/geocoder/v2/?ak=9nC7IzAT2WaeSDEtIgN5NQi7&location=");
                url.append(location.getLatitude());
                url.append(",");
                url.append(location.getLongitude());
                url.append("&output=json");
                //发送Http请求

                HttpUtil.sendHttpRequest(url.toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject result = jsonObject.getJSONObject("result");
                            JSONObject addressComponent = result.getJSONObject("addressComponent");
                            String city = (String) addressComponent.get("district");
                            cityName = city.substring(0, city.length() - 1);
                            Log.d("info", city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
