
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiveMoneyRequests {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("token")
    @Expose
    private String token;

    public ReceiveMoneyRequests(String type,String userID,String token)
    {
        this.type = type;
        this.token=token;
        this.userID=userID;
    }

}
