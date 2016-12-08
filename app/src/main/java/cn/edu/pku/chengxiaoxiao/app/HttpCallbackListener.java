package cn.edu.pku.chengxiaoxiao.app;

/**
 * Created by QiuYuan
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
