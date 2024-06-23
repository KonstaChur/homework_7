package org.example;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class StartCommandNewTread implements ICommand {

    private final BlockingQueue<ICommand> queue;
    private final Flag flag;

    @Override
    public void execute() {
        StartCommand startCommand = new StartCommand(queue, flag);
        Thread startCommandThread = new Thread(startCommand::execute);
        startCommandThread.start();
        System.out.println("StartCommand запущен в отдельном потоке.");
    }
}

