package server.client.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private final ExecutorService  threadPool = Executors.newFixedThreadPool(10);
    private int port;
    private ServerSocket serverSocket;
    private boolean isStopped;

    public Server(int port) {
        this.port = port;
        this.serverSocket = null;
        this.isStopped = false;
    }

    private void startServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server Started");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port: " + this.port, e);
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stopServer() {
        try {
            this.serverSocket.close();
            this.isStopped = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            startServerSocket();

            while (!isStopped()) {
                Socket serverClientSocket = null;
                try {
                    serverClientSocket = this.serverSocket.accept();
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server has been Stopped");
                        return;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }

                this.threadPool.execute(new ServerHandler(serverClientSocket, "Multithreaded Server"));
            }
        } catch(Exception e) {
            throw new RuntimeException("Error in Server", e);
        } finally {
            this.threadPool.shutdown();
            System.out.println("Server Thread Pool fully Stopped");
        }
    }
}
