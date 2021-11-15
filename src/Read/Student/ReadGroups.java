package Read.Student;

import Read.Group.ReadName;

import java.util.HashMap;

public class ReadGroups extends StudentReader {
    private final int studentID;

    public ReadGroups(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public Object read() {
        HashMap<Integer, String> result = new HashMap<>();
        String sql = "select * from " + TABLE + " where id='" + studentID + "'";
        String groupsID = (String) readInfo(sql, GROUPSCol, STRING);
        if ((groupsID.equals(FAILED + ""))) {
            return FAILED;
        }

        try {
            if (groupsID.equals("")) {
                result = new HashMap<>();
            }else{
                String[] IDList = groupsID.trim().split(",");
                for (String s : IDList) {
                    int id = Integer.parseInt(s.trim());
                    String name = (String) new ReadName(id).read().toString();

                    result.put(id, name);}
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return FAILED;
        }

    }
}
