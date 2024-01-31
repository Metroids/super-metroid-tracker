package supermetroid.trackable;

import supermetroid.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BeamTrackable extends Trackable {
  private String current;
  private Map<String, ImageIcon> beams = new HashMap<>();

  public BeamTrackable() throws Exception {
    for(var c = 0; c < 2; c++) {
      for (var w = 0; w < 2; w++) {
        for (var i = 0; i < 2; i++) {
          for (var s = 0; s < 2; s++) {
            for (var p = 0; p < 2; p++) {
              String key = (c == 1 ? "a" : "i") + (w == 1 ? "a" : "i") +(i == 1 ? "a" : "i") + (s == 1 ? "a" : "i") + (p == 1 ? "a" : "i");
              String image = "/beams/cwisp-" + key + ".png";
              ImageIcon icon = new ImageIcon(ImageIO.read(Objects.requireNonNull(BooleanTrackable.class.getResourceAsStream(image))));
              beams.put(key, icon);
            }
          }
        }
      }
    }
  }

  @Override
  public void setIcon(State state) {
    boolean c = (state.charge & 0x10) > 0;
    boolean w = (state.beams & 0x1) > 0;
    boolean i = (state.beams & 0x2) > 0;
    boolean s = (state.beams & 0x4) > 0;
    boolean p = (state.beams & 0x8) > 0;
    String key = (c ? "a" : "i") + (w ? "a" : "i") +(i ? "a" : "i") + (s ? "a" : "i") + (p ? "a" : "i");

    if (!key.equals(current)) {
      current = key;
      label.setIcon(beams.get(key));
    }
  }

  @Override
  public void setDefault() {
    label.setIcon(beams.get("iiiii"));
  }
}
