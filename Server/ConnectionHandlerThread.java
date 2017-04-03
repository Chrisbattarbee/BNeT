package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionHandlerThread extends Thread {

  private static final int waitTimeBetweenPollingEventQueueMS = 1000;

  private final AtomicBoolean shouldContinueProcessing = new AtomicBoolean(true);
  private final Queue<Task> taskQueue = new ArrayDeque<Task>();
  Socket clientSocket;

  public ConnectionHandlerThread(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void kill() {
    this.shouldContinueProcessing.set(false);
  }

  @Override
  public void run() {
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(
          this.clientSocket.getOutputStream());
      ObjectInputStream dataInputStream = new ObjectInputStream(this.clientSocket.getInputStream());

      while (this.shouldContinueProcessing.get()) {
        if (!this.taskQueue.isEmpty()) {
          Task task = this.taskQueue.poll();
          objectOutputStream.writeObject(task);
          task.getResponseHandler().handleResponse(dataInputStream,
              this.clientSocket.getInetAddress().toString());
        }

        try {
          Thread.sleep(waitTimeBetweenPollingEventQueueMS);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addTask(Task task) {
    this.taskQueue.add(task);
  }
}
