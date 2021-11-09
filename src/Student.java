import java.util.ArrayList;

public class Student {
    String name;
    String pass;
    String email;
    ArrayList<String> word_learnt;
    int ID;
    int level;
    int learn_days;
    int sign_in;
    Group group;
//register command 应该就是student(...)
    public Student(String s1, String s2, String s3, ArrayList<String> s4, int i1, int i2, int i3, int i4, Group g1){
        name=s1;
        pass=s2;
        email=s3;
        word_learnt=s4;
        ID=i1;
        level=i2;
        learn_days=i3;
        sign_in=i4;
        group=g1;
    }
//假设student在同一时间只能加一个组， 最先创建的时候student可以没有组
    public Student(String s1, String s2, String s3, ArrayList<String> s4, int i1, int i2, int i3, int i4){
        name=s1;
        pass=s2;
        email=s3;
        word_learnt=s4;
        ID=i1;
        level=i2;
        learn_days=i3;
        sign_in=i4;
        group=null;
    }
    public String getName() {
        return name;
    }
    public String getPass() {
        return pass;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<String> getWord_learnt() {
        return word_learnt;
    }
    public int getID() {
        return ID;
    }
    public int getLevel() {
        return level;
    }
    public int getLearn_days() {
        return learn_days;
    }
    public int getSign_in() {
        return sign_in;
    }
    public Group getGroup() {return group;}
    public int quitGroup() {
        if (this.group != null) {
            this.group.deleteMember(this);
            this.group = null;
            return 1;}
        else{
            return 0;
        }
    }
    public int login(String name, String pass) {
        if (this.name == name & this.pass == pass){
        return 12;}
        else{
        return 0;}
    }
    public int join(int groupID, Group group) {
        if (groupID == group.getID()){
            group.invite(this);
            return 1;}
        else{
            return 0;}

    }
    //没写完   暂时不知道重名怎么写
    public int changeName(String name) {
        this.name = name;
        return 1;
    }
//logout 和 submit有亿点点迷茫

}
