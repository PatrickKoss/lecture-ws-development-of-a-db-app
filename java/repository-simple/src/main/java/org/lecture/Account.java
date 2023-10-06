package org.lecture;

@Entity(tableName = "account")
public class Account {
    @Column(name = "id")
    public String id;
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    @Column(name = "email")
    public String email;
    @Column(name = "created_on")
    public String created_on;

    public Account(String id, String username, String password, String email, String created_on) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.created_on = created_on;
    }

    // default constructor is required for reflection
    public Account() {
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
