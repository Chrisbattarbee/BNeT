package Server.Tasks.PullHardDrive;

import Server.ResponseHandler;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PullHardDriveResponseHandler implements ResponseHandler {

  @Override
  public void handleResponse(ObjectInputStream clientDataStream, String identifier) {

    int x = 0;

    try {
      while (true) {
        FileAndContent file = (FileAndContent) clientDataStream.readObject();
        Path fileToWrite = Paths.get(System.getProperty("user.dir") + identifier + "/" + file);
        fileToWrite.toFile().getParentFile().mkdirs();
        fileToWrite.toFile().createNewFile();

        Files.write(fileToWrite, file.getContent(), Charset.forName("UTF-8"));
        x++;
        System.out.println(x);
      }
    } catch (EOFException e) {
      System.out.println("Pulled " + x + " files from " + identifier);
      return;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

}
