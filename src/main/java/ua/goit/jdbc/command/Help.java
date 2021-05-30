package ua.goit.jdbc.command;

import ua.goit.jdbc.view.View;

public class Help implements Command{
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    public void process() {
        view.write("create - to add info in database");
        view.write("read - to get existing info in database");
        view.write("update - to update existing info in database");
        view.write("delete - to delete info in database");
        view.write("exit - exit from an application");
    }

}
