package Write.Student;

public class WriteGroups extends StudentWriter {
    private int studentID;
    private String groups;

    public WriteGroups(int studentID, String groups) {
        this.groups = groups;
        this.studentID = studentID;
    }


    @Override
    public Object set() {
        return setStudentInfo(GROUPS, studentID, groups);
    }
}
