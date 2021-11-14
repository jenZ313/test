
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GroupWriter extends Writer {
    public final String TABLE = "STUDYGROUP";
    public final int SUCCESS = 0;
    public final int FAILED = -1;

    @Override
    public abstract Object set();

    public Object setGroupInfo(String colName, int groupID, String info) {
        try {
            Connection connection = getConnection();
            String query = "update " + TABLE + " set " + colName + " = ? where id = " + groupID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, info);
            preparedStatement.executeUpdate();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }

    public Object setGroupInfo(String colName, int groupID, int info) {
        try {
            Connection connection = getConnection();
            String query = "update " + TABLE + " set " + colName + " = ? where id = " + groupID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, info);
            preparedStatement.executeUpdate();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }


}

class WriteNewGroup extends GroupWriter {


    @Override
    public Object set() {
        return null;
    }
}


class WriteGroupName extends GroupWriter {
    private int groupID;
    private String name;

    public WriteGroupName(int groupID, String name) {
        this.groupID = groupID;
        this.name = name;
    }

    @Override
    public Object set() {
        String colName = "name";
        return setGroupInfo(colName, groupID, name);
    }
}

