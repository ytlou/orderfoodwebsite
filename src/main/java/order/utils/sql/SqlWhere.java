package order.utils.sql;

import lombok.Data;

@Data
public class SqlWhere {
    private StringBuilder sql = new StringBuilder();
    private int count = 0;

    private SqlWhere addString(String name, String value, String symbol) {
        if (name == null || name.isEmpty() || value == null) {
            return this;
        }
        if (!check(name)) {
            return this;
        }
        sql.append(" `").append(name).append("` ")
                .append(symbol)
                .append(" '").append(value).append("' ");
        return this;
    }

    private SqlWhere add(String name, Object value, String symbol) {
        if (name == null || name.isEmpty() || value == null) {
            return this;
        }
        if (value instanceof String && !value.equals("true") && !value.equals("false")) {
            return this;
        }
        if (!check(name)) {
            return this;
        }
        sql.append(" `").append(name).append("` ")
                .append(symbol)
                .append(" ").append(value);
        return this;
    }

    private boolean check(String name) {
        int len = name.length();
        for (int i = 0; i < len; i++) {
            final char c = name.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') {
            } else {
                return false;
            }
        }
        if (count != 0) {
            sql.append(" AND ");
        }
        count++;
        return true;
    }

    public SqlWhere like(String name, String value) {
        if (name != null && value != null) {
            return addString(name, "%" + value + "%", "like");
        }
        return this;
    }

    public SqlWhere likeLeft(String name, String value) {
        if (name != null && value != null) {
            return addString(name, value + "%", "like");
        }
        return this;
    }

    public SqlWhere likeRight(String name, String value) {
        if (name != null && value != null) {
            return addString(name, "%" + value, "like");
        }
        return this;
    }


    public SqlWhere eq(String name, Object value) {
        if (name != null) {
            return add(name, value, "=");
        }
        return this;
    }

    public SqlWhere gt(String name, Object value) {
        if (name != null) {
            return add(name, value, ">");
        }
        return this;
    }

    public SqlWhere lt(String name, Object value) {
        if (name != null) {
            return add(name, value, "<");
        }
        return this;
    }

    public SqlWhere ge(String name, Object value) {
        if (name != null) {
            return add(name, value, ">=");
        }
        return this;
    }

    public SqlWhere le(String name, Object value) {
        if (name != null) {
            return add(name, value, "<=");
        }
        return this;
    }


    public String getSql() {
        if (count == 0) {
            return "";
        }
        return "WHERE(" + sql.toString() + ")";
    }

    public String getSql(int i, int size) {
        return getSql() + " LIMIT " + (i - 1) * size + "," + size + " ";
    }
}
