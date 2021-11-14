package Read;

import java.sql.*;

public abstract class Reader implements Readable {

    public final int SUCCESS = 0;
    public final int FAILED = -1;

    public final int INT = 100;
    public final int STRING = 101;

    public String driver = "com.mysql.cj.jdbc.Driver";//驱动程序名
    public String url = "jdbc:MySQL://sql5.freemysqlhosting.net:3306/sql5449780";//url指向要访问的数据库study
    public String user = "sql5449780";//MySQL配置时的用户名
    public String password = "HNzHR6WEhn";//MySQL配置时的密码

    public Connection getConnection() {
        try {
            // 加载驱动类
            Class.forName(driver);
            // 建立连接
            return DriverManager.getConnection(url,
                    user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasDuplicateNames(String table, String name) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from " + table + " where name ='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            boolean hasMatch = resultSet.next();
            if (hasMatch) {
                statement.close();
                connection.close();
                return true;
            }
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return true;
        }

    }

    public Object readInfo(int ID, String table, int col, int type) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from " + table + " where id='" + ID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return FAILED;
            }
            if (type == INT) {
                return resultSet.getInt(col);
            } else {
                return resultSet.getString(col);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return FAILED;
        }

    }

    public Object readInfo(String name, String table, int col, int type) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from " + table + " where name='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return FAILED;
            }
            if (type == INT) {
                return resultSet.getInt(col);
            } else {
                return resultSet.getString(col);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return FAILED;
        }
    }

}

