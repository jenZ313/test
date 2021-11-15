import Read.Group.GroupReader;
import Read.Group.ReadCreator;
import Read.Group.ReadStudents;
import Read.Question.QuestionReader;
import Read.Student.ReadID;
import Read.Student.ReadName;
import Read.Student.StudentReader;
import Read.Teacher.TeacherReader;
import Read.Test.ReadMark;
import Read.Test.TestReader;
import Write.Group.GroupWriter;
import Write.Group.WriteStudents;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(timeout = 5000)
    public void testNewStudent() {
        Command c = new registerCommand("Gavin", "Gavin666", "Gavin666@gmail.com", 12);
        c.execute();
        StudentReader studentReader = new ReadID("Gavin");
        int ID = (int) studentReader.read();
        StudentReader studentReader1 = new ReadName(ID);
        String name = (String) studentReader1.read();
        assertEquals(name, "Gavin");
    }
    @Test(timeout = 5000)
    public void testNewTeacher() {
        Command c = new registerCommand("Jacky", "123456", "jacky@gmail.com", 11);
        c.execute();
        TeacherReader teacherReader = new Read.Teacher.ReadID("Jacky");
        int ID = (int) teacherReader.read();
        TeacherReader teacherReader1 = new Read.Teacher.ReadName(ID);
        String name = (String) teacherReader1.read();
        assertEquals(name, "Jacky");
    }

    @Test(timeout = 5000)
    public void testCreateGroup() {
        TeacherReader teacherReader = new Read.Teacher.ReadID("Jacky");
        int ID = (int) teacherReader.read();
        Command c = new createGroupCommand(ID, "group");
        c.execute();
        GroupReader groupReader = new Read.Group.ReadID("group");
        int ID1 = (int) groupReader.read();
        GroupReader groupReader1 = new ReadCreator(ID1);
        int name =(int) groupReader1.read();
        assertEquals(name, ID);
    }

    @Test(timeout = 50000)
    public void testQuestion() {
        Command c = new createQuestationCommand("unittest", "lol", "笑", 5);
        c.execute();
        QuestionReader questionReader = new Read.Question.ReadID("unittest", "lol", "笑");
        int ID1 = (int) questionReader.read();
        Command e = new addQuestionToTestCommand(ID1, 1);
        e.execute();
        StudentReader studentReader = new ReadID("Gavin");
        int ID = (int) studentReader.read();
        String[] answer = {"笑"};
        Command d = new submitAnswerCommand(ID, answer, 1, 1);
        d.execute();
        Command f = new gradeTest(ID, 1);
        f.execute();
        TestReader testReader = new ReadMark(ID);
        int mark =(int) testReader.read();
        assertEquals(5, mark);
    }


    @Test(timeout = 5000)
    public void testDeleteMember() {
        TeacherReader teacherReader = new Read.Teacher.ReadID("Jacky");
        GroupReader groupReader = new Read.Group.ReadID("group");
        int ID1 = (int) groupReader.read();
        Command c = new joinGroupCommand(2, 5);
        c.execute();
        assertEquals(1,1);
    }
}