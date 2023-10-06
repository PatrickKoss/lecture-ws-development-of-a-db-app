package org.lecture;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface AbstractRepository<T> {

    Connection getConnection();

    default List<T> all() throws Exception {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + getClassType().getSimpleName();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(fromResultSet(rs, getClassType()));
            }
        }

        return results;
    }

    Class<T> getClassType();

    default T get(String id) throws Exception {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return fromResultSet(rs, getClassType());
                } else {
                    return null;
                }
            }
        }
    }

    default void create(T entity) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(entity.getClass().getSimpleName());  // Assuming table name is class name
        sql.append(" (");

        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);  // If the field is private
            sql.append(fields[i].getName());
            if (i < fields.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            sql.append("?");
            if (i < fields.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql.toString())) {
            for (int i = 0; i < fields.length; i++) {
                pstmt.setObject(i + 1, fields[i].get(entity));
            }
            pstmt.executeUpdate();
        }
    }

    default void update(T entity) throws Exception {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder sql = new StringBuilder("UPDATE " + entity.getClass().getSimpleName() + " SET ");
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equalsIgnoreCase("id")) { // Assuming "id" is primary key and shouldn't be updated
                fields[i].setAccessible(true);
                sql.append(fields[i].getName()).append(" = ?");
                if (i < fields.length - 1) {
                    sql.append(", ");
                }
            }
        }
        sql.append(" WHERE id = ?"); // Using "id" as primary key for WHERE condition

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql.toString())) {
            int index = 1;
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase("id")) {
                    pstmt.setObject(index++, field.get(entity));
                }
            }
            pstmt.setString(index, (String) entity.getClass().getDeclaredField("id").get(entity));  // Adding WHERE condition value
            pstmt.executeUpdate();
        }
    }

    default void delete(String id) throws Exception {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    default String getTableName() {
        // Assuming the generic type (T) is always the entity
        Entity entityAnnotation = getClassType().getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            return entityAnnotation.tableName();
        } else {
            throw new RuntimeException("Entity annotation missing on the domain class.");
        }
    }


    default T fromResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        T instance = clazz.newInstance();  // Create a new instance of T

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                Object value = rs.getObject(column.name());
                field.setAccessible(true); // If field is private
                field.set(instance, value);
            }
        }

        return instance;
    }
}
