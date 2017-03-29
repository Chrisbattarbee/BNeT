package Server;

import java.io.DataInputStream;

public interface ResponseHandler {

  void handleResponse(DataInputStream clientDataStream);
}
