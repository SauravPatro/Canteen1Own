package com.example.om.canteen1;

/**
 * Created by Om on 2/27/2018.
 */

public class ItemsPOJO {

    String name;
    String price;
    String itemNo;
    String url;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }



    public ItemsPOJO(String name, String price, String itemNo,String url) {
        this.name = name;
        this.price = price;
        this.itemNo=itemNo;
        this.url=url;
    }
    public ItemsPOJO()
    {}


}
