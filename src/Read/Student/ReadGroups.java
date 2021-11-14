package Read.Student;

public class ReadGroups extends StudentReader {
    private int studentID;

    public ReadGroups(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public Object read() {
        return readInfo(studentID, TABLE, GROUPSCol, STRING);
    }
}
