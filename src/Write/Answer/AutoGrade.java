package Write.Answer;

import Read.Answer.AnswerReader;
import Read.Answer.ReadAnswer;
import Read.Answer.ReadID;
import Read.Answer.ReadQuestionID;
import Read.Question.QuestionReader;

import java.sql.*;

public class AutoGrade extends AnswerWriter {
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

            String studentAnswer = (String) new ReadAnswer(answerID).read();
            int questionID = (int) new ReadQuestionID(answerID).read();

            QuestionReader questionReader = new Read.Question.ReadAnswer(questionID);
            String correctAnswer = (String) questionReader.read();

            //give student a mark of 0/1
            int studentMark;
            if (correctAnswer.equals(studentAnswer)) {
                studentMark = 1;
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


