package Write.Group;

public class WriteStudents extends GroupWriter {
    private int groupID;
    private String students;

    public WriteStudents(String students, int groupID) {
        this.groupID = groupID;
        this.students = students;
    }

    @Override
    public Object set() {
        return setGroupInfo(STUDENTS, groupID, students);
    }
}
