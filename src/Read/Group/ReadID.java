package Read.Group;

public class ReadID extends GroupReader {
    private String groupName;

    public ReadID(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public Object read() {
        return readInfo(groupName, TABLE, 1, INT);
    }
}
