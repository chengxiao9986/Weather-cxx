package cn.edu.pku.chengxiaoxiao.bean;

/**
 * Created by Jason on 16/10/22.
 */

public class TodayWeather {




    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;



    public String getType() {
        return type;
    }

    public String getLow() {

        return low;
    }


    public String getHigh() {

        return high;
    }
    public String getCity() {
        return city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public String getQuality() {
        return quality;
    }

    public String getFengxiang(){
        return fengxiang;
    }
    public String getFengli() {
        return fengli;
    }
    public String getDate() {
        return date;
    }



    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setFengxiang(String fengxiang){
        this.fengxiang=fengxiang;
    }
    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setShidu(String shidu) {

        this.shidu = shidu;
    }

    public void setWendu(String wendu) {

        this.wendu = wendu;
    }

    public void setUpdatetime(String updatetime) {

        this.updatetime = updatetime;
    }





    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ",updatetime='" + updatetime + '\'' +
                ",shidu='" + shidu + '\'' +
                ",pm25='" + pm25 + '\'' +
                ",quality='" + quality + '\'' +
                ",fengxiang='" + fengxiang + '\'' +
                ",fengli='" + fengli + '\'' +
                ",date='" + date + '\'' +
                ",high='" + high + '\'' +
                ",low='" + low + '\'' +
                ",type='" + type + '\'' +
                '}';
    }

}
