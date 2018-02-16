
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProduct {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("productprice")
    @Expose
    private String productprice;
    @SerializedName("productshipping")
    @Expose
    private String productshipping;
    @SerializedName("productdescription")
    @Expose
    private String productdescription;
    @SerializedName("productbutton")
    @Expose
    private String productbutton;
    @SerializedName("token")
    @Expose
    private String token;

    public AddProduct(String type,String userID,String token,String paidby,String productname,String productprice,String productshipping,String productdescription,String productbutton)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.paidby = paidby;
        this.productname = productname;
        this.productprice = productprice;
        this.productshipping = productshipping;
        this.productdescription = productdescription;
        this.productbutton = productbutton;

    }

}
