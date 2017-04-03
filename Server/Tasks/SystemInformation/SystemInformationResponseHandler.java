package Server.Tasks.SystemInformation;

import Server.ResponseHandler;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SystemInformationResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(ObjectInputStream clientDataStream, String identifier) {
    try {
      int numCores = clientDataStream.readInt();
      double amountOfRam = clientDataStream.readDouble();
      System.out.println(
          "Number of cores on " + identifier + ": " + numCores + ", amount of RAM: " + amountOfRam
              + " GB");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
