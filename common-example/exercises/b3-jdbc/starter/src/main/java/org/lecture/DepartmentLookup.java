package org.lecture;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DepartmentLookup {
    private final Database database;

    public DepartmentLookup(Database database) { this.database = database; }

    public List<String> findCodes() throws SQLException {
        var codes = new ArrayList<String>();
        try (var connection = database.open();
                var statement = connection.prepareStatement("SELECT code FROM departments ORDER BY id");
                var rows = statement.executeQuery()) {
            while (rows.next()) codes.add(rows.getString("code"));
        }
        return codes;
    }
}
