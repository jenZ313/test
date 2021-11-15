package Write.Answer;

import Read.Answer.AnswerReader;
import Read.Answer.ReadAnswer;
import Read.Answer.ReadID;
import Read.Answer.ReadQuestionID;
import Read.Question.QuestionReader;
import Read.Question.ReadMark;

import java.sql.*;

public class AutoGrade extends AnswerWriter {
    public AutoGrade(){}
    @Override

    public Object set() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            AnswerReader answerReader = new ReadID(FAILED);
            int answerID = (int) answerReader.read();
            if (answerID == FAILED) {
                return FAILED;
            }
            AnswerReader answerReader1 = new ReadAnswer(answerID);
            String studentAnswer = (String) answerReader1.read();
            AnswerReader answerReader2 = new ReadQuestionID(answerID);
            int questionID = (int) answerReader2.read();

            QuestionReader questionReader = new Read.Question.ReadAnswer(questionID);
            String correctAnswer;
            correctAnswer = (String) questionReader.read();
            QuestionReader questionReader1 = new Read.Question.ReadMark(questionID);
            int mark = (int) questionReader1.read();
            int studentMark;
            if (correctAnswer.equals(studentAnswer)) {
                studentMark = mark;
            } else {
                studentMark = 0;
            }

            //write the mark into database
            String sql1 = "update " + TABLE + " set mark = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, studentMark);
            preparedStatement.setInt(2, answerID);
            preparedStatement.executeUpdate();

            statement.close();
            connection.close();

            return studentMark;

        } catch (SQLException e) {
            e.printStackTrace();
            return FAILED;
        }
    }
}


