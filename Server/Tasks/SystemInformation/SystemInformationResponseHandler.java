package Server.Tasks.SystemInformation;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.IOException;

public class SystemInformationResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream) {
    try {
      int numCores = clientDataStream.readInt();
      double amountOfRam = clientDataStream.readDouble();
      System.out.println(
          "Number of cores on machine: " + numCores + ", amount of RAM: " + amountOfRam + " GB");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
