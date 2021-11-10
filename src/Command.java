//These are gateways

import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Command {
    public final int SUCCESS = 0;
    public final int FAILED = 1;
    public final int USERNAMEALREADYUSED = 2;
    public final int GROUPALREADYJOINED = 3;
    public final int NOTREGISTERED = 4;
    public final int TESTNAMEALREADYUSED = 5;
    public final int QUESTIONNAMEALREADYUSED = 6;
    public final int TEACHER = 11;
    public final int STUDENT = 12;

    public final String STUDENTTABLENAME = "STUDENT";
    public final String TEACHERTABLENAME = "TEACHER";
    public final String GROUPTABLENAME = "STUDYGROUP";


    public final int STUDENTIDCOLINGROUP = 4;
    public final int GROUPIDCOLINTEACHER = 6;
    public final int GROUPIDCOLINSTUDENT = 7;
    public final int EMAILINSTUDENT = 5;
    public final int EMAILINTEACHER = 5;

    public String driver = "com.mysql.cj.jdbc.Driver";//驱动程序名
    public String url = "jdbc:MySQL://sql5.freemysqlhosting.net:3306/sql5449780";//url指向要访问的数据库study
    public String user = "sql5449780";//MySQL配置时的用户名
    public String password = "HNzHR6WEhn";//MySQL配置时的密码
    public Connection connection = null;

    public Command() {
        getConnection();
    }

    public void getConnection() {
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

    public int removeGroupFromUser(int userID, int groupID, int userType, Connection connection, Statement statement) {
        String table;
        int col;
        String updateSql;
        if (userType == STUDENT) {
            table = STUDENTTABLENAME;
            col = GROUPIDCOLINSTUDENT;
            updateSql = "update STUDENT set groupID = ? where id = ?";

        } else {
            table = TEACHERTABLENAME;
            col = GROUPIDCOLINTEACHER;
            updateSql = "update TEACHER set groupID = ? where id = ?";
        }
        try {
            ////get all the groups creates by the user
            String sql = "select * from " + table + " where id='" + userID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups created by the user
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allGroups = resultSet.getString(col);//in the form of "21,20,23,4"
            ////remove groupID from the string
            int index = allGroups.indexOf(groupID + "");
            //////if group not found, return failed
            if (index == -1) {
                statement.close();
                connection.close();
                return FAILED;
            }
            int IDLength = (groupID + "").length();
            String newGroups;
            if (allGroups.length() == IDLength) {
                newGroups = "";
            } else {
                newGroups = allGroups.substring(0, index) + allGroups.substring(index + IDLength + 1);
            }
            ////rewrite the new string to the database
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, newGroups);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return FAILED;
        }
    }

    public int removeUserfromGroup(int userID, int groupID, Connection connection, Statement statement) {
        String table = GROUPTABLENAME;
        int col = 4;
        String updateSql = "update STUDYGROUP set students = ? where id = ?";
        try {
            ////get all the users in this group
            String sql = "select * from " + table + " where id='" + groupID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of users in this group
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allusers = resultSet.getString(col);//in the form of "21,20,23,4"
            ////remove groupID from the string
            int index = allusers.indexOf(userID + "");
            //////if group not found, return failed
            if (index == -1) {
                statement.close();
                connection.close();
                return FAILED;
            }
            int IDLength = (userID + "").length();
            String newusers;
            if (allusers.length() == IDLength) {
                newusers = "";
            } else {
                newusers = allusers.substring(0, index) + allusers.substring(index + IDLength);
            }
            ////rewrite the new string to the database
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, newusers);
            preparedStatement.setInt(2, groupID);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return FAILED;
        }
    }

    public int setRandomPass(int userType, String username, int pass, Statement statement, Connection connection) {
        try {
            String table;
            int col;
            String updateSql;
            if (userType == STUDENT) {
                table = STUDENTTABLENAME;
                col = GROUPIDCOLINSTUDENT;
                updateSql = "update STUDENT set pass = ? where name = ?";

            } else {
                table = TEACHERTABLENAME;
                col = GROUPIDCOLINTEACHER;
                updateSql = "update TEACHER set pass = ? where name = ?";
            }


            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, pass + "");
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return FAILED;
        }


    }

    public void sendMail(String email, String pass) {

        // 收件人电子邮箱
        String to = email;

        // 发件人电子邮箱
        String from = "zhanni2020@gmail.com";

        // 指定发送邮件的主机为 localhost
        String host = "localhost";

        // 获取系统属性
        Properties properties = System.getProperties();


        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties);

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("Password Changed");

            // 设置消息体
            message.setText("Your password is now: " + pass+".");

            // 发送消息
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


    }

    public int execute() {
        return -1;
    }
}

