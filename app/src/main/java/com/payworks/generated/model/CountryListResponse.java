
package com.payworks.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryListResponse {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;

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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

}
