//These are gateways

import java.sql.*;

public class Command {
    public final int SUCCESS = 0;
    public final int FAILED = 1;
    public final int USERNAMEALREADYUSED = 2;
    public final int TEACHER = 11;
    public final int STUDENT = 12;

    public String driver = "com.mysql.cj.jdbc.Driver";//驱动程序名
    public String url = "jdbc:MySQL://sql5.freemysqlhosting.net:3306/sql5449780";//url指向要访问的数据库study
    public String user = "sql5449780";//MySQL配置时的用户名
    public String password = "HNzHR6WEhn";//MySQL配置时的密码
    public Connection connection = null;

    public Command() {
        try {
            // 加载驱动类
            Class.forName(driver);
            // 建立连接
            this.connection = DriverManager.getConnection(url,
                    user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int execute() {
        return -1;
    }
}

//String name,String pass -> STUDENT/TEACHER/FAILED
class loginCommand extends Command {
    private String name;
    private String pass;

    public loginCommand(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    @Override
    public int execute() {
        try {
            //check if the user is a student
            String tableName = "STUDENT";
            Statement statement = connection.createStatement();
            String sql = "select * from " + tableName + " where name='" + name + "' and pass='" + pass + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////check if there is a match
            boolean returnValue = resultSet.next();
            statement.close();
            connection.close();
            if (returnValue) {
                return STUDENT;
            }

            //check if the user is a teacher
            tableName = "TEACHER";
            statement = connection.createStatement();
            sql = "select * from " + tableName + " where name='" + name + "' and pass='" + pass + "'";
            resultSet = statement.executeQuery(sql);
            ////check if there is a match
            returnValue = resultSet.next();
            statement.close();
            connection.close();
            if (returnValue) {
                return TEACHER;
            }

            //if not both, return FAILED
            return FAILED;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//String name, String pass, String email, int type(teacher11, student12)-> USERNAMEALREADYUSED/SUCCESS/FAILED
class registerCommand extends Command {
    private String name;
    private String pass;
    private String email;
    private int type;

    public registerCommand(String name, String pass, String email, int type) {
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.type = type;
    }

    @Override
    public int execute() {
        try {
            //check if username already exists
            ////check if name is used by a student
            Statement statement = connection.createStatement();
            String sql = "select * from STUDENT where name='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            //////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return USERNAMEALREADYUSED;
            }
            ////check if name is used by a teacher
            sql = "select * from TEACHER where name='" + name + "'";
            resultSet = statement.executeQuery(sql);
            //////check if there is a match
            returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return USERNAMEALREADYUSED;
            }

            //register
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());//get date
            String groupID = "";//set groupID to []
            ////register as a student:id(auto-generated)|name|pass|date|email|words|groupID|level
            if (type == STUDENT) {
                String words = "";//set words to []
                int level = 1;//set level to 1
                sql = "insert into student (name,pass,date,email,words,groupID,level) VALUE (?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, words);
                preparedStatement.setString(6, groupID);
                preparedStatement.setInt(7, level);
                preparedStatement.executeUpdate();
                statement.close();
                connection.close();
            }
            ////register as a teacher:id|name|pass|date|email|groupID
            else {
                sql = "insert into student (name,pass,date,email,groupID) VALUE (?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, groupID);
                preparedStatement.executeUpdate();
                statement.close();
                connection.close();
            }
            return SUCCESS;


        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//TODO
//int studentID, int groupID -> SUCCESS/FAILED
class quitGroupCommand extends Command {
    int studentID;
    int groupID;

    public quitGroupCommand(int studentID, int groupID) {
        this.studentID = studentID;
        this.groupID = groupID;
    }

    @Override
    public int execute() {
        try {
            //remove group from student
            ////get all the groups joined by the student
            String tableName = "STUDENT";
            int groupIDCol = 7;
            Statement statement = connection.createStatement();
            String sql = "select * from " + tableName + " where id='" + studentID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            resultSet.next();
            String allGroups = resultSet.getString(groupIDCol);//in the form of "1,2,3,4"
            ////remove groupID from the string
            //TODO
            ////rewrite the new string to the database
            //TODO
            //remove student from group
            ////get all the students for the group
            //TODO
            ////remove studentID from the group
            //TODO
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//TODO
//int studentID, int groupID -> SUCCESS/FAILED
class joinGroupCommand extends Command {
    int studentID;
    int groupID;

    public joinGroupCommand(int studentID, int groupID) {
        this.studentID = studentID;
        this.groupID = groupID;
    }

    @Override
    public int execute() {
        try {
            //add group to student
            ////get all the groups joined by the student
            String tableName = "STUDENT";
            int groupIDCol = 7;
            Statement statement = connection.createStatement();
            String sql = "select * from " + tableName + " where id='" + studentID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            resultSet.next();
            String allGroups = resultSet.getString(groupIDCol);//in the form of "1,2,3,4"
            ////add groupID to the string
            //TODO
            ////rewrite the new string to the database
            //TODO
            //add student to group
            ////get all the students for the group
            //TODO
            ////add studentID to the group
            //TODO
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }


    }
}

//int teacherID, String groupName -> SUCCESS/FAILED
class createGroupCommand extends Command {
    String teacherName;
    String groupName;
    public createGroupCommand(String teacherName,String groupName){
        this.teacherName=teacherName;
        this.groupName=groupName;
    }
    @Override
    public int execute() {

    }
}

class deleteGroupCommand extends Command {

    @Override
    public int execute() {

    }
}

class changeNameCommand extends Command {

    @Override
    public int execute() {

    }
}

class createTestCommand extends Command {

    @Override
    public int execute() {

    }
}

class classaddQuestationCommand extends Command {

    @Override
    public int execute() {

    }
}

class releaseMarkCommand extends Command {

    @Override
    public int execute() {

    }
}

class inviteCommand extends Command {

    @Override
    public int execute() {

    }
}

class deleteMemberCommand extends Command {

    @Override
    public int execute() {

    }
}

class createAnnouncementCommand extends Command {

    @Override
    public int execute() {

    }
}

class submitCommand extends Command {

    @Override
    public int execute() {

    }
}

