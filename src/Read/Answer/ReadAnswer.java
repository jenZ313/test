package Read.Answer;

public class ReadAnswer extends AnswerReader {
    private final int ID;

    public ReadAnswer(int ID) {
        this.ID = ID;
    }

    @Override
    public Object read() {
        String sql = "select * from " + TABLE + " where id='" + ID + "'";
        return readInfo(sql, ANSWERCol, STRING);
    }
}
