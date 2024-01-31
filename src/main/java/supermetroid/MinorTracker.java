package supermetroid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MinorTracker extends JFrame {
  ImageIcon[] on = new ImageIcon[5];
  ImageIcon[] off = new ImageIcon[5];
  JLabel[] images = new JLabel[5];
  JLabel[] counts = new JLabel[5];
  JLayeredPane panel;

  public MinorTracker() throws Exception {
    setTitle("Super Metroid - Minor Tracker");
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize((64 * 5) + (2*4), 64);
    panel = new JLayeredPane();
    panel.setPreferredSize(new Dimension((64 * 5) + (2*4), 64));
    panel.setBackground(Color.BLACK);
    panel.setOpaque(true);
    setContentPane(panel);

    for (var col = 0; col < 5; col++) {
      on[col] = new ImageIcon(ImageIO.read(MinorTracker.class.getResourceAsStream("/on/4-" + col + ".png")));
      off[col] = new ImageIcon(ImageIO.read(MinorTracker.class.getResourceAsStream("/off/4-" + col + ".png")));

      panel.add(images[col] = new JLabel(), 0);
      images[col].setSize(64,64);
      images[col].setLocation((64 * col) + (2 * col), 0);
      images[col].setIcon(off[col]);

      panel.add(counts[col] = new JLabel(), 1);
      counts[col].setOpaque(true);
      counts[col].setBackground(Color.black);
      counts[col].setLocation(7 + (64 * col) + (2 * col), 42);
      counts[col].setForeground(Color.WHITE);
      counts[col].setFont(new Font("Calibri",Font.BOLD,16));
    }

    pack();
  }

  public void setIconStatus(Count count, int value) {
    if (value > 0) {
      images[count.col].setIcon(this.on[count.col]);
      counts[count.col].setText(" " + value);
      counts[count.col].setSize((value < 10 ? 16 : (value < 100 ? 24 : 30)), 30);
      panel.moveToFront(counts[count.col]);
    } else {
      images[count.col].setIcon(this.off[count.col]);
      counts[count.col].setText("");
      counts[count.col].setSize(0, 30);
    }
  }
}
