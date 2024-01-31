package supermetroid;

import org.json.JSONObject;
import supermetroid.trackable.BooleanTrackable;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectiveReader {
  public int total;
  public int required;
  List<String> objectives = new ArrayList<>();
  Map<String, String> objectiveToImage = new HashMap<>();

  // Located in patches/common/sym/objectives.json
  private static final int NUMBER_OF_OBJECTIVES_FILE_POINTER = 8769536;           // "n_objectives"
  private static final int NUMBER_OF_REQUIRED_OBJECTIVES_FILE_POINTER = 8769538;  // "n_objectives_required"
  private static final int OBJECTIVE_FUNCTIONS_FILE_POINTER = 8769540;            // "objective_funcs"

  public ObjectiveReader() throws Exception {
    var b = new byte[2];

    RandomAccessFile reader = new RandomAccessFile(new File(locateRom()), "r");
    reader.seek(variaAddressToFileAddress(NUMBER_OF_OBJECTIVES_FILE_POINTER));
    reader.read(b);
    total = ((b[1] & 255) << 8) + (b[0] & 255);

    reader.seek(variaAddressToFileAddress(NUMBER_OF_REQUIRED_OBJECTIVES_FILE_POINTER));
    reader.read(b);
    required = ((b[1] & 255) << 8) + (b[0] & 255);

    Map<Integer, String> functionToObjective = new HashMap<>();
    InputStream s = Main.class.getResourceAsStream("/data/objectives.json");
    JSONObject jo = new JSONObject(new String(s.readAllBytes()));
    for (var key: jo.keySet()) {
      functionToObjective.put(jo.getInt(key) & 0xFFFF, key);
    }
    s.close();

    s = Main.class.getResourceAsStream("/data/objective-to-image.json");
    jo = new JSONObject(new String(s.readAllBytes()));
    for (var key: jo.keySet()) {
      objectiveToImage.put(key, jo.getString(key));
    }
    s.close();

    for (int i = 0; i < total; i++) {
      int address = OBJECTIVE_FUNCTIONS_FILE_POINTER + (i*2);
      reader.seek(variaAddressToFileAddress(address));
      reader.read(b);

      int function = ((b[1] & 255) << 8) + (b[0] & 255);
      objectives.add(functionToObjective.get(function));
    }
  }

  public BooleanTrackable createObjectiveTrackable(int objective) {
    String image = "objective/" + objectiveToImage.get(objectives.get(objective));

    return switch (objective) {
      case 0 -> new BooleanTrackable(image, state -> (state.objectives[0] & 4) > 0);
      case 1 -> new BooleanTrackable(image, state -> (state.objectives[0] & 16) > 0);
      case 2 -> new BooleanTrackable(image, state -> (state.objectives[0] & 64) > 0);
      case 3 -> new BooleanTrackable(image, state -> (state.objectives[1] & 1) > 0);
      case 4 -> new BooleanTrackable(image, state -> (state.objectives[1] & 4) > 0);
      case 5 -> new BooleanTrackable(image, state -> (state.objectives[1] & 16) > 0);
      case 6 -> new BooleanTrackable(image, state -> (state.objectives[1] & 64) > 0);
      case 7 -> new BooleanTrackable(image, state -> (state.objectives[2] & 1) > 0);
      case 8 -> new BooleanTrackable(image, state -> (state.objectives[2] & 4) > 0);
      case 9 -> new BooleanTrackable(image, state -> (state.objectives[2] & 16) > 0);
      case 10 -> new BooleanTrackable(image, state -> (state.objectives[2] & 64) > 0);
      case 11 -> new BooleanTrackable(image, state -> (state.objectives[3] & 1) > 0);
      case 12 -> new BooleanTrackable(image, state -> (state.objectives[3] & 4) > 0);
      case 13 -> new BooleanTrackable(image, state -> (state.objectives[3] & 16) > 0);
      case 14 -> new BooleanTrackable(image, state -> (state.objectives[3] & 64) > 0);
      case 15 -> new BooleanTrackable(image, state -> (state.objectives[4] & 1) > 0);
      case 16 -> new BooleanTrackable(image, state -> (state.objectives[4] & 4) > 0);
      case 17 -> new BooleanTrackable(image, state -> (state.objectives[4] & 16) > 0);
      default -> throw new RuntimeException("The max objective index is 17, given " + objective + ".");
    };

  }

  private String locateRom() throws Exception {
    String desktop = System.getProperty("user.home") + "\\Desktop";
    return Files.walk(Paths.get(desktop), 1)
      .filter(f -> f.getFileName().toString().endsWith(".sfc"))
      .findFirst()
      .get()
      .toString();
  }

  private static int variaAddressToFileAddress(int address) {
    var b1 = address >> 16;
    var a1 = (b1 - 0x80) >> 1;

    var b2 = address & 0xFFFF;
    var a2 = (b1 & 1) == 0 ? b2 & 0x7FFF : b2;

    return (a1 << 16) | a2;
  }
}
