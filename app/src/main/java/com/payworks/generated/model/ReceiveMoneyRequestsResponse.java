
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiveMoneyRequestsResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("receivedrequests")
    @Expose
    private List<Receivedrequest> receivedrequests = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Receivedrequest> getReceivedrequests() {
        return receivedrequests;
    }

    public void setReceivedrequests(List<Receivedrequest> receivedrequests) {
        this.receivedrequests = receivedrequests;
    }

}
