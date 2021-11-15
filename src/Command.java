import Read.Group.GroupReader;
import Read.Group.ReadStudents;
import Read.Student.StudentReader;
import Read.Teacher.TeacherReader;
import Read.Test.ReadQuestions;
import Read.Test.TestReader;
import Write.Answer.AnswerWriter;
import Write.Answer.AutoGrade;
import Write.Answer.WriteMark;
import Write.Answer.WriteNewAnswer;
import Write.Group.*;
import Write.Question.QuestionWriter;
import Write.Question.WriteNewQuestion;
import Write.Student.StudentWriter;
import Write.Student.WriteGroups;
import Write.Student.WriteNewStudent;
import Write.Teacher.TeacherWriter;
import Write.Teacher.WriteNewTeacher;
import Write.Test.TestWriter;
import Write.Test.WriteNewTest;
import Write.Test.WriteQuestions;


import java.sql.*;


public abstract class Command {
    public final int SUCCESS = 0;
    public final int FAILED = -1;
    public final int USERNAMEALREADYUSED = -2;
    public final int GROUPALREADYJOINED = -3;
    public final int TEACHER = 11;
    public final int STUDENT = 12;

    public final String STUDENTTABLENAME = "STUDENT";


    public Connection connection = null;

    public String driver = "com.mysql.cj.jdbc.Driver";//驱动程序名
    public String url = "jdbc:MySQL://sql5.freemysqlhosting.net:3306/sql5449780";//url指向要访问的数据库study
    public String user = "sql5449780";//MySQL配置时的用户名
    public String password = "HNzHR6WEhn";//MySQL配置时的密码

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

    public int removeGroupFromUser(int userID, int groupID, int userType) {

        if (userType == STUDENT) {
            //get all group ids
            StudentReader reader = new Read.Student.ReadGroups(userID);
            String allGroups = (String) reader.read();
            if (allGroups.equals(FAILED + "")) {
                return FAILED;
            }
            //check if group id is in the string
            if (!isInString(allGroups, groupID, ",")) {
                return FAILED;
            }
            //remove id from string
            String newGroups = removeIDFromString(allGroups, groupID, ",");
            //reset the new string
            StudentWriter writer = new Write.Student.WriteGroups(userID, newGroups);
            return (int) writer.set();

        } else {
            TeacherReader reader = new Read.Teacher.ReadGroups(userID);
            String allGroups = (String) reader.read();
            if (allGroups.equals(FAILED + "")) {
                return FAILED;
            }
            if (!isInString(allGroups, groupID, ",")) {
                return FAILED;
            }
            String newGroups = removeIDFromString(allGroups, groupID, ",");
            TeacherWriter writer = new Write.Teacher.WriteGroups(userID, newGroups);
            return (int) writer.set();
        }
    }

    public int removeStudentFromGroup(int userID, int groupID) {

        //get all students
        GroupReader groupReader = new ReadStudents(groupID);
        String allStudents = (String) groupReader.read();
        if (allStudents.equals(FAILED + "")) {
            return FAILED;
        }
        //check if student is in the group
        if (!isInString(allStudents, groupID, ",")) {
            return FAILED;
        }
        //remove student from the group
        String newStudents = removeIDFromString(allStudents, userID, ",");
        //reset the new string
        GroupWriter writer = new Write.Group.WriteStudents(newStudents, groupID);
        return (int) writer.set();
    }

    public boolean isInString(String string, int id, String split) {
        String[] array = string.split(split);
        for (String s : array) {
            if (s.trim().equals(id + "")) {
                return true;
            }
        }
        return false;
    }

