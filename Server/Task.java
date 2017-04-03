package Server;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Task extends Thread implements Serializable {

  private ObjectOutputStream streamToServer;

  public void setStreamToServer(ObjectOutputStream streamToServer) {
    this.streamToServer = streamToServer;
  }

  public abstract void execute();

  public abstract void returnResponse(ObjectOutputStream streamToServer);

  @Override
  public void run() {
    execute();
    returnResponse(this.streamToServer);
  }

  public abstract ResponseHandler getResponseHandler();

}
