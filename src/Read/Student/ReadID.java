package Read.Student;


public class ReadID extends StudentReader {
    private String name;

    public ReadID(String name) {
        this.name = name;
    }

    @Override
    public Object read() {
        return readInfo(name, TABLE, IDCol, INT);
    }
}
