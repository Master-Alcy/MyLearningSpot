import redis.naive.client.Client;
import redis.naive.server.ClientBehaviour;
import redis.naive.server.Server;

import java.util.ArrayList;
import java.util.List;

public class TestRun {

    public static void main(String[] args) {
        System.out.println("Test!!!!!");
        test1();
//        test2();
    }

    private static void test2() {
        ClientBehaviour currEnum = ClientBehaviour.valueOf("READ_ONE_TODAY");

        System.out.println(currEnum.toString());

        switch(currEnum) {
            case READ_ONE_TODAY:
                System.out.println("Yes?");
                break;
        }

        System.out.println("Finished");
    }

    private static void test1() {
        // initializing
        Server server = new Server(8888);
        Thread main = new Thread(server);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(new Client("localhost", 8888, "READ_ONE_TODAY")));
        }
        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(new Client("localhost", 8888, "READ_ONE_LASTDAY")));
        }
        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(new Client("localhost", 8888, "READ_ALL_TODAY")));
        }
        for (int i = 0; i < 2; i++) {
            threads.add(new Thread(new Client("localhost", 8888, "READ_ALL_LASTDAY")));
        }

        // running
        main.start();
        threads.forEach((o) -> {
            o.start();
        });

        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        server.stopServer();

        System.out.println("All DONE -------------------------------");
    }
}
