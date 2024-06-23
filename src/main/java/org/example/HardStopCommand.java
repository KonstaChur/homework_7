package org.example;

public class HardStopCommand implements ICommand {

    private final Flag flag;

    public HardStopCommand(Flag flag) {
        this.flag = flag;
    }

    @Override
    public void execute() {
        flag.setFlag(false);
        System.out.println("Цикл остановлен командой HardStopCommand.");
    }
}



