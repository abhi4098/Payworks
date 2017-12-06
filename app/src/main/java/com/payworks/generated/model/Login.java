
package com.payworks.generated.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    public static class LoginDetails {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("token")
        @Expose
        private String token;


        public LoginDetails(String email, String password,String token) {
            this.email = email;
            this.password = password;
            this.token = token;
        }




    }




    public static class LoginResponse {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("auth_token")
        @Expose
        private String authToken;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        @SerializedName("info")
        @Expose
        private String info;

        /**
         *
         * @return
         * The info
         */
        public String getInfo() {
            return info;
        }

        /**
         *
         * @param info
         * The info
         */
        public void setInfo(String info) {
            this.info = info;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The authToken
         */
        public String getAuthToken() {
            return authToken;
        }

        /**
         *
         * @param authToken
         * The auth_token
         */
        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        /**
         *
         * @return
         * The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         *
         * @param phone
         * The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         *
         * @return
         * The createdAt
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         *
         * @param createdAt
         * The created_at
         */
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        /**
         *
         * @return
         * The updatedAt
         */
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         *
         * @param updatedAt
         * The updated_at
         */
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    }


