package org.example;

public class Test2 implements ICommand {
    @Override
    public void execute() {
        throw new RuntimeException("Test2 Exception");
    }
}
