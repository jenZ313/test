import java.sql.*;
import java.util.Scanner;

public class mySqlTest {
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

    public static boolean insertStudent(Student student) {
        try {
            //TODO：检查是否已经存在
            //1，得到Connection对象，
            Connection connection = getConnection();
            //2，通过Connection获取一个操作sql语句的对象Statement
            Statement statement = connection.createStatement();
            //3，获取需要传递的参数
            String name = student.getName();
            String pass = student.getPass();
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            //4，写sql语句，参数使用？占位符
            String sql = "insert into student (name,pass,date) VALUE (?,?,?)";
            //5，得到PreparedStatement对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //6，通过PreparedStatement对象设置参数
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            preparedStatement.setDate(3, sqlDate);
            //7，执行sql语句
            preparedStatement.executeUpdate();
            //8，释放资源
            statement.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkStudentLogin(String name, String pass) {
        try {
            //1，得到Connection对象，
            Connection connection = getConnection();
            //2，通过Connection获取一个操作sql语句的对象Statement
            Statement statement = connection.createStatement();
            //3，拼接sql语句
            String sql = "select * from student where name='"+name+"' and pass='"+pass+"'";
            //4，查询，返回的结果放入ResultSet对象中。
            ResultSet resultSet = statement.executeQuery(sql);
            //5.检查是否有匹配项
            boolean returnValue = resultSet.next();
            statement.close();
            connection.close();
            return returnValue;
//            //5，将游标后移一位
//            resultSet.next();
//            //6，获取数据
//            int studentId = resultSet.getInt(1);//第一行的第一列数据，我们知道是id，也知道是int类型，
//            String studentName = resultSet.getString(2);//第二个数据对应name
//            int studentAge = resultSet.getInt(3);//第三个数据对应age
//
//            Student student = new Student();
//            student.setId(studentId);
//            student.setName(studentName);
//            student.setAge(studentAge);
//
//            //7，释放资源
//            statement.close();
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS student " +
                "(id INT UNSIGNED AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " pass VARCHAR(255), " +
                " date Date, " +
                " PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
        System.out.println("Created table in given database...");
    }

    public static void main(String[] args) throws SQLException {
//        createTable();
//        Scanner scanner = new Scanner(System.in);
//        String name = scanner.nextLine();
//        String pass = scanner.nextLine();
//        Student s1 = new Student(name, pass);
//        Boolean b = insertStudent(s1);
//        System.out.println(b);
        System.out.println(checkStudentLogin("testname1","testpass1"));

    }

}