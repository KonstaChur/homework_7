package org.example;

public class HardStopCommand implements ICommand {

    private final StartCommand startCommand;

    public HardStopCommand(StartCommand startCommand) {
        this.startCommand = startCommand;
    }

    @Override
    public void execute() {
        startCommand.hardStop();
        System.out.println("Запустил Hardstop");
    }
}



