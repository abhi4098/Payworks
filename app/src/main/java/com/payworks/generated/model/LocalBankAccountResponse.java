
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalBankAccountResponse {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("tokenid")
    @Expose
    private String tokenid;
    @SerializedName("localbanks")
    @Expose
    private List<Localbank> localbanks = null;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public List<Localbank> getLocalbanks() {
        return localbanks;
    }

    public void setLocalbanks(List<Localbank> localbanks) {
        this.localbanks = localbanks;
    }

}
