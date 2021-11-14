package Write.Student;

import Write.Writer;

public abstract class StudentWriter extends Writer {

    public final String TABLE = "STUDENT";
    public final String ID = "id";
    public final String NAME = "name";
    public final String PASS = "pass";
    public final String DATE = "date";
    public final String EMAIL = "email";
    public final String GROUPS = "groupID";
    public final String TESTS = "testID";
    public final String ANSWERS = "answerID";


    public final int IDCol = 1;
    public final int NAMECol = 2;
    public final int PASSCol = 3;
    public final int DATECol = 4;
    public final int EMAILCol = 5;
    public final int GROUPSCol = 7;
    public final int TESTSCol = 8;
    public final int ANSWERSCol = 9;




    public Object setStudentInfo(String colName, int teacherID, String info) {
        return updateInfo(teacherID, info, TABLE, colName);
    }

    public Object setStudentInfo(String colName, int teacherID, int info) {
        return updateInfo(teacherID, info, TABLE, colName);
    }
}

