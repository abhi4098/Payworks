
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddInvoice {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("paidby")
    @Expose
    private String paidby;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_email")
    @Expose
    private String companyEmail;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("invoicenumber")
    @Expose
    private String invoicenumber;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("invoicebutton")
    @Expose
    private String invoicebutton;
    @SerializedName("token")
    @Expose
    private String token;

    public AddInvoice(String type,String userID,String token,String paidby,String customerName,String amount,String companyName,String companyEmail,String invoicenumber, String notes,String invoicebutton)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.paidby = paidby;
        this.customerName = customerName;
        this.amount = amount;
        this.companyName =companyName;
        this.companyEmail = companyEmail;
        this.invoicenumber = invoicenumber;
        this.notes = notes;
        this.invoicebutton = invoicebutton;

    }

}
