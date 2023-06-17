import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static List<RunnableForClient> clients = new ArrayList<>();
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static File settingsFile = new File("Settings.txt");
    private int port;

    public Server() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {
                String line = br.readLine();
                port = Integer.parseInt(line.substring(5, line.length()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {
            System.out.println("Сервер запущен");
            while (!server.isClosed()) {
                Socket socket = server.accept();
                RunnableForClient runnableForClient = new RunnableForClient(socket);
                clients.add(runnableForClient);
                executorService.execute(runnableForClient);
            }
            executorService.shutdown();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
