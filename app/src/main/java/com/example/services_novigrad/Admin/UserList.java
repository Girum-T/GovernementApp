package com.example.services_novigrad.Admin;

public class UserList {

    private String username;
    private String password;
    private String role;
    private String email;
    private String id;

    //String docId;

    public UserList(){
        //empty constructor needed
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public UserList(String username, String password, String role, String email){
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;

        //this.docId = docId;

    }
}
