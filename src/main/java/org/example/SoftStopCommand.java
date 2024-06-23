package org.example;

import java.util.concurrent.BlockingQueue;

public class SoftStopCommand implements ICommand {

    private final BlockingQueue<ICommand> queue;
    private final Flag flag;

    public SoftStopCommand(BlockingQueue<ICommand> queue, Flag flag) {
        this.queue = queue;
        this.flag = flag;
    }


    @Override
    public void execute() {
        queue.add(new HardStopCommand(flag));
    }
}

