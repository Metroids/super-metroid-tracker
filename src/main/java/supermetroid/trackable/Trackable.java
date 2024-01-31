package supermetroid.trackable;

import supermetroid.State;

import javax.swing.*;

public abstract class Trackable {
  protected JLabel label;

  public void setLable(JLabel label) {
    this.label = label;
    setDefault();
  }

  public abstract void setIcon(State state);
  public abstract void setDefault();
}
