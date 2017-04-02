package Server.Tasks.PullHardDrive;

import Server.ResponseHandler;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PullHardDriveResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(DataInputStream clientDataStream, String identifier) {

    ObjectInputStream objectInputStream = (ObjectInputStream) ((InputStream) clientDataStream);

    try {
      while (true) {
        FileAndContent file = (FileAndContent) objectInputStream.readObject();
        Path fileToWrite = Paths.get(System.getProperty("user.dir") + identifier + "/" + file);
        Files.write(fileToWrite, file.getContent(), Charset.forName("UTF-8"));
      }
    } catch (EOFException e) {
      return;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

}
