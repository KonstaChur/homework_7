package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StartCommandTest {

    private BlockingQueue<ICommand> queue;
    private Flag flag;

    @BeforeEach
    public void setUp() {
        queue = new ArrayBlockingQueue<>(10);
        flag = new Flag(true);
    }

    @Test
    public void testStartCommandWithHardStop() throws InterruptedException {
        StartCommand startCommand = new StartCommand(queue, flag);
        HardStopCommand hardStopCommand = new HardStopCommand(flag);

        queue.add(hardStopCommand);

        Thread startThread = new Thread(startCommand::execute);
        startThread.start();

        startThread.join(1000);

        assertFalse(flag.isFlag());
    }

    @Test
    public void testStartCommandWithSoftStop() throws InterruptedException {
        StartCommand startCommand = new StartCommand(queue, flag);
        SoftStopCommand softStopCommand = new SoftStopCommand(queue, flag);

        queue.add(softStopCommand);

        Thread startThread = new Thread(startCommand::execute);
        startThread.start();

        startThread.join(1000);

        assertFalse(flag.isFlag());
    }

    @Test
    public void testStartCommandNewTreadWithHardStop() throws InterruptedException {
        StartCommandNewTread startCommandNewTread = new StartCommandNewTread(queue, flag);
        HardStopCommand hardStopCommand = new HardStopCommand(flag);

        startCommandNewTread.execute();

        queue.add(hardStopCommand);

        Thread.sleep(1000);

        assertFalse(flag.isFlag());
    }

    @Test
    public void testStartCommandNewTreadWithSoftStop() throws InterruptedException {
        StartCommandNewTread startCommandNewTread = new StartCommandNewTread(queue, flag);
        SoftStopCommand softStopCommand = new SoftStopCommand(queue, flag);

        startCommandNewTread.execute();

        queue.add(softStopCommand);

        Thread.sleep(2000);

        assertFalse(flag.isFlag());
    }

    @Test
    public void testStartCommandHandlesException() throws InterruptedException {
        ICommand failingCommand = Mockito.mock(ICommand.class);
        doThrow(new RuntimeException("Test Exception")).when(failingCommand).execute();

        queue.add(failingCommand);
        queue.add(new HardStopCommand(flag));

        StartCommand startCommand = new StartCommand(queue, flag);
        Thread startCommandThread = new Thread(startCommand::execute);
        startCommandThread.start();

        startCommandThread.join(1000);

        assertFalse(flag.isFlag());

        Mockito.verify(failingCommand).execute();
    }
}

