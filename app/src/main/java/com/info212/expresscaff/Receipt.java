package com.info212.expresscaff;

/**
 * Created by Edvard on 02.11.2015.
 */
public class Receipt {

    private String nameShop;
    private  String addressShop;
    private String expireShop;
    private String dateShop;
    private String coffeeType1;
    private int coffeePrice;
    private  int totalSum;
    private int barcode;
    private String expireDate;



    public String getNameShop(){
        return nameShop;
    }
    public void setNameShop(String nameShop){
        this.nameShop = nameShop;
    }


    public String getAddress(){
        return addressShop;
    }
    public void setAddress(String addressShop){
        this.addressShop = addressShop;
    }

    public String getExpire(){
        return expireShop;
    }
    public void setExpire(String expireShop){
        this.expireShop = expireShop;
    }

    public String getDate(){
        return dateShop;
    }
    public void setDate(String dateShop) {
        this.dateShop = dateShop;
    }

    public String getCoffeeType1(){
        return coffeeType1;
    }
    public void setCoffeeType1(String coffeeType1) {
        this.coffeeType1 = coffeeType1;
    }

    public int getCoffeePrice(){
        return coffeePrice;
    }
    public void setCoffeePrice(int coffeePrice) {
        this.coffeePrice = coffeePrice;
    }

    public int getTotalSum(){
        return totalSum;
    }
    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }


    public int getBarcode(){
        return barcode;
    }
    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }


    public String getExpireDate(){
        return expireDate;
    }
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

            /*receiptShopName = i.getStringExtra("name");
        receiptShopAddress = i.getStringExtra("shopAddress");
        currentDate = i.getStringExtra("currentDate");
        coffeeType1 = i.getStringExtra("coffee_type1");

        coffeePrice = i.getIntExtra("coffee_price", 0);
        totalSum = i.getIntExtra("total_sum", 0);
        barcode = i.getIntExtra("barcode", 0);*/
}
