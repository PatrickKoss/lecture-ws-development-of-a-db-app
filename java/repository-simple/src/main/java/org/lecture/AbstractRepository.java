package org.lecture;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public interface AbstractRepository<T> {

    Connection getConnection();

    Class<T> getClassType();

    default List<T> all() throws Exception {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " ORDER BY id";

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                results.add(fromResultSet(resultSet));
            }
        }

        return results;
    }

    default T get(int id) throws Exception {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? fromResultSet(resultSet) : null;
            }
        }
    }

    default void create(T entity) throws Exception {
        List<Field> fields = mappedFieldsWithoutId();
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for (Field field : fields) {
            columns.add(field.getAnnotation(Column.class).name());
            placeholders.add("?");
        }

        String sql = "INSERT INTO " + getTableName() +
                " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (int index = 0; index < fields.size(); index++) {
                Field field = fields.get(index);
                field.setAccessible(true);
                statement.setObject(index + 1, field.get(entity));
            }
            statement.executeUpdate();
        }
    }

    default void update(T entity) throws Exception {
        List<Field> fields = mappedFieldsWithoutId();
        StringJoiner assignments = new StringJoiner(", ");

        for (Field field : fields) {
            assignments.add(field.getAnnotation(Column.class).name() + " = ?");
        }

        String sql = "UPDATE " + getTableName() + " SET " + assignments + " WHERE id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(index++, field.get(entity));
            }
            statement.setInt(index, idOf(entity));
            statement.executeUpdate();
        }
    }

    default void delete(int id) throws Exception {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    default String getTableName() {
        Entity entity = getClassType().getAnnotation(Entity.class);
        if (entity == null) {
            throw new IllegalStateException("Entity annotation missing on " + getClassType().getName());
        }
        return entity.tableName();
    }

    private List<Field> mappedFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        for (Field field : getClassType().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && !column.name().equalsIgnoreCase("id")) {
                fields.add(field);
            }
        }
        return fields;
    }

    private int idOf(T entity) throws IllegalAccessException {
        for (Field field : getClassType().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.name().equalsIgnoreCase("id")) {
                field.setAccessible(true);
                return field.getInt(entity);
            }
        }
        throw new IllegalStateException("Column annotation for id missing on " + getClassType().getName());
    }

    private T fromResultSet(ResultSet resultSet) throws Exception {
        T instance = getClassType().getDeclaredConstructor().newInstance();

        for (Field field : getClassType().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                field.set(instance, resultSet.getObject(column.name()));
            }
        }

        return instance;
    }
}
