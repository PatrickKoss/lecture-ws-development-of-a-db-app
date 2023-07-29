package org.lecture;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:accounts.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                Statement stmt = conn.createStatement();

                String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                        "user_id text PRIMARY KEY," +
                        "username text UNIQUE NOT NULL," +
                        "password text NOT NULL," +
                        "email text UNIQUE NOT NULL," +
                        "created_on text NOT NULL," +
                        "last_login text" +
                        ");";

                stmt.execute(sql);

                ResultSet rs = stmt.executeQuery("SELECT * FROM accounts;");

                while (rs.next()) {
                    System.out.println(rs.getString("user_id") + "\t" +
                            rs.getString("username") + "\t" +
                            rs.getString("password") + "\t" +
                            rs.getString("email") + "\t" +
                            rs.getString("created_on") + "\t" +
                            rs.getString("last_login"));
                }

                try {
                    sql = "INSERT INTO accounts (user_id, username, password, email, created_on) " +
                            "VALUES ('c996c60b-c617-4c05-8eca-e3391a9b495e', 'test', 'test', 'test@test.com', '2022-10-10T13:10:11Z');";
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println("db table entry test@test.com already exists and therefore ignored");
                }

                rs = stmt.executeQuery("SELECT * FROM accounts;");

                while (rs.next()) {
                    System.out.println(rs.getString("user_id") + "\t" +
                            rs.getString("username") + "\t" +
                            rs.getString("password") + "\t" +
                            rs.getString("email") + "\t" +
                            rs.getString("created_on") + "\t" +
                            rs.getString("last_login"));
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
