
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMoneyVerified {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("payid")
    @Expose
    private String payid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public SendMoneyVerified(String type,String userID,String token,String payid)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.payid=payid;
    }

}
