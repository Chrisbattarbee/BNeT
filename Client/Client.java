package Client;

import Server.Task;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

  private static final String ip = "chrisbattarbee.tech";
  private static final int port = 4562;

  public static void main(String[] args) {
    try {

      Socket connectionSocket = new Socket(ip, port);
      ObjectOutputStream serverStream = new ObjectOutputStream(connectionSocket.getOutputStream());
      ObjectInputStream taskStream = new ObjectInputStream(connectionSocket.getInputStream());

      while (true) {
        Task task = (Task) taskStream.readObject();
        task.setStreamToServer(serverStream);
        task.start();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
