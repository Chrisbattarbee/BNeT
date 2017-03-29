package Server;

import java.io.DataOutputStream;
import java.io.Serializable;

public abstract class Task extends Thread implements Serializable {

  private DataOutputStream streamToServer;

  public void setStreamToServer(DataOutputStream streamToServer) {
    this.streamToServer = streamToServer;
  }

  public abstract void execute();

  public abstract void returnResponse(DataOutputStream streamToServer);

  @Override
  public void run() {
    execute();
    returnResponse(this.streamToServer);
  }

  public abstract ResponseHandler getResponseHandler();

}
