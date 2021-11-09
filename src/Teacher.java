import java.util.ArrayList;

public class Teacher {
    String name;
    String pass;
    String email;;
    int ID;
    ArrayList<Group> group;

    public Teacher(String s1, String s2, String s3, int i1, ArrayList<Group> g1){
        name=s1;
        pass=s2;
        email=s3;
        ID=i1;
        group = g1;
    }
//Teacher 可以先不加group
    public Teacher(String s1, String s2, String s3, int i1){
        name=s1;
        pass=s2;
        email=s3;
        ID=i1;
        group = null;
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
    public int getID() {
        return ID;
    }
    public ArrayList<Group> getGroup() {return group;}
    public int login(String name, String pass) {
        if (this.name == name & this.pass == pass){
            return 11;}
        else{
            return 0;}
    }
}