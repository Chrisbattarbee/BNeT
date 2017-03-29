package Server.Tasks.DenialOfService;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.IOException;

public class DenialOfServiceResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream) {
    try {
      int numberOfRequests = clientDataStream.readInt();
      String address = clientDataStream.readUTF();
      int length = clientDataStream.readInt();
      System.out.println(
          "Made " + numberOfRequests + " requests to " + address + " in " + length + " seconds");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
