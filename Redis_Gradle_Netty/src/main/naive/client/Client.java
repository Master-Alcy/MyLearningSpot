package naive.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class Client implements Runnable{
    private Socket client;
    private String hostName;
    private int port;
    private String currentBehaviour;

    public Client(String hostName, int port, String behaviour) {
        this.client = null;
        this.hostName = hostName;
        this.port = port;
        this.currentBehaviour = behaviour;
    }

    @Override
    public void run() {
        System.out.println("New Client running on: " + currentBehaviour);

        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            client = new Socket(hostName, port);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());

            switch (currentBehaviour) {
                case "READ_ONE_TODAY":
                    System.out.println("We in READ_ONE_TODAY");
                    // requesting
                    output.writeUTF("READ_ONE_TODAY");
                    output.flush();

                    // receiving
                    int len1 = input.readInt();
                    if (len1 <= 0) {
                        // error
                    }
                    byte[] result1 = new byte[len1];
                    input.readFully(result1, 0, len1);

                    String receivedID1 = new String(result1);
                    System.out.println("Back for READ_ONE_TODAY: " + receivedID1);
                    break;

                case "READ_ONE_LASTDAY":
                    System.out.println("We in READ_ONE_LASTDAY");
                    // requesting
                    output.writeUTF("READ_ONE_LASTDAY");
                    output.flush();

                    // receiving
                    int len2 = input.readInt();
                    if (len2 <= 0) {
                        // error
                    }
                    byte[] result2 = new byte[len2];
                    input.readFully(result2, 0, len2);

                    String receivedID2 = new String(result2);
                    System.out.println("Back for READ_ONE_LASTDAY: " + receivedID2);
                    break;

                case "READ_ALL_TODAY":
                    System.out.println("We in READ_ALL_TODAY");
                    // requesting
                    output.writeUTF("READ_ALL_TODAY");
                    output.flush();

                    // receiving
                    Map<String, byte[]> receivedMap1 = (Map<String, byte[]>)input.readObject();
                    System.out.println("Back for READ_ALL_TODAY: " + receivedMap1.toString());
                    break;

                case "READ_ALL_LASTDAY":
                    System.out.println("We in READ_ALL_LASTDAY");
                    // requesting
                    output.writeUTF("READ_ALL_LASTDAY");
                    output.flush();

                    // receiving
                    Map<String, byte[]> receivedMap2 = (Map<String, byte[]>)input.readObject();
                    System.out.println("Back for READ_ALL_LASTDAY: " + receivedMap2.toString());
                    break;

                default:
                    System.out.println("Something Not Supported");
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        } finally {
            try {
                input.close();
                output.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error closing client", e);
            }
        }
    }
}
