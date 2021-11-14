package Write.Group;

import Write.Writer;


public abstract class GroupWriter extends Writer {
    public final String TABLE = "STUDYGROUP";
    public final String ID = "id";
    public final String NAME = "name";
    public final String CREATOR = "creator";
    public final String STUDENTS = "students";
    public final String POST = "posts";
    public final String TESTS = "testID";

    public final int IDCol = 1;
    public final int NAMECol = 2;
    public final int CREATORCol = 3;
    public final int STUDENTSCol = 4;
    public final int POSTCol = 5;
    public final int TESTSCol = 6;




    public Object setGroupInfo(String colName, int groupID, String info) {
        return updateInfo(groupID, info, TABLE, colName);
    }

    public Object setGroupInfo(String colName, int groupID, int info) {
        return updateInfo(groupID, info, TABLE, colName);
    }


}


