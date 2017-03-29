package Server.Tasks.DisplayMessage;

import Server.ResponseHandler;
import Server.Task;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class DisplayMessageTask extends Task {

  private final String message;
  private final int fontSize;

  public DisplayMessageTask(String message, int fontSize) {
    this.message = message;
    this.fontSize = fontSize;
  }

  public static DisplayMessageTask buildFromScanner(Scanner scanner) {
    String message = "";
    while (!scanner.hasNextInt()) {
      message += scanner.next() + " ";
    }
    int fontSize = scanner.nextInt();

    return new DisplayMessageTask(message, fontSize);
  }

  @Override
  public void execute() {
    JFrame jf = new JFrame("Master");
    Container cp = jf.getContentPane();
    jf.setSize(800, 600);
    MyCanvas tl = new MyCanvas(jf);
    cp.add(tl);
    jf.setVisible(true);
  }

  @Override
  public void returnResponse(DataOutputStream streamToServer) {
    try {
      streamToServer.writeUTF(this.message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseHandler getResponseHandler() {
    return new DisplayMessageResponseHandler();
  }

  private class MyCanvas extends JComponent {

    JFrame frame;

    public MyCanvas(JFrame frame) {
      super();
      this.frame = frame;
    }

    @Override
    public void paintComponent(Graphics g) {
      if (g instanceof Graphics2D) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Verdana", Font.BOLD, DisplayMessageTask.this.fontSize);
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (this.frame.getWidth() - metrics.stringWidth(DisplayMessageTask.this.message)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((this.frame.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.setFont(font);
        g2.drawString(DisplayMessageTask.this.message, x, y);
      }
    }
  }
}
