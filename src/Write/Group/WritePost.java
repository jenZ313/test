package Write.Group;

public class WritePost extends GroupWriter {
    private int groupID;
    private String post;

    public WritePost(int groupID, String post) {
        this.groupID = groupID;
        this.post = post;
    }

    @Override
    public Object set() {
        return setGroupInfo(POST, groupID, post);
    }
}
