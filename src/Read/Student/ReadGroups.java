package Read.Student;

public class ReadGroups extends StudentReader {
    private final int studentID;

    public ReadGroups(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public Object read() {
        String sql = "select * from " + TABLE + " where id='" + studentID + "'";
        return readInfo(sql, GROUPSCol, STRING);
    }
}