//String name,String pass -> an id/FAILED
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
            getConnection();
            //check if the user is a student
            Statement statement = connection.createStatement();
            String sql = "select * from " + STUDENTTABLENAME + " where name='" + name + "' and pass='" + pass + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                int studentID = resultSet.getInt(1);
                statement.close();
                connection.close();
                return studentID;
            }

            //check if the user is a teacher
            statement = connection.createStatement();
            sql = "select * from " + TEACHERTABLENAME + " where name='" + name + "' and pass='" + pass + "'";
            resultSet = statement.executeQuery(sql);
            ////check if there is a match
            returnValue = resultSet.next();
            if (returnValue) {
                int teacherID = resultSet.getInt(1);
                statement.close();
                connection.close();
                return teacherID;
            }
            //if not both, return FAILED
            statement.close();
            connection.close();
            return FAILED;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//int id -> STUDENT/TEACHER/FAILED
class checkIdentity extends Command {
    int id;

    public checkIdentity(int id) {
        this.id = id;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            //check if id appears in student table
            Statement statement = connection.createStatement();
            String sql = "select * from STUDENT where id='" + id + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            //////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return STUDENT;
            }
            ////check if id appears in teacher table
            sql = "select * from TEACHER where id='" + id + "'";
            resultSet = statement.executeQuery(sql);
            //////check if there is a match
            returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return TEACHER;
            }
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
            getConnection();
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
            String testID = "";// set testID to 0 refers no test
            ////register as a student:id(auto-generated)|name|pass|date|email|words|groupID|level
            if (type == STUDENT) {
                String words = "";//set words to []
                int level = 1;//set level to 1
                sql = "insert into STUDENT (name,pass,date,email,words,groupID,testID,level) VALUE (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, words);
                preparedStatement.setString(6, groupID);
                preparedStatement.setString(7, testID);
                preparedStatement.setInt(8, level);
                preparedStatement.executeUpdate();
                statement.close();
                connection.close();
            }
            ////register as a teacher:id|name|pass|date|email|groupID
            else {
                sql = "insert into TEACHER (name,pass,date,email,groupID,testID) VALUE (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, groupID);
                preparedStatement.setString(6, testID);
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
            getConnection();
            Statement statement = connection.createStatement();
            //remove group from student
            int result = removeGroupFromUser(studentID, groupID, STUDENT, connection, statement);
            if (result != SUCCESS) {
                return FAILED;
            }
            //remove student from group
            ////get all the students for the group
            String sql = "select * from " + GROUPTABLENAME + " where id='" + groupID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allStudents = resultSet.getString(STUDENTIDCOLINGROUP);//in the form of "21,20,23,4"
            ////remove studentID from the string
            int index = allStudents.indexOf(studentID + "");
            //////if student not found, return failed
            if (index == -1) {
                statement.close();
                connection.close();
                return FAILED;
            }
            ////remove studentID from the group
            int IDLength = (studentID + "").length();
            String newStudents;
            if (allStudents.length() == IDLength) {
                newStudents = "";
            } else {
                newStudents = allStudents.substring(0, index) + allStudents.substring(index + IDLength + 1);
            }
            ////rewrite the new string to the database
            sql = "update STUDYGROUP set students = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStudents);
            preparedStatement.setInt(2, groupID);
            preparedStatement.executeUpdate();

            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}


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
            getConnection();
            //add group to student
            ////get all the groups joined by the student
            Statement statement = connection.createStatement();
            String sql = "select * from " + STUDENTTABLENAME + " where id='" + studentID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            resultSet.next();
            String allGroups = resultSet.getString(GROUPIDCOLINSTUDENT);//in the form of "1,2,3,4"
            ////add groupID to the string
            if (allGroups.indexOf(groupID + "") != -1) {
                statement.close();
                connection.close();
                return GROUPALREADYJOINED;
            }
            if (allGroups.length() == 0) {
                allGroups = groupID + "";
            } else {
                allGroups = allGroups + "," + groupID;
            }
            ////rewrite the new string to the database
            sql = "update STUDENT set groupID = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, allGroups);
            preparedStatement.setInt(2, studentID);
            preparedStatement.executeUpdate();


            //add student to group
            ////get all the students form the group
            sql = "select * from " + GROUPTABLENAME + " where id='" + groupID + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            String allStudents = resultSet.getString(STUDENTIDCOLINGROUP);
            ////add studentID to the group
            if (allStudents.length() == 0) {
                allStudents = studentID + "";
            } else {
                allStudents = allStudents + "," + studentID;
            }
            ////rewrite the new string to the database
            sql = "update STUDYGROUP set students = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, allStudents);
            preparedStatement.setInt(2, groupID);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }


    }
}

