import java.util.ArrayList;

public class Group {
    String name;
    Teacher teacher;
    ArrayList<String> posts;
    ArrayList<Student> students;
    int ID;
//createGroupCommand 应该是Group(...)
    public Group(String s1, Teacher s2, ArrayList<String> s3, ArrayList<Student> s4, int i1){
        name=s1;
        teacher=s2;
        posts=s3;
        students=s4;
        ID=i1;
    }
//Teacher 最先创建组的时候可以没有student加入
    public Group(String s1, Teacher s2, ArrayList<String> s3, int i1){
        name=s1;
        teacher=s2;
        posts=s3;
        students=null;
        ID=i1;
    }

    public String getName() {
        return name;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public ArrayList<String> getPosts() {
        return posts;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
    public int getID() {
        return ID;
    }
    public boolean changeName(String name) {
        this.name = name;
        return true;
    }
    public boolean invite(Student student) {
        this.students.add(student);
        return true;
    }
    public boolean deleteMember(Student student) {
        if (this.students.contains(student)){
            this.students.remove(student);
            return true;}
        else{
            return false;
        }
    }
    public boolean createAnnouncement(String announcement) {
        this.posts.add(announcement);
        return true;
    }
}