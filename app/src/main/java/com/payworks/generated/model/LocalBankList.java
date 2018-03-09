
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalBankList {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public LocalBankList(String type,String token)
    {
        this.type = type;
        this.token = token;

    }

}
