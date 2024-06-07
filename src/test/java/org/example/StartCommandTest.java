package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StartCommandTest {

    private BlockingQueue<ICommand> queue;
    private StartCommand startCommand;

    @BeforeEach
    public void setUp() {
        queue = new LinkedBlockingQueue<>();
        startCommand = new StartCommand(queue);
    }

    @Test
    public void testHardStopCommand() throws InterruptedException {
        ICommand mockCommand = mock(ICommand.class);
        queue.put(mockCommand);

        startCommand.execute();

        Thread.sleep(100);

        HardStopCommand hardStopCommand = new HardStopCommand(startCommand);
        queue.put(hardStopCommand);

        Thread.sleep(100);

        assertFalse(startCommand.isRunning());

        verify(mockCommand, times(1)).execute();
    }

    @Test
    public void testSoftStopCommand() throws InterruptedException {
        ICommand mockCommand1 = mock(ICommand.class);
        ICommand mockCommand2 = mock(ICommand.class);
        queue.put(mockCommand1);
        queue.put(mockCommand2);

        startCommand.execute();

        Thread.sleep(100);

        SoftStopCommand softStopCommand = new SoftStopCommand(startCommand);
        queue.put(softStopCommand);

        Thread.sleep(100);

        assertFalse(startCommand.isRunning());

        verify(mockCommand1, times(1)).execute();
        verify(mockCommand2, times(1)).execute();
    }

    @Test
    public void testSoftStopCommandWithMultipleCommands() throws InterruptedException {
        ICommand mockCommand1 = mock(ICommand.class);
        ICommand mockCommand2 = mock(ICommand.class);
        ICommand mockCommand3 = mock(ICommand.class);
        queue.put(mockCommand1);
        queue.put(mockCommand2);
        queue.put(mockCommand3);

        startCommand.execute();

        Thread.sleep(100);

        SoftStopCommand softStopCommand = new SoftStopCommand(startCommand);
        queue.put(softStopCommand);

        Thread.sleep(100);

        assertFalse(startCommand.isRunning());

        verify(mockCommand1, times(1)).execute();
        verify(mockCommand2, times(1)).execute();
        verify(mockCommand3, times(1)).execute();
    }

    @Test
    public void testCommandExecutionContinuesAfterException() throws InterruptedException {
        ICommand failingCommand = mock(ICommand.class);
        doThrow(new RuntimeException("Test Exception")).when(failingCommand).execute();

        ICommand succeedingCommand1 = mock(ICommand.class);
        ICommand succeedingCommand2 = mock(ICommand.class);

        queue.put(failingCommand);
        queue.put(succeedingCommand1);
        queue.put(succeedingCommand2);

        startCommand.execute();

        Thread.sleep(300);

        verify(failingCommand, times(1)).execute();
        verify(succeedingCommand1, times(1)).execute();
        verify(succeedingCommand2, times(1)).execute();

        assertTrue(startCommand.isRunning());
    }
}

