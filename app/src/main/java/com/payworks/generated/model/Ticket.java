
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ticketname")
    @Expose
    private String ticketname;
    @SerializedName("ticketdescription")
    @Expose
    private String ticketdescription;
    @SerializedName("ticketprice")
    @Expose
    private String ticketprice;
    @SerializedName("tickettax")
    @Expose
    private String tickettax;
    @SerializedName("ticketreturnurl")
    @Expose
    private String ticketreturnurl;
    @SerializedName("ticketnotifyurl")
    @Expose
    private String ticketnotifyurl;
    @SerializedName("ticketcancelurl")
    @Expose
    private String ticketcancelurl;
    @SerializedName("ticketbutton")
    @Expose
    private String ticketbutton;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("ticketavailable")
    @Expose
    private String ticketavailable;
    @SerializedName("sold")
    @Expose
    private Object sold;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketname() {
        return ticketname;
    }

    public void setTicketname(String ticketname) {
        this.ticketname = ticketname;
    }

    public String getTicketdescription() {
        return ticketdescription;
    }

    public void setTicketdescription(String ticketdescription) {
        this.ticketdescription = ticketdescription;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getTickettax() {
        return tickettax;
    }

    public void setTickettax(String tickettax) {
        this.tickettax = tickettax;
    }

    public String getTicketreturnurl() {
        return ticketreturnurl;
    }

    public void setTicketreturnurl(String ticketreturnurl) {
        this.ticketreturnurl = ticketreturnurl;
    }

    public String getTicketnotifyurl() {
        return ticketnotifyurl;
    }

    public void setTicketnotifyurl(String ticketnotifyurl) {
        this.ticketnotifyurl = ticketnotifyurl;
    }

    public String getTicketcancelurl() {
        return ticketcancelurl;
    }

    public void setTicketcancelurl(String ticketcancelurl) {
        this.ticketcancelurl = ticketcancelurl;
    }

    public String getTicketbutton() {
        return ticketbutton;
    }

    public void setTicketbutton(String ticketbutton) {
        this.ticketbutton = ticketbutton;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTicketavailable() {
        return ticketavailable;
    }

    public void setTicketavailable(String ticketavailable) {
        this.ticketavailable = ticketavailable;
    }

    public Object getSold() {
        return sold;
    }

    public void setSold(Object sold) {
        this.sold = sold;
    }

    public String getPaidby() {
        return paidby;
    }

    public void setPaidby(String paidby) {
        this.paidby = paidby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
