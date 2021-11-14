package Write.Group;

public class WriteGroupPost extends GroupWriter {
    private int groupID;
    private String post;

    public WriteGroupPost(int groupID, String post) {
        this.groupID = groupID;
        this.post = post;
    }

    @Override
    public Object set() {
        return setGroupInfo(POST, groupID, post);
    }
}
