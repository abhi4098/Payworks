
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalBankBranchResponse {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("branches")
    @Expose
    private List<Branch> branches = null;

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

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

}