    public String removeIDFromString(String string, int id, String split) {
        if (string.trim().length() == (id + "").length()) {
            return "";
        }
        String[] array = string.split(split);
        String result = "";
        for (String s : array) {
            if (!s.equals(id + "")) {
                result = s + ",";
            }
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public abstract Object execute();
}

//String name,String pass -> an id/FAILED
class loginCommand extends Command {
    private final String name;
    private final String pass;

    public loginCommand(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    @Override
    public Object execute() {

        StudentReader studentReader = new Read.Student.ReadNameAndPass(name, pass);
        int id = (int) studentReader.read();
        if (id != FAILED) {
            return id;
        }

        TeacherReader teacherReader = new Read.Teacher.ReadNameAndPass(name, pass);
        id = (int) teacherReader.read();
        return id;

    }
}

//int id -> STUDENT/TEACHER/FAILED
class checkIdentity extends Command {
    int id;

    public checkIdentity(int id) {
        this.id = id;
    }

    @Override
    public Object execute() {

        TeacherReader teacherReader = new Read.Teacher.ReadName(id);
        String name = (String) teacherReader.read();
        if (!name.equals(FAILED + "")) {
            return TEACHER;
        }
        StudentReader studentReader = new Read.Student.ReadName(id);
        name = (String) studentReader.read();
        if (!name.equals(FAILED + "")) {
            return STUDENT;
        }

        return FAILED;

    }
}

//String name, String pass, String email, int type(teacher11, student12)-> USERNAMEALREADYUSED/SUCCESS/FAILED
class registerCommand extends Command {
    private final String name;
    private final String pass;
    private final String email;
    private final int type;

    public registerCommand(String name, String pass, String email, int type) {
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.type = type;
    }

    @Override
    public Object execute() {
        //register as a student
        if (type == STUDENT) {
            StudentWriter studentWriter = new WriteNewStudent(name, pass, email, type);
            return studentWriter.set();
        }
        //register as a teacher
        else {
            TeacherWriter teacherWriter = new WriteNewTeacher(name, pass, email, type);
            return teacherWriter.set();
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
    public Object execute() {

        //remove group from student
        int result = removeGroupFromUser(studentID, groupID, STUDENT);
        if (result != SUCCESS) {
            return FAILED;
        }
        //remove student from group
        result = removeStudentFromGroup(studentID, groupID);
        if (result != SUCCESS) {
            return FAILED;
        }
        return SUCCESS;
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
    public Object execute() {

        //add group to student
        ////get all the groups joined by the student
        StudentReader studentReader = new Read.Student.ReadGroups(studentID);
        String allGroups = (String) studentReader.read();
        ////add groupID to the string
        if (isInString(allGroups, groupID, ",")) {
            return GROUPALREADYJOINED;
        }
        if (allGroups.length() == 0) {
            allGroups = groupID + "";
        } else {
            allGroups = allGroups + "," + groupID;
        }
        ////rewrite the new string to the database
        StudentWriter studentWriter = new WriteGroups(studentID, allGroups);
        int result = (int) studentWriter.set();
        if (result == FAILED) {
            return FAILED;
        }


        //add student to group
        ////get all the students form the group
        GroupReader groupReader = new ReadStudents(groupID);
        String allStudents = (String) groupReader.read();

        ////add studentID to the group
        if (allStudents.length() == 0) {
            allStudents = studentID + "";
        } else {
            allStudents = studentID + "," + allStudents;
        }
        ////rewrite the new string to the database
        GroupWriter groupWriter = new WriteStudents(allStudents, groupID);
        return groupWriter.set();

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
    public Object execute() {
        GroupWriter groupWriter = new WriteNewGroup(teacherID, groupName);
        return groupWriter.set();
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
    public Object execute() {

        //remove group from teacher
        int result = removeGroupFromUser(teacherID, groupID, TEACHER);
        if (result != SUCCESS) {
            return FAILED;
        }
        //remove group from students
        GroupReader groupReader = new ReadStudents(groupID);
        String allStudents = (String) groupReader.read();
        if (allStudents.trim().length() != 0) {
            String[] students = allStudents.trim().split(",");
            for (String student : students) {
                removeGroupFromUser(Integer.parseInt(student), groupID, STUDENT);
            }
        }

        //delete the group from the group table
        GroupWriter groupWriter = new DeleteGroup(groupID);
        return groupWriter.set();
    }

}

//String test name, String author, int price -> SUCCESS/FAILED
class createTestCommand extends Command {
    private final String name;
    private final int author;
    private final int price = 90;
    private final java.util.Date date;

    public createTestCommand(String name, int author, java.util.Date date) {
        this.name = name;
        this.author = author;
        this.date = date;
    }

    @Override
    public Object execute() {
        TestWriter testWriter = new WriteNewTest(name, author, price, date);
        return testWriter.set();
    }
}

//string question name, string question, string answer -> SUCCESS/FAILED
class createQuestationCommand extends Command {
    private final String name;
    private final String question;
    private final String answer;
    private final int mark;

    public createQuestationCommand(String name, String question, String answer, int mark) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.mark = mark;
    }

    @Override
    public Object execute() {
        QuestionWriter questionWriter = new WriteNewQuestion(name, question, answer, mark);
        return questionWriter.set();
    }
}

//int studentID, int groupID ->Success/failed
class deleteMemberCommand extends Command {
    int studentID;
    int groupID;

    public deleteMemberCommand(int studentID, int groupID) {
        this.studentID = studentID;
        this.groupID = groupID;
    }

    @Override
    public Object execute() {

        int result1 = removeGroupFromUser(studentID, groupID, STUDENT);
        int result3 = removeStudentFromGroup(studentID, groupID);
        if (result1 == SUCCESS && result3 == SUCCESS) {
            return SUCCESS;
        }
        return FAILED;
    }

}

//String content, int group id -> SUCCESS/FAILED
class createAnnouncementCommand extends Command {
    private final int groupID;
    private final String announcement;

    public createAnnouncementCommand(String announcement, int groupID) {
        this.announcement = announcement;
        this.groupID = groupID;
    }

    @Override
    public Object execute() {
        GroupWriter groupWriter = new WriteAnnouncement(groupID, announcement);
        return groupWriter.set();
    }
}

//int question id, int test id -> FAILED/SUCCESS
class addQuestionToTestCommand extends Command {
    private final int questionID;
    private final int testID;

    public addQuestionToTestCommand(int questionID, int testID) {
        this.questionID = questionID;
        this.testID = testID;
    }

    @Override
    public Object execute() {

        TestReader testReader = new ReadQuestions(testID);
        String allQuestions = (String) testReader.read();
        if (allQuestions.length() == 0) {
            allQuestions = "" + questionID;
        } else {
            allQuestions = allQuestions + "," + questionID;
        }

        TestWriter testWriter = new WriteQuestions(allQuestions, testID);
        return testWriter.set();


    }
}

//int student id, string[] answer, int test id -> SUCCESS/FAILED
class submitAnswerCommand extends Command {
    private final String[] answer;
    private final int testID;
    private final int studentID;

    public submitAnswerCommand(int studentID, String[] answer, int testID) {
        this.answer = answer;
        this.testID = testID;
        this.studentID = studentID;
    }

    @Override
    public Object execute() {
        TestReader testReader = new ReadQuestions(testID);
        String question = (String) testReader.read();
        if (question.equals(FAILED + "")) {
            return FAILED;
        }
        try {
            String[] questions = question.trim().split(",");
            for (int i = 0; i < answer.length; i++) {
                AnswerWriter answerWriter = new WriteNewAnswer(studentID, answer[i], Integer.parseInt(questions[i]));
                int result = (int) answerWriter.set();
                if (result == FAILED) {
                    return FAILED;
                }
                Command c = new autoGrade();
                if ((int) c.execute() == FAILED) {
                    return FAILED;
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILED;
        }


    }
}

//void --> mark/FAILED
class autoGrade extends Command {

    public autoGrade() {
    }

    @Override
    public Object execute() {
        AnswerWriter answerWriter = new AutoGrade();
        return answerWriter.set();

    }
}

//int answer id, int mark --> SUCCESS/FAILED
class gradeQuestion extends Command {
    private final int answerID;
    private final int mark;

    public gradeQuestion(int answerID, int mark) {
        this.answerID = answerID;
        this.mark = mark;
    }

    @Override
    public Object execute() {
        AnswerWriter answerWriter = new WriteMark(answerID, mark);
        return answerWriter.set();
    }
}
//
////int studentID, int testID --> int mark/FAILED
//class gradeTest extends Command {
//    private final int studentID;
//    private final int testID;
//
//    public gradeTest(int studentID, int testID) {
//        this.studentID = studentID;
//        this.testID = testID;
//    }
//
//    @Override
//    public Object execute() {
//        try {
//            getConnection();
//            Statement statement = connection.createStatement();
//
//            //get student answer
//            String sql = "select * from " + "TEST" + " where id='" + testID + "'";
//            ResultSet resultSet = statement.executeQuery(sql);
//            boolean hasMatch = resultSet.next();
//            if (!hasMatch) {
//                statement.close();
//                connection.close();
//                return FAILED;
//            }
//            String questions = resultSet.getString(6);
//            String[] question = questions.trim().split(",");
//            int sum = 0;
//            for (String q : question) {
//                sql = "select * from " + "QUESTIONANSWER" + " where questionID='" + q + "' and studentID='" + studentID + "'";
//                resultSet = statement.executeQuery(sql);
//                hasMatch = resultSet.next();
//                if (!hasMatch) {
//                    statement.close();
//                    connection.close();
//                    return FAILED;
//                }
//                int marks = resultSet.getInt(4);
//                sum += marks;
//            }
//            sql = "insert into TESTANSWER (testID,studentID,mark) VALUE (?,?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, testID);
//            preparedStatement.setInt(2, studentID);
//            preparedStatement.setInt(3, sum);
//            preparedStatement.executeUpdate();
//            sql = "select * from " + STUDENTTABLENAME + " where id='" + studentID + "'";
//            resultSet = statement.executeQuery(sql);
//            resultSet.next();
//            String alltests = resultSet.getString(8);//in the form of "1,2,3,4"
//            ////add groupID to the string
//            if (isInString(alltests, testID, ",")) {
//                statement.close();
//                connection.close();
//                return GROUPALREADYJOINED;
//            }
//            if (alltests.length() == 0) {
//                alltests = testID + " ";
//            } else {
//                alltests = alltests + "," + testID;
//            }
//            ////rewrite the new string to the database
//            sql = "update STUDENT set testID = ? where id = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, alltests);
//            preparedStatement.setInt(2, studentID);
//            preparedStatement.executeUpdate();
//
//            statement.close();
//            connection.close();
//            Command c = new autoGrade();
//            c.execute();
//            return SUCCESS;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return FAILED;
//        }
//    }
//}
//
////int studentID, int groupID --> int student average
//class getStudentAve extends Command {
//    private final int studentID;
//
//    public getStudentAve(int studentID) {
//        this.studentID = studentID;
//    }
//
//    @Override
//    public Object execute() {
//        try {
//            getConnection();
//            Statement statement = connection.createStatement();
//
//            //get student answer
//            String sql = "select * from " + "TESTANSWER" + " where studentID='" + studentID + "'";
//            ResultSet resultSet = statement.executeQuery(sql);
//            boolean hasMatch = resultSet.next();
//            if (!hasMatch) {
//                statement.close();
//                connection.close();
//                return 0;
//            }
//            int time = 1;
//            int total = resultSet.getInt(4);
//            while (resultSet.next()) {
//                total += resultSet.getInt(4);
//                time += 1;
//            }
//            return total * 1.0 / time;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return FAILED;
//        }
//    }
//}