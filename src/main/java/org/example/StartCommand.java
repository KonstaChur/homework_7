package org.example;

import java.util.concurrent.BlockingQueue;

public class StartCommand implements ICommand {

    private final BlockingQueue<ICommand> queue;
    private volatile boolean flag = true;

    public StartCommand(BlockingQueue<ICommand> queue) {
        this.queue = queue;
    }

    @Override
    public void execute() {
        new Thread(() -> {
            while (flag) {

                try {
                    ICommand command = queue.take();
                    command.execute();
                    System.out.println("Старт потока " + Thread.currentThread().getName());
                } catch (Exception e) {
                    System.out.println("Пришло исключение в StartCommand " + e.getMessage());
                }
            }
        }).start();
    }

    public void hardStop() {
        flag = false;
    }

    public void softStop() {
        new Thread(() -> {
            while (!queue.isEmpty()) {
                try {
                    ICommand command = queue.take();
                    command.execute();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            flag = false;
        }).start();
    }

    public boolean isRunning() {
        return flag;
    }
}

