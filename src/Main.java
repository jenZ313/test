import java.sql.*;

public class Main {
    public static Connection getConnection() {
        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";//驱动程序名
        String url = "jdbc:MySQL://sql5.freemysqlhosting.net:3306/sql5449780";//url指向要访问的数据库study
        String user = "sql5449780";//MySQL配置时的用户名
        String password = "HNzHR6WEhn";//MySQL配置时的密码
        try {
            // 加载驱动类
            Class.forName(driver);
            // 建立连接
            conn = DriverManager.getConnection(url,
                    user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static boolean ini() {
        return iniGroupTable() && iniTestTable() && iniWordTable() && iniQuestionTable() && iniStudentTable() && iniTeacherTable();

    }

    public static boolean iniStudentTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS STUDENT " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " name VARCHAR(16), " +
                    " pass VARCHAR(16), " +
                    " date Date, " +
                    " email VARCHAR(255), " +
                    " words VARCHAR(1000), " +
                    " groupID VARCHAR(1000), " +
                    " level INT, " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
            System.out.println("Created student table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean iniTeacherTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TEACHER " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " name VARCHAR(16), " +
                    " pass VARCHAR(16), " +
                    " date Date, " +
                    " email VARCHAR(255), " +
                    " groupID VARCHAR(1000), " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
            System.out.println("Created teacher table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean iniGroupTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS STUDYGROUP " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " name VARCHAR(16), " +
                    " creator VARCHAR(16), " +
                    " students VARCHAR(1000), " +
                    " posts VARCHAR(1000), " +
                    " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
            System.out.println("Created group table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean iniWordTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS WORD " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " spelling VARCHAR(255), " +
                    " meaning VARCHAR(255), " +
                    " level INT, " +
                    " PRIMARY KEY ( id )) CHARACTER SET = utf8";
            statement.executeUpdate(sql);
            System.out.println("Created student table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean iniTestTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TEST " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " name VARCHAR(16), " +
                    " author VARCHAR(16), " +
                    " time INT, " +
                    " price INT, " +
                    " questions VARCHAR(1000), " +
                    " PRIMARY KEY ( id )) CHARACTER SET = utf8";
            statement.executeUpdate(sql);
            System.out.println("Created test table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean iniQuestionTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS QUESTION " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " question VARCHAR(1000), " +
                    " answer VARCHAR(1000), " +
                    " PRIMARY KEY ( id )) CHARACTER SET = utf8";
            statement.executeUpdate(sql);
            System.out.println("Created question table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void main(String[] args) {


    }

}