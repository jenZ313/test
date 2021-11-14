package Write.Answer;

import Write.Question.QuestionWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class WriteNewAnswer extends AnswerWriter {
    private final String answer;
    private final int questionID;
    private final int studentID;

    public WriteNewAnswer(int studentID, String answer, int questionID) {
        this.answer = answer;
        this.questionID = questionID;
        this.studentID = studentID;
    }

    @Override
    public Object set() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "insert into " + TABLE + " (questionID,answer,mark,studentID) VALUE (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, questionID);
            preparedStatement.setString(2, answer);
            preparedStatement.setInt(3, FAILED);
            preparedStatement.setInt(4, studentID);
            preparedStatement.executeUpdate();
            statement.close();
            connection.close();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}
