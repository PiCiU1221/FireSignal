package firesignal;

import firesignal.menuholders.MyFrame;
import firesignal.utils.SSESubscriber;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(SSESubscriber::shutdownExecutorService));

        new MyFrame();
    }
}