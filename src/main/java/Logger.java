import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance = null;
    private static File logFile = new File("ServerLog.txt");
    private static FileWriter fileWriter;

    private Logger() {
    }

    public static Logger get() {
        try {
            fileWriter = new FileWriter(logFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(String messege, String name) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String log = dateFormat.format(date) + " - " + name + " - " + messege + "\n";
        try {
            fileWriter.write(log);
            fileWriter.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(log);


    }


}
