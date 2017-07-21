package com.example.zeus.jsonrealm;

import io.realm.RealmObject;


public class KingInfo extends RealmObject {

    private String name;
    private String city;
    private String house;
    private String years;
    private String url;
    private int id;
    private byte[] bytearr=new byte[0];

    public byte[] getBytearr()
    {
        return bytearr;
    }
    public void setBytearr(byte[] bytearr){
        this.bytearr=bytearr;
    }

    public String getUrl()
    {

        return url;
    }
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getCity()
    {
        return city;
    }
    public String getHouse()
    {
        return house;
    }
    public String getYears()
    {
        return years;
    }

    public void setName(String name)
    {
        this.name=name;
    }
    public void setCity(String city)
    {
        this.city=city;
    }
    public void setHouse(String house)
    {
        this.house=house;
    }
    public void setYears(String years)
    {
        this.years=years;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
