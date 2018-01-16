
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankAccountResponse {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("userbankaccounts")
    @Expose
    private List<Userbankaccount> userbankaccounts = null;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Userbankaccount> getUserbankaccounts() {
        return userbankaccounts;
    }

    public void setUserbankaccounts(List<Userbankaccount> userbankaccounts) {
        this.userbankaccounts = userbankaccounts;
    }

}
