package cn.edu.pku.chengxiaoxiao.bean;

/**
 * Created by Jason on 16/10/25.
 */

public class City {
    private String province;
    private String city;
    private String number;
    private String firstPY;
    private String allPY;
    private String allFristPY;


    public City(String province,String number,String firstPY,String allPY,String allFristPY){
        this.province=province;
        this.city=city;
        this.number=number;
        this.firstPY=firstPY;
        this.allPY=allPY;
        this.allFristPY=allFristPY;
    }
    public String getProvince(){return province;}
    public String getCity(){return city;}
    public String getNumber(){return number;}
    public String getFirstPY(){return firstPY;}
    public String getAllPY(){return allPY;}
    public String getAllFristPY(){return allFristPY;}

}
