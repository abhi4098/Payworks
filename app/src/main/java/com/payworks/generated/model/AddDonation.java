
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDonation {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("donationname")
    @Expose
    private String donationname;
    @SerializedName("donationprice")
    @Expose
    private String donationprice;
    @SerializedName("donationbutton")
    @Expose
    private String donationbutton;
    @SerializedName("token")
    @Expose
    private String token;

    public AddDonation(String type,String userID,String token,String paidby,String donationname,String donationprice,String donationbutton)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.paidby = paidby;
        this.donationname = donationname;
        this.donationprice = donationprice;
        this.donationbutton = donationbutton;

    }

}
