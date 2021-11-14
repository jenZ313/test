package Read.Student;

import Read.Reader;

public abstract class StudentReader extends Reader {

    public final String TABLE = "STUDENT";
    public final String ID = "id";
    public final String NAME = "name";
    public final String PASS = "pass";
    public final String DATE = "date";
    public final String EMAIL = "email";
    public final String GROUPS = "groupID";
    public final String TESTS = "testID";
    public final String ANSWERS = "answerID";


    public final int IDCol = 1;
    public final int NAMECol = 2;
    public final int PASSCol = 3;
    public final int DATECol = 4;
    public final int EMAILCol = 5;
    public final int GROUPSCol = 7;
    public final int TESTSCol = 8;
    public final int ANSWERSCol = 9;

}