//int teacherID, String groupName -> GroupID/FAILED
class createGroupCommand extends Command {
    int teacherID;
    String groupName;

    public createGroupCommand(int teacherID, String groupName) {
        this.teacherID = teacherID;
        this.groupName = groupName;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            //check if group already exists
            Statement statement = connection.createStatement();
            String sql = "select * from STUDYGROUP where name='" + groupName + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            //////check if there is a match
            boolean hasMatch = resultSet.next();
            if (hasMatch) {
                statement.close();
                connection.close();
                return USERNAMEALREADYUSED;
            }

            //create
            String students = "";
            String posts = "";
            ////register a group:id(auto-generated)|name|creator|students|posts
            sql = "insert into STUDYGROUP (name,creator,students,posts) VALUE (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, groupName);
            preparedStatement.setInt(2, teacherID);
            preparedStatement.setString(3, students);
            preparedStatement.setString(4, posts);
            preparedStatement.executeUpdate();
            //return SUCCESS;
            //get group id
            sql = "select * from " + GROUPTABLENAME + " where name='" + groupName + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            int groupID = resultSet.getInt(1);
            //add group to teacher
            sql = "select * from " + TEACHERTABLENAME + " where id='" + teacherID + "'";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            String allGroups = resultSet.getString(6);
            if (allGroups.length() == 0) {
                allGroups = groupID + "";
            } else {
                allGroups = allGroups + "," + groupID;
            }
            sql = "update TEACHER set groupID = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, allGroups);
            preparedStatement.setInt(2, teacherID);
            preparedStatement.executeUpdate();

            statement.close();
            connection.close();
            return groupID;

        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//int teacherID, int groupID -> SUCCESS/FAILED
class deleteGroupCommand extends Command {
    int teacherID;
    int groupID;

    public deleteGroupCommand(int teacherID, int groupID) {
        this.teacherID = teacherID;
        this.groupID = groupID;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            Statement statement = connection.createStatement();
            //remove group from teacher
            int result = removeGroupFromUser(teacherID, groupID, TEACHER, connection, statement);
            if (result != SUCCESS) {
                return FAILED;
            }
            //remove group from students
            String sql = "select * from " + GROUPTABLENAME + " where id='" + groupID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allStudents = resultSet.getString(STUDENTIDCOLINGROUP);//in the form of "21,20,23,4"

            String[] students = allStudents.split(",");
            for (String student : students) {
                removeGroupFromUser(Integer.parseInt(student), groupID, STUDENT, connection, statement);
            }
            //delete the group from the group table
            sql = "delete from STUDYGROUP where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupID);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }

    }
}

//String name -> SUCCESS/FAILED/NOTREGISTERED
class forgetPassCommand extends Command {
    String name;

    public forgetPassCommand(String name) {
        this.name = name;
    }

    @Override
    public int execute() {
        try {
            int type;
            String email = null;
            getConnection();
            //check if the email is registered as student
            Statement statement = connection.createStatement();
            String sql = "select * from " + STUDENTTABLENAME + " where name='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                email = resultSet.getString(EMAILINSTUDENT);
                type = STUDENT;
                //set password to a random int
            } else {
                //check if the user is a teacher
                statement = connection.createStatement();
                sql = "select * from " + TEACHERTABLENAME + " where name='" + name + "'";
                resultSet = statement.executeQuery(sql);
                ////check if there is a match
                returnValue = resultSet.next();
                if (returnValue) {
                    email = resultSet.getString(EMAILINTEACHER);
                    type = TEACHER;
                } else {
                    statement.close();
                    connection.close();
                    return NOTREGISTERED;
                }
            }
            //if not both, return Not registered
            if (email == null) {
                statement.close();
                connection.close();
                return NOTREGISTERED;
            } else {
                Random rand = new Random();
                int upperbound = 20020313;
                int int_random = rand.nextInt(upperbound);
                int result = setRandomPass(type, name, int_random, statement, connection);
                statement.close();
                connection.close();
                if (result != SUCCESS) {
                    return FAILED;
                } else {
                    sendMail(email, int_random + "");
                    return SUCCESS;
                }
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
            return FAILED;
        }

    }
}

class createTestCommand extends Command {
    private String name;
    private int author;
    private int price;

    public createTestCommand(String name, int author, int price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            //check if test name already exists
            Statement statement = connection.createStatement();
            String sql = "select * from TEST where name='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            //////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return TESTNAMEALREADYUSED;
            }
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());//get date
            String questions = "";// set questionsID to 0 refers no test
            sql = "insert into TEST (name,author,date,price,questions) VALUE (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, author);
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setInt(4, price);
            preparedStatement.setString(5, questions);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;


        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

class addQuestationCommand extends Command {
    private String name;
    private String question;
    private String answer;

    public addQuestationCommand(String name, String question, String answer) {
        this.name = name;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            //check if test name already exists
            Statement statement = connection.createStatement();
            String sql = "select * from QUESTION where name='" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            //////check if there is a match
            boolean returnValue = resultSet.next();
            if (returnValue) {
                statement.close();
                connection.close();
                return QUESTIONNAMEALREADYUSED;
            }
            sql = "insert into QUESTION (name,question,answer) VALUE (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, question);
            preparedStatement.setString(3, answer);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;


        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}

//class releaseMarkCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class inviteCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
class deleteMemberCommand extends Command {
    int teacherID;
    int studentID;
    int groupID;

    public deleteMemberCommand(int teacherID, int studentID, int groupID) {
        this.studentID = studentID;
        this.groupID = groupID;
        this.teacherID = teacherID;
    }

    @Override
    public int execute() {
        try {
            getConnection();
            Statement statement = connection.createStatement();
            //remove group from students
            String sql = "select * from " + GROUPTABLENAME + " where id='" + groupID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            removeGroupFromUser(studentID, groupID, STUDENT, connection, statement);
            getConnection();
            Statement statement1 = connection.createStatement();

            removeUserfromGroup(studentID, groupID, connection, statement1);

            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }

    }

}
class createAnnouncementCommand extends Command {
    private String announcement;

    public createAnnouncementCommand(String announcement) {
        this.announcement = announcement;
    }
    @Override
    public int execute() {
       try{
           getConnection();
           String query = "update STUDYGROUP set posts = ?";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1, announcement);
           preparedStatement.executeUpdate();
           connection.close();
           return SUCCESS;}
        catch (SQLException e) {
            e.printStackTrace();
            return FAILED;}
    }
}


//class submitCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}

