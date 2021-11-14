package Read.Answer;

public class ReadMark extends AnswerReader {
    private final int ID;

    public ReadMark(int ID) {
        this.ID = ID;
    }

    @Override
    public Object read() {
        String sql = "select * from " + TABLE + " where id='" + ID + "'";
        return readInfo(sql, 4, INT);
    }
}