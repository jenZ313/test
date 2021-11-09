public interface Command {
    public int execute();
}

class loginStudentCommand implements Command {
    private Student student;
    private String name;
    private String pass;
    @Override
    public int execute() {

        return student.login(name, pass);
    }
}
class loginTeacherCommand implements Command {
    @Override
    public int execute() {
        ;
        return 11;
    }
}


class quitGroupCommand implements Command {

    @Override
    public int execute() {

    }
}

class joinCommand implements Command {

    @Override
    public int execute() {

    }
}

class registerstudentCommand implements Command {

    @Override
    public int execute() {

    }
}

class registerteacherCommand implements Command {

    @Override
    public int execute() {

    }
}

class createGroupCommand implements Command {

    @Override
    public int execute() {

    }
}

class changeNameCommand implements Command {

    @Override
    public int execute() {

    }
}

class createTestCommand implements Command {

    @Override
    public int execute() {

    }
}

class addQuestationCommand implements Command {

    @Override
    public int execute() {

    }
}

class releaseMarkCommand implements Command {

    @Override
    public int execute() {

    }
}

class inviteCommand implements Command {

    @Override
    public int execute() {

    }
}

class deleteMemberCommand implements Command {

    @Override
    public int execute() {

    }
}

class createAnnouncementCommand implements Command {

    @Override
    public int execute() {

    }
}

class submitCommand implements Command {

    @Override
    public int execute() {

    }
}

