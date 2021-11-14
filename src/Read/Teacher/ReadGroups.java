package Read.Teacher;


public class ReadGroups extends TeacherReader {

    private int teacherID;

    public ReadGroups(int teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public Object read() {
        return readInfo(teacherID, TABLE, GROUPSCol, STRING);
    }
}
