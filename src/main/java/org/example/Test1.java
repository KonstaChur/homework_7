package org.example;
public class Test1 implements ICommand {

    private final int i;

    public Test1(int i) {
        this.i = i;
    }

    @Override
    public void execute() {
        System.out.println("Test1 = "+i);
    }
}
