
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMoney {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("nameoncard")
    @Expose
    private String nameoncard;
    @SerializedName("cardnumber")
    @Expose
    private String cardnumber;
    @SerializedName("cvvnumber")
    @Expose
    private String cvvnumber;
    @SerializedName("cardtype")
    @Expose
    private String cardtype;
    @SerializedName("cardexpmonth")
    @Expose
    private String cardexpmonth;
    @SerializedName("cardexpyear")
    @Expose
    private String cardexpyear;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("userID")
    @Expose
    private String userID;

    public AddMoney(String type,String token,String amount,String state,String city,String country,String zip,String nameoncard
            ,String cardnumber,String cardexpmonth,String cardexpyear,String cardtype,String cvvnumber,String address,String userID) {
        this.type = type;
        this.amount = amount;
        this.token = token;
        this.nameoncard = nameoncard;
        this.state = state;
        this.city=city;
        this.country = country;
        this.zip = zip;
        this.cardnumber= cardnumber;
        this.cardexpmonth= cardexpmonth;
        this.cardexpyear= cardexpyear;
        this.cardtype= cardtype;
        this.cvvnumber=cvvnumber;
        this.address= address;
        this.userID = userID;

    }
}
