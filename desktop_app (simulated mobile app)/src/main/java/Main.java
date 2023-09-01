package main.java;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(SSESubscriber::shutdownExecutorService));

        new MyFrame();
    }
}