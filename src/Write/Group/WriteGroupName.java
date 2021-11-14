package Write.Group;

public class WriteGroupName extends GroupWriter {
    private int groupID;
    private String name;

    public WriteGroupName(int groupID, String name) {
        this.groupID = groupID;
        this.name = name;
    }

    @Override
    public Object set() {
        return setGroupInfo(NAME, groupID, name);
    }

}
