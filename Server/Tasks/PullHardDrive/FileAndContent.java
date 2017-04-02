package Server.Tasks.PullHardDrive;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

public class FileAndContent extends File {

  private List<String> content;

  public FileAndContent(String pathname) {
    super(pathname);
    fillContents();
  }

  public FileAndContent(String parent, String child) {
    super(parent, child);
    fillContents();
  }

  public FileAndContent(File parent, String child) {
    super(parent, child);
    fillContents();
  }

  public FileAndContent(URI uri) {
    super(uri);
    fillContents();
  }

  private void fillContents() {
    if (this.canRead()) {
      try {
        this.content = Files.readAllLines(this.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      this.content = null;
    }
  }

  public List<String> getContent() {
    return this.content;
  }

  public void setContent(List<String> contents) {
    this.content = contents;
  }
}
