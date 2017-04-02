package Server.Tasks.DenialOfService;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.IOException;

public class DenialOfServiceResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream, String identifier) {
    try {
      int numberOfRequests = clientDataStream.readInt();
      String address = clientDataStream.readUTF();
      int length = clientDataStream.readInt();
      System.out.println(
          "Made " + numberOfRequests + " requests to " + address + " in " + length + " seconds"
              + "from " + identifier);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
