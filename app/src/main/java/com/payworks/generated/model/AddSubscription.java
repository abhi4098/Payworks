
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSubscription {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("subscriptionname")
    @Expose
    private String subscriptionname;
    @SerializedName("subscriptiondescription")
    @Expose
    private String subscriptiondescription;
    @SerializedName("subscriptionprice")
    @Expose
    private String subscriptionprice;
    @SerializedName("subscriptionduration")
    @Expose
    private String subscriptionduration;
    @SerializedName("subscriptiontrialperiod")
    @Expose
    private String subscriptiontrialperiod;
    @SerializedName("subscriptionsetupfee")
    @Expose
    private String subscriptionsetupfee;
    @SerializedName("subscriptionshipping")
    @Expose
    private String subscriptionshipping;
    @SerializedName("subscriptionbutton")
    @Expose
    private String subscriptionbutton;
    @SerializedName("token")
    @Expose
    private String token;

    public AddSubscription(String type,String userID,String token,String paidby,String subscriptionname,String subscriptionprice,String subscriptionshipping,String subscriptiondescription,String subscriptionbutton,String subscriptionsetupfee,String subscriptiontrialperiod,String subscriptionduration)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.paidby = paidby;
        this.subscriptionname = subscriptionname;
        this.subscriptionprice = subscriptionprice;
        this.subscriptionshipping = subscriptionshipping;
        this.subscriptiondescription = subscriptiondescription;
        this.subscriptionbutton = subscriptionbutton;
        this.subscriptionsetupfee =subscriptionsetupfee;
        this.subscriptiontrialperiod = subscriptiontrialperiod;
        this.subscriptionduration = subscriptionduration;

    }

}
