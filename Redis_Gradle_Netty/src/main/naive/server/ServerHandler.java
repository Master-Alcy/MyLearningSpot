package naive.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerHandler implements Runnable {
    private Socket serverClientSocket;
    private String name;
    private testContainer data;

    public ServerHandler(Socket serverClientSocket, String name) {
        this.serverClientSocket = serverClientSocket;
        this.name = name;
        this.data = new testContainer();
    }

    private class testContainer {
        public String ID1;
        public String ID2;
        public byte[] TRADE1;
        public byte[] TRADE2;
        public Map<String, byte[]> MAP1;
        public Map<String, byte[]> MAP2;

        public testContainer() {
            ID1 = "TODAY - Some Random Objects";
            ID2 = "YESTERDAY - Some Other Random Objects";
            TRADE1 = ID1.getBytes();
            TRADE2 = ID2.getBytes();
            MAP1 = new HashMap<String, byte[]>();
            MAP1.put(ID1, TRADE1);
            MAP2 = new HashMap<String, byte[]>();
            MAP1.put(ID2, TRADE2);
        }
    }

    @Override
    public void run() {
        System.out.println("New Handler running");

        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            output = new ObjectOutputStream(serverClientSocket.getOutputStream());
            input = new ObjectInputStream(serverClientSocket.getInputStream());

            String word = input.readUTF();
            System.out.println("Handing: " + word);
            ClientBehaviour curr = ClientBehaviour.valueOf(word);

            switch (curr) {
                case READ_ONE_TODAY:
                    System.out.println("In ServerHandler, READ_ONE_TODAY. Call ServerUtils");
                    System.out.println("Pretend we got the byte[] Back.");
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Output
                    output.writeInt(data.TRADE1.length);
                    output.write(data.TRADE1);
                    output.flush();

                    return;
                case READ_ONE_LASTDAY:
                    System.out.println("In ServerHandler, READ_ONE_LASTDAY. Call ServerUtils");
                    System.out.println("Pretend we got the byte[] Back.");
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Output
                    output.writeInt(data.TRADE2.length);
                    output.write(data.TRADE2);
                    output.flush();

                    return;
                case READ_ALL_TODAY:
                    System.out.println("In ServerHandler, READ_ALL_TODAY. Call ServerUtils");
                    System.out.println("Pretend we got the Object Back.");
                    try {
                        TimeUnit.SECONDS.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Output
                    output.writeObject(data.MAP1);
                    output.flush();

                    return;
                case READ_ALL_LASTDAY:
                    System.out.println("In ServerHandler, READ_ALL_LASTDAY. Call ServerUtils");
                    System.out.println("Pretend we got the Object Back.");
                    try {
                        TimeUnit.SECONDS.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Output
                    output.writeObject(data.MAP2);
                    output.flush();

                    return;
                default:
                    System.out.println("Something Not Supported");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error in ServerHandler", e);
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error closing ServerHandler", e);
            }
        }
    }
}
