import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class TestRunnableForClient {
    @Test
    public void testRun() {
        Client client1 = new Client("localhost", "Test");
        Client client2 = new Client("localhost", "Test1");
        client1.connect();
        client2.connect();


    }


    public class Client {
        private static File settingsFile = new File("Settings.txt");
        private static File clientLog = new File("ClientLog.txt");
        public BufferedReader br;
        public DataOutputStream out;
        public BufferedReader in;
        private String host;
        private int port;
        private String nickname;
        private Logger logger = Logger.get();
        private Socket socket;

        public Client(String host, String nickname) {
            this.host = host;
            this.nickname = nickname;
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {
                    String line = br.readLine();
                    port = Integer.parseInt(line.substring(5, line.length()));
                }
                socket = new Socket(host, port);
                br = new BufferedReader(new InputStreamReader(System.in));
                out = new DataOutputStream(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void connect() {
            try {
                System.out.println("Client connected to socket.");
                System.out.println("Please choose your nickname");
                String clientCommand;
                System.out.println(nickname);
                out.writeUTF(nickname);
                out.flush();
                while (!socket.isOutputShutdown()) {
                    clientCommand = "Test message";
                    out.writeUTF(clientCommand);
                    out.flush();
                    System.out.println(in.readLine());
                    break;
                }


            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }


}
