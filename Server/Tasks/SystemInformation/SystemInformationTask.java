package Server.Tasks.SystemInformation;

import Server.ResponseHandler;
import Server.Task;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

public class SystemInformationTask extends Task {

  private int cpuCores;
  private double amountOfRamInGB;

  public static SystemInformationTask buildFromScanner(Scanner scanner) {
    return new SystemInformationTask();
  }

  @Override
  public void execute() {
    this.cpuCores = Runtime.getRuntime().availableProcessors();
    this.amountOfRamInGB = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
        .getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / Math.pow(2, 30);
  }

  @Override
  public void returnResponse(ObjectOutputStream streamToServer) {
    System.out.println();
    try {
      streamToServer.writeInt(this.cpuCores);
      streamToServer.writeDouble(this.amountOfRamInGB);

      streamToServer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new SystemInformationResponseHandler();
  }

}
