package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerThread extends Thread {

  private final int portNum;

  private final List<ConnectionHandlerThread> connectionThreads = new ArrayList<>();

  private final AtomicBoolean acceptConnections = new AtomicBoolean(true);

  public ServerThread(int portNum) {
    this.portNum = portNum;
  }

  public void kill() {
    this.connectionThreads.forEach(x -> x.kill());

    this.acceptConnections.set(false);
  }

  @Override
  public void run() {
    try {

      ServerSocket socket = new ServerSocket(this.portNum);

      while (this.acceptConnections.get()) {
        Socket clientSocket = socket.accept();
        createClientHandler(clientSocket);
        System.out.println(
            "Connection from " + clientSocket.getInetAddress() + " port: " + clientSocket
                .getPort());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createClientHandler(Socket clientSocket) {
    ConnectionHandlerThread thread = new ConnectionHandlerThread(clientSocket);
    this.connectionThreads.add(thread);
    thread.start();
  }

  public void addTask(Task task) {
    this.connectionThreads.forEach(x -> x.addTask(task));
  }
}
