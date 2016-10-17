package cn.edu.pku.chengxiaoxiao.util;

import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;


public class NetUtil {
    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager)
                context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORN_NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            return NETWORN_WIFI;
        }

        return NETWORN_NONE;
    }
}
