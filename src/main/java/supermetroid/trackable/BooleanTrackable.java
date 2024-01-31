package supermetroid.trackable;

import supermetroid.MajorTracker;
import supermetroid.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Objects;
import java.util.function.Function;

public class BooleanTrackable extends Trackable {
  private ImageIcon on;
  private ImageIcon off;
  private Function<State, Boolean> booleanMemoryReader;
  private Boolean cache;

  public BooleanTrackable(String image, Function<State, Boolean> booleanMemoryReader) {
    try {
      this.on = new ImageIcon(ImageIO.read(BooleanTrackable.class.getResourceAsStream("/on/" + image + ".png")));
      this.off = new ImageIcon(ImageIO.read(BooleanTrackable.class.getResourceAsStream("/off/" + image + ".png")));
    } catch (Exception e) {
      System.out.println("Error loading boolean trackable image for " + image);
      e.printStackTrace();
    }

    this.booleanMemoryReader = booleanMemoryReader;
  }

  @Override
  public void setIcon(State state) {
    if (cache == null || cache != booleanMemoryReader.apply(state)) {
      cache = booleanMemoryReader.apply(state);
      label.setIcon(cache ? on : off);
    }
  }

  @Override
  public void setDefault() {
    label.setIcon(off);
  }

  public static final BooleanTrackable CHARGE = new BooleanTrackable("charge", state -> (state.charge & 0x10) > 0);
  public static final BooleanTrackable ICE = new BooleanTrackable("ice", state -> (state.beams & 0x2) > 0);
  public static final BooleanTrackable WAVE = new BooleanTrackable("wave", state -> (state.beams & 0x1) > 0);
  public static final BooleanTrackable SPAZER = new BooleanTrackable("spazer", state -> (state.beams & 0x4) > 0);
  public static final BooleanTrackable PLASMA = new BooleanTrackable("plasma", state -> (state.beams & 0x8) > 0);

  public static final BooleanTrackable MORPH = new BooleanTrackable("morph", state -> (state.equipmentA & 0x4) > 0);
  public static final BooleanTrackable VARIA = new BooleanTrackable("varia", state -> (state.equipmentA & 0x1) > 0);
  public static final BooleanTrackable SPRING_BALL = new BooleanTrackable("spring-ball", state -> (state.equipmentA & 0x2) > 0);
  public static final BooleanTrackable HI_JUMP = new BooleanTrackable("hi-jump", state -> (state.equipmentB & 0x1) > 0);
  public static final BooleanTrackable SPACE_JUMP = new BooleanTrackable("space-jump", state -> (state.equipmentB & 0x2) > 0);
  public static final BooleanTrackable BOMBS = new BooleanTrackable("bombs", state -> (state.equipmentB & 0x10) > 0);
  public static final BooleanTrackable GRAVITY = new BooleanTrackable("gravity", state -> (state.equipmentA & 0x20) > 0);
  public static final BooleanTrackable SPEED_BOOSTER = new BooleanTrackable("speed-booster", state -> (state.equipmentB & 0x20) > 0);
  public static final BooleanTrackable SCREW_ATTACK = new BooleanTrackable("screw-attack", state -> (state.equipmentA & 0x8) > 0);

  public static final BooleanTrackable PHANTOON = new BooleanTrackable("phantoon", state -> (state.wreckedShipBosses & 0x1) > 0);
  public static final BooleanTrackable RIDLEY = new BooleanTrackable("ridley", state -> (state.norfairBosses & 0x1) > 0);
  public static final BooleanTrackable KRAID = new BooleanTrackable("kraid", state -> (state.brinstarBosses & 0x1) > 0);
  public static final BooleanTrackable DRAYGON = new BooleanTrackable("draygon", state -> (state.maridiaBosses & 0x1) > 0);

  public static final BooleanTrackable CROCOMIRE = new BooleanTrackable("crocomire", state -> (state.norfairBosses & 0x2) > 0);
  public static final BooleanTrackable SHAKTOOL = new BooleanTrackable("shaktool", state -> (state.events & 0x20) > 0);
}
