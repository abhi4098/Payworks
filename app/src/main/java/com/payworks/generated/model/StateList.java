
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateList {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("countryID")
    @Expose
    private String countryID;
    @SerializedName("token")
    @Expose
    private String token;

    public StateList(String type,String token,String countryID)
    {
        this.type = type;
        this.token = token;
        this.countryID = countryID;

    }

}
