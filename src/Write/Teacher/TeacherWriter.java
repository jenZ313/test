package Write.Teacher;

import Write.Writer;

public abstract class TeacherWriter extends Writer {
    public final String TABLE = "TEACHER";
    public final String ID = "id";
    public final String NAME = "name";
    public final String PASS = "pass";
    public final String EMAIL = "email";
    public final String GROUPS = "groupID";
    public final String TESTS = "testID";


    public final int IDCol = 1;
    public final int NAMECol = 2;
    public final int PASSCol = 3;
    public final int EMAILCol = 5;
    public final int GROUPSCol = 6;
    public final int TESTSCol = 7;


    public Object setTeacherInfo(String colName, int teacherID, String info) {
        return updateInfo(teacherID, info, TABLE, colName);
    }

    public Object setTeacherInfo(String colName, int teacherID, int info) {
        return updateInfo(teacherID, info, TABLE, colName);
    }
}

