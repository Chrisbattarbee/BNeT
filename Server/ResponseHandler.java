package Server;

import java.io.ObjectInputStream;

public interface ResponseHandler {

  void handleResponse(ObjectInputStream clientDataStream, String identifier);
}
