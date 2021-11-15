package Read.Question;

public class ReadID extends QuestionReader {
    private final String name;
    private final String question;
    private final String answer;

    public ReadID(String name, String question, String answer) {
        this.name = name;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public Object read() {
        String sql = "select * from " + TABLE + " where name='" + name + "' and question='" + question + "' and answer='" + answer + "'";
        return readInfo(sql, IDCol, INT);
    }
}
