package Server.Tasks.SystemInformation;

import Server.ResponseHandler;
import Server.Task;
import java.io.DataOutputStream;
import java.io.IOException;
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
  public void returnResponse(DataOutputStream streamToServer) {
    try {
      streamToServer.writeInt(this.cpuCores);
      streamToServer.writeDouble(this.amountOfRamInGB);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new SystemInformationResponseHandler();
  }

}
