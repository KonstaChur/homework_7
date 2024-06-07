package org.example;

public class SoftStopCommand implements ICommand {

    private final StartCommand startCommand;

    public SoftStopCommand(StartCommand startCommand) {
        this.startCommand = startCommand;
    }

    @Override
    public void execute() {
        startCommand.softStop();
        System.out.println("Запустил SoftStop");
    }
}

