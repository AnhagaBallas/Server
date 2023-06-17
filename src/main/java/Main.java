import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        System.out.println("" +
                "1) Запустить сервер");
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                server.start();
                break;

            default:
                System.out.println("Неверный ввод");
                break;

        }


    }
}
