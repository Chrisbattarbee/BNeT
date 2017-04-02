package Server.Tasks.DisplayMessage;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.IOException;

public class DisplayMessageResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream, String identifier) {
    try {
      String message = clientDataStream.readUTF();
      System.out.println("Displayed " + message + " on " + identifier + "'s screen");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
