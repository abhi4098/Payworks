
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalBankAddition {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("banktype")
    @Expose
    private String banktype;
    @SerializedName("localbankname")
    @Expose
    private String localbankname;
    @SerializedName("branchdropdown")
    @Expose
    private String branchdropdown;
    @SerializedName("accountholder")
    @Expose
    private String accountholder;
    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;
    @SerializedName("accounttype")
    @Expose
    private String accounttype;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("token")
    @Expose
    private String token;

    public LocalBankAddition(String type,String userID,String token, String banktype,String localbankname,String branchdropdown,
                             String accountholder,String accountnumber,String accounttype)
    {
        this.type = type;
        this.userID = userID;
        this.token = token;
        this.accountholder =accountholder;
        this.banktype = banktype;
        this.localbankname =localbankname;
        this.branchdropdown =branchdropdown;
        this.accounttype = accounttype;
        this.accountnumber =accountnumber;

    }
}
