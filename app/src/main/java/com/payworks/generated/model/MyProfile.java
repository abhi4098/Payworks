package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhinandan on 12/12/17.
 */

public class MyProfile {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("userID")
    @Expose
    private String userID;

    @SerializedName("token")
    @Expose
    private String token;

    public MyProfile(String type,String userID,String token) {
        this.type = type;
        this.userID = userID;
        this.token = token;

    }


}
