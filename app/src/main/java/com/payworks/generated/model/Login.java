
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
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("roles_id")
        @Expose
        private String rolesId;
        @SerializedName("role_parent")
        @Expose
        private String roleParent;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private Object phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("login")
        @Expose
        private String login;
        @SerializedName("login_date")
        @Expose
        private String loginDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRolesId() {
            return rolesId;
        }

        public void setRolesId(String rolesId) {
            this.rolesId = rolesId;
        }

        public String getRoleParent() {
            return roleParent;
        }

        public void setRoleParent(String roleParent) {
            this.roleParent = roleParent;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }
    }
    }


