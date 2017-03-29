package Server;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionHandlerThread extends Thread {

  private static final int waitTimeBetweenPollingEventQueueMS = 1000;

  private final AtomicBoolean shouldContinueProcessing = new AtomicBoolean(true);
  private final Queue<Task> taskQueue = new PriorityQueue<>();
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
      DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());

      while (this.shouldContinueProcessing.get()) {
        if (!this.taskQueue.isEmpty()) {
          Task task = this.taskQueue.poll();
          objectOutputStream.writeObject(task);
          task.getResponseHandler().handleResponse(dataInputStream);
        }

        try {
          Thread.sleep(waitTimeBetweenPollingEventQueueMS);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      System.out.println(e.getStackTrace());
    }
  }

  public void addTask(Task task) {
    this.taskQueue.add(task);
  }
}
