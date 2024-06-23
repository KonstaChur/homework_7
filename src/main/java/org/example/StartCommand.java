package org.example;

import java.util.concurrent.BlockingQueue;

public class StartCommand implements ICommand {

    private final BlockingQueue<ICommand> queue;
    private final Flag flag;

    public StartCommand(BlockingQueue<ICommand> queue, Flag flag) {
        this.queue = queue;
        this.flag = flag;
    }

    @Override
    public void execute() {
            while (flag.isFlag()) {
                try {
                    ICommand command = queue.take();
                    command.execute();
                } catch (Exception e) {
                    System.out.println("Пришло исключение в StartCommand: " + e.getMessage());
                }
            }
    }
}




