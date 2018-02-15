
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Withdrawal {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("transfermethod")
    @Expose
    private String transfermethod;
    @SerializedName("withdrawlamount")
    @Expose
    private String withdrawlamount;

    @SerializedName("tobankaccount")
    @Expose
    private String tobankaccount;

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public Withdrawal(String type,String userID,String token,String withdrawlamount,String transfermethod,String tobankaccount)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.tobankaccount = tobankaccount;
        this.transfermethod = transfermethod;
        this.withdrawlamount = withdrawlamount;

    }

}
