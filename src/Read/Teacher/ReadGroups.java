package Read.Teacher;


public class ReadGroups extends TeacherReader {

    private final int teacherID;

    public ReadGroups(int teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public Object read() {
        String sql = "select * from " + TABLE + " where id='" + teacherID + "'";
        return readInfo(sql, GROUPSCol, STRING);
    }
}
