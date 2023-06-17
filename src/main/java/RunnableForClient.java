import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RunnableForClient implements Runnable {
    private static Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String name;
    private Logger logger = Logger.get();

    public RunnableForClient(Socket socket) {
        try {
            RunnableForClient.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            name = in.readUTF();
            while (!socket.isClosed()) {
                String entry = in.readUTF();
                if (entry.equals("/exit")) {
                    System.out.println("You've disconected from server");
                    out.writeUTF("Client " + name + " have disconected");
                    out.flush();
                    in.close();
                    out.close();
                    socket.close();
                }
                logger.log(entry, name);
                for (RunnableForClient client : Server.clients) {
                    client.out.writeUTF(sendMessage(entry, name));
                    out.flush();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String sendMessage(String message, String name) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String log = dateFormat.format(date) + " - " + name + " - " + message + "\n";
        return log;

    }
}