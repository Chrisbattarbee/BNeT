package Server.Tasks.DisplayMessage;

import Server.ResponseHandler;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DisplayMessageResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(ObjectInputStream clientDataStream, String identifier) {
    try {
      String message = clientDataStream.readUTF();
      System.out.println("Displayed " + message + " on " + identifier + "'s screen");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
