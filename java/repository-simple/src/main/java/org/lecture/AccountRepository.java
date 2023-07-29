package org.lecture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements AbstractRepository {
    Connection con;
    Statement stmt;

    public AccountRepository() throws Exception {
        this.con = DriverManager.getConnection("jdbc:sqlite:accounts.db");
        this.stmt = this.con.createStatement();

        // create table if it doesn't exist
        this.stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                "user_id TEXT PRIMARY KEY," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "created_on TEXT NOT NULL" +
                ")");
    }

    public List<Account> all() throws Exception {
        List<Account> accounts = new ArrayList<Account>();
        ResultSet res = stmt.executeQuery("SELECT user_id, username, password, email, created_on FROM accounts");

        while (res.next()) {
            long timestamp = res.getLong("created_on");
            Timestamp timestampObj = new Timestamp(timestamp);
            accounts.add(new Account(
                    res.getString("user_id"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("email"),
                    timestampObj
            ));
        }
        return accounts;
    }

    public Account get(String id) throws Exception {
        ResultSet res = stmt.executeQuery(
                "SELECT user_id, username, password, email, created_on FROM accounts WHERE user_id = '" + id + "'"
        );
        if (res.next()) {
            long timestamp = res.getLong("created_on");
            Timestamp timestampObj = new Timestamp(timestamp);
            return new Account(
                    res.getString("user_id"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("email"),
                    timestampObj
            );
        }
        return null;
    }

    public void create(Account account) throws Exception {
        String sql = "INSERT INTO accounts (user_id, username, password, email, created_on) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, account.id);
        pstmt.setString(2, account.username);
        pstmt.setString(3, account.password);
        pstmt.setString(4, account.email);
        pstmt.setTimestamp(5, account.created_on);
        pstmt.executeUpdate();
    }

    public void update(Account account) throws Exception {
        String sql = "UPDATE accounts SET username=?, password=?, email=?, created_on=? WHERE user_id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, account.username);
        pstmt.setString(2, account.password);
        pstmt.setString(3, account.email);
        pstmt.setTimestamp(4, account.created_on);
        pstmt.setString(5, account.id);
        pstmt.executeUpdate();
    }

    public void delete(String id) throws Exception {
        stmt.execute("DELETE FROM accounts WHERE user_id = '" + id + "'");
    }
}
