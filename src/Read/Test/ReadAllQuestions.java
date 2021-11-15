package Read.Test;

import Read.Question.QuestionReader;
import Read.Question.ReadAnswer;
import Read.Question.ReadQuestion;

public class ReadAllQuestions extends TestReader {
    private final int testID;

    public ReadAllQuestions(int testID) {
        this.testID = testID;
    }

    @Override
    public Object read() {
        String allQuestionID = (String) new ReadQuestions(testID).read();
        if (allQuestionID.equals(FAILED + "")) {
            return FAILED;
        }

        String[] questionList = allQuestionID.split(",");
        String[] questions = new String[questionList.length - 1];
        for (int i = 0; i < questionList.length; i++) {
            QuestionReader questionReader = new ReadQuestion(Integer.parseInt(questionList[i]));
            questions[i] = (String) questionReader.read();
        }
        return questionList;
    }
}
