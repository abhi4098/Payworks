
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("billtemplateid")
    @Expose
    private String billtemplateid;
    @SerializedName("consumername")
    @Expose
    private String consumername;
    @SerializedName("consumerid")
    @Expose
    private String consumerid;
    @SerializedName("billamount")
    @Expose
    private String billamount;
    @SerializedName("created_date")
    @Expose
    private Object createdDate;
    @SerializedName("paiddate")
    @Expose
    private Object paiddate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_date")
    @Expose
    private Object updatedDate;
    @SerializedName("billtemplatename")
    @Expose
    private String billtemplatename;
    @SerializedName("billtemplatetype")
    @Expose
    private String billtemplatetype;
    @SerializedName("billtemplatefee")
    @Expose
    private String billtemplatefee;
    @SerializedName("addedby")
    @Expose
    private Object addedby;
    @SerializedName("billaccountformat")
    @Expose
    private String billaccountformat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBilltemplateid() {
        return billtemplateid;
    }

    public void setBilltemplateid(String billtemplateid) {
        this.billtemplateid = billtemplateid;
    }

    public String getConsumername() {
        return consumername;
    }

    public void setConsumername(String consumername) {
        this.consumername = consumername;
    }

    public String getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(String consumerid) {
        this.consumerid = consumerid;
    }

    public String getBillamount() {
        return billamount;
    }

    public void setBillamount(String billamount) {
        this.billamount = billamount;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(Object paiddate) {
        this.paiddate = paiddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getBilltemplatename() {
        return billtemplatename;
    }

    public void setBilltemplatename(String billtemplatename) {
        this.billtemplatename = billtemplatename;
    }

    public String getBilltemplatetype() {
        return billtemplatetype;
    }

    public void setBilltemplatetype(String billtemplatetype) {
        this.billtemplatetype = billtemplatetype;
    }

    public String getBilltemplatefee() {
        return billtemplatefee;
    }

    public void setBilltemplatefee(String billtemplatefee) {
        this.billtemplatefee = billtemplatefee;
    }

    public Object getAddedby() {
        return addedby;
    }

    public void setAddedby(Object addedby) {
        this.addedby = addedby;
    }

    public String getBillaccountformat() {
        return billaccountformat;
    }

    public void setBillaccountformat(String billaccountformat) {
        this.billaccountformat = billaccountformat;
    }

}
