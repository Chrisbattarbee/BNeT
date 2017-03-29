package Server.Tasks.DisplayMessage;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.IOException;

public class DisplayMessageResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream) {
    try {
      String message = clientDataStream.readUTF();
      System.out.println("Displayed " + message + " on clients screen");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
