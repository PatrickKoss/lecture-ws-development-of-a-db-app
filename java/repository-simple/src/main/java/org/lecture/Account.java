package org.lecture;

import java.sql.Timestamp;

public class Account {
    public String id;
    public String username;
    public String password;
    public String email;
    public Timestamp created_on;

    public Account(String id, String username, String password, String email, Timestamp created_on) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.created_on = created_on;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", created_on=" + created_on +
                '}';
    }
}
