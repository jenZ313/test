//These are gateways

import java.sql.*;

public class Command {
    public final int SUCCESS = 0;
    public final int FAILED = 1;
    public final int USERNAMEALREADYUSED = 2;
    public final int GROUPALREADYJOINED = 3;
    public final int TEACHER = 11;
    public final int STUDENT = 12;

    public final String STUDENTTABLENAME = "STUDENT";
    public final String TEACHERTABLENAME = "TEACHER";
    public final String GROUPTABLENAME = "STUDYGROUP";

    public final int STUDENTIDCOLINGROUP = 4;
    public final int IDCOLINGROUP = 4;
    public final int GROUPIDCOLINSTUDENT = 7;

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
            ////register as a student:id(auto-generated)|name|pass|date|email|words|groupID|level
            if (type == STUDENT) {
                String words = "";//set words to []
                int level = 1;//set level to 1
                sql = "insert into STUDENT (name,pass,date,email,words,groupID,level) VALUE (?,?,?,?,?,?,?)";
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
                sql = "insert into TEACHER (name,pass,date,email,groupID) VALUE (?,?,?,?,?)";
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
            //remove group from student
            ////get all the groups joined by the student
            Statement statement = connection.createStatement();
            String sql = "select * from " + STUDENTTABLENAME + " where id='" + studentID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            boolean hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allGroups = resultSet.getString(GROUPIDCOLINSTUDENT);//in the form of "21,20,23,4"
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
            sql = "update STUDENT set groupID = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newGroups);
            preparedStatement.setInt(2, studentID);
            preparedStatement.executeUpdate();

            //remove student from group
            ////get all the students for the group
            sql = "select * from " + GROUPTABLENAME + " where id='" + groupID + "'";
            resultSet = statement.executeQuery(sql);
            ////get all ids of groups joined by the student
            hasMatch = resultSet.next();
            if (!hasMatch) {
                statement.close();
                connection.close();
                return FAILED;
            }
            String allStudents = resultSet.getString(STUDENTIDCOLINGROUP);//in the form of "21,20,23,4"
            ////remove studentID from the string
            index = allStudents.indexOf(studentID + "");
            //////if student not found, return failed
            if (index == -1) {
                statement.close();
                connection.close();
                return FAILED;
            }
            ////remove studentID from the group
            IDLength = (studentID + "").length();
            String newStudents;
            if (allStudents.length() == IDLength) {
                newStudents = "";
            } else {
                newStudents = allStudents.substring(0, index) + allStudents.substring(index + IDLength + 1);
            }
            ////rewrite the new string to the database
            sql = "update STUDYGROUP set students = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
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

//int teacherID, String groupName -> SUCCESS/FAILED
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

//class deleteGroupCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class changeNameCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class createTestCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class classaddQuestationCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
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
//class deleteMemberCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class createAnnouncementCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}
//
//class submitCommand extends Command {
//
//    @Override
//    public int execute() {
//
//    }
//}

