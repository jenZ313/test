import Read.Answer.AnswerReader;
import Read.Answer.ReadAnswer;
import Read.Answer.ReadID;
import Read.Answer.ReadQuestionID;
import Read.Question.QuestionReader;
import Read.Question.ReadMark;

import java.sql.*;

public class Ini {
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
        return iniGroupTable() && iniTestTable() && iniWordTable() && iniQuestionTable() && iniStudentTable() &&
                iniTeacherTable() && iniQAnswerTable() && iniTAnswerTable();

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
                    " testID VARCHAR(1000), " +
                    " answerID VARCHAR(1000), " +
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
                    " testID VARCHAR(1000), " +
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
                    " testID VARCHAR(1000) , " +
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
                    " author INT, " +
                    " date DATE, " +
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
                    " name VARCHAR(16), " +
                    " question VARCHAR(1000), " +
                    " answer VARCHAR(1000), " +
                    " mark INT , " +
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

    public static boolean iniQAnswerTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS QUESTIONANSWER " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " questionID INT, " +
                    " answer VARCHAR(1000), " +
                    " mark INT , " +
                    " studentID INT, " +
                    " groupID INT, " +
                    " PRIMARY KEY ( id )) CHARACTER SET = utf8";
            statement.executeUpdate(sql);
            System.out.println("Created answer table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean iniTAnswerTable() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TESTANSWER " +
                    "(id INT UNSIGNED AUTO_INCREMENT, " +
                    " testID INT, " +
                    " studentID INT, " +
                    " mark INT, " +
                    " PRIMARY KEY ( id )) CHARACTER SET = utf8";
            statement.executeUpdate(sql);
            System.out.println("Created answer table in given database...");
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void main(String[] args) {

//        Command a = new Command();
//        a.removeGroupFromUser(1,1,12);
//
//        Command c = new registerCommand("23434","234","qweqtrqret",12);
//        c.execute();
//        Command c1 = new forgetPassCommand("j1");
//        c1.execute();
//        Command d = new registerCommand("234","123","123",11);
//        d.execute();
//        Command d1 = new registerCommand("234","123","123",12);
//        d1.execute();
//        Command d3 = new registerCommand("234","123","123",11);
//        d3.execute();
//        System.out.println(ID);
//
//        Command a = new joinGroupCommand(6, 7);
//        a.execute();
//        Command c = new deleteGroupCommand(2, 3);
//        System.out.println(c.execute());
//        iniStudentTable();
//        iniTeacherTable();
//        Command c = new quitGroupCommand(3, 4);
//        System.out.println(c.execute());
//        iniTestTable();
//        Command c = new createTestCommand("testexample", 1, 10);
//        c.execute();
//        Command c = new createAnnouncementCommand("Hi, guys again");
//        System.out.println(c.execute());
//        Command c = new deleteMemberCommand(2,1,3);
//        c.execute();
//        Command a = new registerCommand("a", "1232456", "aaaaaaa", 12);
//        a.execute();
//        Command b = new registerCommand("b", "123456", "aaaa", 12);
//        b.execute();
//        Command d = new joinGroupCommand(2, 11);
//        d.execute();
//        Command e = new joinGroupCommand(1, 11);
//        e.execute();
//        Command f = new joinGroupCommand(3, 11);
//        f.execute();
//        Command c = new addQuestationCommand("question1", "banana", "香蕉");
//        c.execute();
//        Command c = new createGroupCommand(1, "group12");
//        c.execute();
//        iniQAnswerTable();
//
//         iniQAnswerTable();
//        Command c = new gradeQuestion();
//        System.out.println(c.execute());
//        Command c= new submitCommand(2,"香蕉",1);
//        c.execute();
//          Command c = new createGroupCommand(1, "test");
//          c.execute();
//        Command a1 = new registerCommand("jen12", "passads", "qdqq", 12);
//        a1.execute();
        Command a2 = new joinGroupCommand(7, 6);
        System.out.println(a2.execute());
//        Command a3 = new quitGroupCommand(id,1);
//        System.out.println(a3.execute());
//        Command c = new submitCommand(1,"a", 1);
//        c.execute();
//        Command d = new submitCommand(1,"a", 2);
//        d.execute();
//        Command c  = new gradeTest(1,1);
//        c.execute();
//        Command c = new addQuestionToTestCommand(1, 2);
//        c.execute();
//        Command d = new addQuestionToTestCommand(2, 2);
//        d.execute();
//        Command c = new gradeTest(1, 2);
//        c.execute();
//        Command c = new getStudentAve(1);
//        System.out.println(c.execute());;
//        Command c = new createQuestationCommand("q1", "apple", "苹果", 5);
//        c.execute();
//        Command c = new submitAnswerCommand(1, "苹果", 1);
//        c.execute();
//        Command d = new submitAnswerCommand(1, "pingguo", 1);
//        d.execute();
//        String studentAnswer = (String) new ReadAnswer(15).read();
//        System.out.println(studentAnswer);
//        int questionID = (int) new ReadQuestionID(15).read();
//        System.out.println(questionID);
//        QuestionReader questionReader = new Read.Question.ReadAnswer(1);
//        String correctAnswer = (String) questionReader.read();
//        System.out.println(correctAnswer);
//        Command c = new autoGrade();
//        c.execute();
//        AnswerReader answerReader = new ReadID(-1);
//        int answerID = (int) answerReader.read();
//        int questionID = (int) new ReadQuestionID(answerID).read();
//        QuestionReader q = new Read.Question.ReadMark(1);
//        int mark = (int) q.read();
//        System.out.println(mark);
//        Command c = new getStudentAve(1);
//        System.out.println(c.execute());
//        iniQAnswerTable();
//        String[] a = {"苹果", "diyi", "吃饭"};
//        Command c = new submitAnswerCommand(1, a, 1, 1);
//        c.execute();
//        iniTAnswerTable();
        Command c = new checkIdentity(10);
        System.out.println(c.execute());
    }

}