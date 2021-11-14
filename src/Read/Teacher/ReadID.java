package Read.Teacher;

public class ReadID extends TeacherReader {
    private String name;

    public ReadID(String name) {
        this.name = name;
    }

    @Override
    public Object read() {
        return readInfo(name, TABLE, IDCol, INT);
    }
}
