package org.lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class AccountRepository implements AbstractRepository<Account> {
    Connection con;
    Statement stmt;

    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public Class<Account> getClassType() {
        return Account.class;
    }

    public AccountRepository() throws Exception {
        this.con = DriverManager.getConnection("jdbc:sqlite:accounts.db");
        this.stmt = this.con.createStatement();

        // create table if it doesn't exist
        this.stmt.execute("CREATE TABLE IF NOT EXISTS account (" +
                "id TEXT PRIMARY KEY," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "created_on TEXT NOT NULL" +
                ")");
    }

    public List<Account> all() throws Exception {
        return AbstractRepository.super.all();
//        List<Account> account = new ArrayList<>();
//        ResultSet res = stmt.executeQuery("SELECT id, username, password, email, created_on FROM account");
//
//        while (res.next()) {
//            long timestamp = res.getLong("created_on");
//            Timestamp timestampObj = new Timestamp(timestamp);
//            account.add(new Account(
//                    res.getString("id"),
//                    res.getString("username"),
//                    res.getString("password"),
//                    res.getString("email"),
//                    timestampObj
//            ));
//        }
//        return account;
    }

    public Account get(String id) throws Exception {
        return AbstractRepository.super.get(id);
//        ResultSet res = stmt.executeQuery(
//                "SELECT id, username, password, email, created_on FROM account WHERE id = '" + id + "'"
//        );
//        if (res.next()) {
//            long timestamp = res.getLong("created_on");
//            Timestamp timestampObj = new Timestamp(timestamp);
//            return new Account(
//                    res.getString("id"),
//                    res.getString("username"),
//                    res.getString("password"),
//                    res.getString("email"),
//                    timestampObj.toString()
//            );
//        }
//        return null;
    }

    @Override
    public void create(Account account) throws Exception {
        AbstractRepository.super.create(account);
//        String sql = "INSERT INTO account (id, username, password, email, created_on) VALUES (?, ?, ?, ?, ?)";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setString(1, account.id);
//        pstmt.setString(2, account.username);
//        pstmt.setString(3, account.password);
//        pstmt.setString(4, account.email);
//        pstmt.setTimestamp(5, account.created_on);
//        pstmt.executeUpdate();
    }

    public void update(Account account) throws Exception {
        AbstractRepository.super.update(account);
//        String sql = "UPDATE account SET username=?, password=?, email=?, created_on=? WHERE id=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setString(1, account.username);
//        pstmt.setString(2, account.password);
//        pstmt.setString(3, account.email);
//        pstmt.setString(4, account.created_on);
//        pstmt.setString(5, account.id);
//        pstmt.executeUpdate();
    }

    public void delete(String id) throws Exception {
        AbstractRepository.super.delete(id);
        // stmt.execute("DELETE FROM account WHERE id = '" + id + "'");
    }
}
