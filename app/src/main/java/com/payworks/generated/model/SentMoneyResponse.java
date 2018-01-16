
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SentMoneyResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("sentrequests")
    @Expose
    private List<Sentrequest> sentrequests = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Sentrequest> getSentrequests() {
        return sentrequests;
    }

    public void setSentrequests(List<Sentrequest> sentrequests) {
        this.sentrequests = sentrequests;
    }

}
