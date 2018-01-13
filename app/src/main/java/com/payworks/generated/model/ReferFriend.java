
package com.payworks.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferFriend {

    @SerializedName("email1")
    @Expose
    private String email1;
    @SerializedName("email2")
    @Expose
    private String email2;
    @SerializedName("email3")
    @Expose
    private String email3;
    @SerializedName("email4")
    @Expose
    private String email4;
    @SerializedName("email5")
    @Expose
    private String email5;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("type")
    @Expose
    private String type;

   public ReferFriend(String email1,String email2,String email3,String email4,String email5,String userID,String token, String type)
   {
   this.email1 = email1;
       this.email2 = email2;
       this.email3 = email3;
       this.email4 = email4;
       this.email5 = email5;
       this.userID = userID;
       this.token = token;
       this.type = type;
   }

}
