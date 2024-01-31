package supermetroid;

public class State {
  public byte beams;
  public byte charge;
  public byte equipmentA;
  public byte equipmentB;
  public byte brinstarBosses;
  public byte norfairBosses;
  public byte wreckedShipBosses;
  public byte maridiaBosses;

  public int maxHealth;
  public int maxReserveHealth;
  public int maxMissiles;
  public int maxSuperMissiles;
  public int maxPowerBombs;

  public byte game;
  public byte events;

  public byte[] objectives = new byte[5];

  public void update(State current, MajorTracker majorTracker, MinorTracker minorTracker, boolean first, boolean zebesOnFire) {
    boolean stateChanged = first;
    stateChanged = stateChanged || beams != current.beams;
    stateChanged = stateChanged || charge != current.charge;
    stateChanged = stateChanged || equipmentA != current.equipmentA;
    stateChanged = stateChanged || equipmentB != current.equipmentB;
    stateChanged = stateChanged || brinstarBosses != current.brinstarBosses;
    stateChanged = stateChanged || norfairBosses != current.norfairBosses;
    stateChanged = stateChanged || wreckedShipBosses != current.wreckedShipBosses;
    stateChanged = stateChanged || maridiaBosses != current.maridiaBosses;
    stateChanged = stateChanged || events != current.events;
    for (var i = 0; i < objectives.length; i++) {
      stateChanged = stateChanged || objectives[i] != current.objectives[i];
    }

    if (maxHealth != current.maxHealth || first) updateMinor(minorTracker, Count.E_TANKS);
    if (maxReserveHealth != current.maxReserveHealth || first) updateMinor(minorTracker, Count.RESERVE_TANKS);
    if (maxMissiles != current.maxMissiles || first) updateMinor(minorTracker, Count.MISSILES);
    if (maxSuperMissiles != current.maxSuperMissiles || first) updateMinor(minorTracker, Count.SUPER_MISSILES);
    if (maxPowerBombs != current.maxPowerBombs || first) updateMinor(minorTracker, Count.POWER_BOMBS);

    if (stateChanged) {
      majorTracker.updateState(this);
    }
  }

  private void updateMinor(MinorTracker minorTracker, Count count) {
    minorTracker.setIconStatus(count,count.getCount(this));
  }
}
