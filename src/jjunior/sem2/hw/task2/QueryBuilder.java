package jjunior.sem2.hw.task2;

import java.lang.reflect.Field;
import java.util.UUID;

public class QueryBuilder {

    public String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder query = new StringBuilder("INSERT INTO ");
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name())
                    .append(" (");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    query.append(columnAnnotation.name()).append(", ");
                }
            }

            // удаление последней запятой и пробела
            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(") VALUES(");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    query.append("'").append(field.get(obj)).append("', ");
                }
            }

            // удаление последней запятой и пробела
            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(")");
            return query.toString();

        } else {
            return null;
        }
    }

    public String buildSelectQuery(Class<?> clazz, UUID primaryKey) {
        StringBuilder query = new StringBuilder("SELECT * FROM ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" WHERE ");
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                    query.append(columnAnnotation.name()).append(" = '").append(primaryKey).append("'");
                    break;
                }
            }
        }

        return query.toString();
    }

    public String buildUpdateQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder query = new StringBuilder("UPDATE ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" SET ");
        }

        if (clazz.isAnnotationPresent(Table.class)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey())
                        continue;
                    query
                            .append(columnAnnotation.name())
                            .append(" = '")
                            .append(field.get(obj))
                            .append("', ");
                }
            }

            // удаление последней запятой и пробела
            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            query.append(" WHERE ");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey()) {
                        query
                                .append(columnAnnotation.name())
                                .append(" = '")
                                .append(field.get(obj))
                                .append("', ");
                        break;
                    }
                }
            }

            // удаление последней запятой и пробела
            if (query.charAt(query.length() - 2) == ',') {
                query.delete(query.length() - 2, query.length());
            }

            return query.toString();

        } else {
            return null;
        }
    }


    /**
     * Запрос на удаление
     * @param clazz
     * @param primaryKey
     * @return
     */
    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey) {
        StringBuilder query = new StringBuilder("DELETE FROM ");

        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query.append(tableAnnotation.name()).append(" WHERE ");
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                    query.append(columnAnnotation.name()).append(" = '").append(primaryKey).append("'");
                    break;
                }
            }
        }

        return query.toString();

    }
}
