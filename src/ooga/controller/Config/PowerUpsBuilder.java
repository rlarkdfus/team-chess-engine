package ooga.controller.Config;

import static java.lang.Integer.parseInt;
import static ooga.controller.Config.BoardBuilder.mappings;
import static ooga.controller.Config.EndConditionBuilder.convertJSONArrayOfStrings;
import static ooga.controller.Config.LocationWriter.COMMA;
import static ooga.model.Moves.MoveUtility.BOARD_MAX;
import static ooga.model.Moves.MoveUtility.BOARD_MIN;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.Powerups.PowerupInterface;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Authors Albert
 * purpose - this class builds Powerup objects that will be used by the model to populate the game
 *  with powerups. The powerups are stored in
 * assumptions - we assume that the filepath points to a valid json file with valid formatting. If
 *  this file path isn't correct, we throw an InvalidPowerupsConfigException.
 * dependencies - this class depends on a method in EndConditionBuilder
 * To use - The user will getPowerups() with the filepath to a valid powerups .json file. This will
 *  return a list of Powerups that will be sent to board.
 */
public class PowerUpsBuilder {

  public static final String POWERUPS_PREFIX = "ooga.model.Powerups.";
  public static final String POWERUPS_SUFFIX = "Powerup";
  public static final String OUT_OF_BOUNDS_ERROR = "outOfBoundsError";

  /**
   *This return the List of powerup objects. This will be used by the model to determine when and what
   *   powerup should be activated.
   * @param powerupsFilepath - a path to a powerups json file
   * @return - a list of powerups
   * @throws InvalidPowerupsConfigException
   */
  public static List<PowerupInterface> getPowerups(String powerupsFilepath) throws InvalidPowerupsConfigException {

      try {
        return buildPowerupsRunner(powerupsFilepath);
      } catch (Exception e) {
        throw new InvalidPowerupsConfigException(e.getMessage());
      }
  }

  /**
   * iterates through the powerup types and callsl buildPowerup
   */
  private static List<PowerupInterface> buildPowerupsRunner(String powerupsFilepath)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidPowerupsConfigException {
    List<PowerupInterface> powerupsList = new ArrayList<>();

    JSONObject PowerupsJSONObject = JsonParser.loadFile(new File(powerupsFilepath));

    for (String powerupType : PowerupsJSONObject.keySet()) {
      List<Location> powerupLocations = getPowerupLocations(PowerupsJSONObject.getJSONArray(powerupType));
      powerupsList.add(buildPowerUp(powerupType,powerupLocations));
    }
    return powerupsList;
  }

  /**
   * converts the jsonarray of string locations to a list of locations. Also checks that the locations
   * are inbounds
   */
  private static List<Location> getPowerupLocations(JSONArray jsonArray)
      throws InvalidPowerupsConfigException {
    List<Location> locations = new ArrayList<>();
    List<String> stringLocations = convertJSONArrayOfStrings(jsonArray);
    for (String s : stringLocations){
      String[] location = s.split(COMMA);
      int row = parseInt(location[0].strip());
      int col = parseInt(location[1].strip());
      if (outOfBounds(row,col)){
        throw new InvalidPowerupsConfigException(mappings.getString(OUT_OF_BOUNDS_ERROR));
      }
      locations.add(new Location(row,col));
    }
    return locations;
  }

  /**
   * helper method for checking that a location is in side the 8x8 bounds
   */
  private static boolean outOfBounds(int row, int col) {
    if (row >= BOARD_MAX || row < BOARD_MIN || col >= BOARD_MAX || col < BOARD_MIN){
      return true;
    }
    return false;
  }

  /**
   * builds the powerup objects using reflection
   */
  private static PowerupInterface buildPowerUp(String powerupType, List<Location> powerupLocations)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    PowerupInterface powerup;

    Class<?> clazz = Class.forName(POWERUPS_PREFIX + powerupType + POWERUPS_SUFFIX);
    powerup = (PowerupInterface) clazz.getDeclaredConstructor(List.class)
        .newInstance(powerupLocations);

    return powerup;
  }
}
