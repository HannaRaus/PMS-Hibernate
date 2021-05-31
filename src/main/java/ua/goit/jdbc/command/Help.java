package ua.goit.jdbc.command;

import ua.goit.jdbc.view.View;

public class Help implements Command {
    private static final String MAIN_MENU = """
            create - to add info in database
            read - to get existing info in database
            update - to update existing info in database
            delete - to delete info in database
            exit - exit from an application""";
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
        view.write(MAIN_MENU);
    }

}
