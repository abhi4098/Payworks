
package com.payworks.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalBankBranch {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bankid")
    @Expose
    private String bankid;
    @SerializedName("token")
    @Expose
    private String token;

    public LocalBankBranch(String type,String token,String bankid)
    {
        this.type = type;
        this.token = token;
        this.bankid = bankid;

    }


}
