package Read;

public abstract class GroupReader extends Reader {
    @Override
    public abstract Object read();
}

class ReadGroupStudents extends GroupReader {

    @Override
    public Object read() {
        return null;
    }
}
