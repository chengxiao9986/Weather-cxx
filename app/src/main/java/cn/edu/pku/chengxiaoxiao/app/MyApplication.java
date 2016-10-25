package cn.edu.pku.chengxiaoxiao.app;

import android.app.Application;
import android.util.Log;
/**
 * Created by Jason on 16/10/25.
 */

public class MyApplication {

    public class MyApplication extends Application{
        private static final String TAG="MyApp";
        @Override
        public void onCreate(){
            super.onCreate();
            Log.d(TAG,"MyApplicaton->Oncreate");
        }
    }

}
