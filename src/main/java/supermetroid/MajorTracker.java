package supermetroid;

import supermetroid.trackable.Trackable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MajorTracker extends JFrame {
  public boolean reset = false;
  private List<Trackable> trackables;

  public MajorTracker(Map<Point, Trackable> trackablesByLocation) throws Exception {
    setTitle("Super Metroid - Major Tracker");
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
          case 'r': reset = true; break;
          case 's': takeScreenshot(); break;
        }
      }
    });

    JPanel panel = new JPanel(new GridLayout(4,5,2,2));
    panel.setBackground(Color.BLACK);
    setContentPane(panel);
    trackables = new ArrayList<>(trackablesByLocation.values());

    var black = new ImageIcon(ImageIO.read(Objects.requireNonNull(MajorTracker.class.getResourceAsStream("/black.png"))));

    for (var row = 0; row < 4; row++) {
      for (var col = 0; col < 5; col++) {
        Point p;

        JLabel label =  new JLabel();
        panel.add(label);
        label.setSize(64,64);
        label.setIcon(black);

        if (trackablesByLocation.containsKey(p = new Point(col, row))) {
          trackablesByLocation.get(p).setLable(label);
        }
      }
    }

    pack();
  }

  public void updateState(State state) {
    trackables.forEach(trackable -> trackable.setIcon(state));
  }

  public void takeScreenshot() {
    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = image.createGraphics();
    paint(graphics2D);
    try {
      ImageIO.write(image,"png", new File(System.getProperty("user.home") + "/Desktop/tracker.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
