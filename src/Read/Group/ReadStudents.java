package Read.Group;

public class ReadStudents extends GroupReader {
    private int groupID;

    public ReadStudents(int groupID) {
        this.groupID = groupID;
    }

    @Override
    public Object read() {
        return readInfo(groupID, TABLE, STUDENTSCol, STRING);
    }
}
