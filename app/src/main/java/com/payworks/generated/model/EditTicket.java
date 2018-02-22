
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditTicket {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("ticketname")
    @Expose
    private String ticketname;
    @SerializedName("ticketprice")
    @Expose
    private String ticketprice;
    @SerializedName("ticketavailable")
    @Expose
    private String ticketavailable;
    @SerializedName("ticketdescription")
    @Expose
    private String ticketdescription;
    @SerializedName("ticketbutton")
    @Expose
    private String ticketbutton;
    @SerializedName("token")
    @Expose
    private String token;

    public EditTicket(String type, String userID, String id,String token, String paidby, String ticketname, String ticketprice, String ticketavailable, String ticketdescription, String ticketbutton)
    {
        this.type = type;
        this.userID = userID;
        this.id = id;
        this.token = token;
        this.paidby = paidby;
        this.ticketname = ticketname;
        this.ticketprice = ticketprice;
        this.ticketavailable = ticketavailable;
        this.ticketdescription = ticketdescription;
        this.ticketbutton = ticketbutton;

    }

}
