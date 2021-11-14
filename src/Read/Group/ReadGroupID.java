package Read.Group;

public class ReadGroupID extends GroupReader {
    private String groupName;

    public ReadGroupID(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public Object read() {
        return readInfo(groupName, TABLE, 1, INT);
    }
}
