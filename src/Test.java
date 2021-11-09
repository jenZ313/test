import java.util.ArrayList;

public class Test {
    String name;
    Teacher teacher;
    ArrayList<String> questions;
    int ID;
    int timeLimit;
    int Price;

    public Test(String s1, Teacher s2, ArrayList<String> s3, int i1, int i2, int i3){
        name=s1;
        teacher=s2;
        questions=s3;
        ID=i1;
        timeLimit=i2;
        Price=i3;

    }
    //Teacher 最先创建Test的时候可以没有questions
    public Test(String s1, Teacher s2, int i1, int i2, int i3){
        name=s1;
        teacher=s2;
        questions=null;
        ID=i1;
        timeLimit=i2;
        Price=i3;
    }

    public String getName() {
        return name;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public ArrayList<String> getQuestions() {
        return questions;
    }
    public int getID() {
        return ID;
    }
    public int getTimeLimit() {
        return timeLimit;
    }
    public int getPrice() {
        return Price;
    }
    public void addQuestion(String question) {
        this.questions.add(question);
    }
    //release mark还没写
}