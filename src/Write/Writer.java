
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Writer implements Writable {
    @Override
    public abstract Object set();

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

}
