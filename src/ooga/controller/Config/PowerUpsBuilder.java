package ooga.controller.Config;

import static java.lang.Integer.parseInt;
import static ooga.controller.Config.BoardBuilder.mappings;
import static ooga.controller.Config.EndConditionBuilder.convertJSONArrayOfStrings;
import static ooga.controller.Config.LocationWriter.COMMA;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.LogUtils;
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
  public static final int ZERO_BOUNDS = 0;

  /**
   *This return the List of powerup objects. This will be used by the model to determine when and what
   *   powerup should be activated.
   * @param powerupsFilepath - a path to a powerups json file
   * @return - a list of powerups
   * @throws InvalidPowerupsConfigException
   */
  public static List<PowerupInterface> getPowerups(String powerupsFilepath, Location bounds) throws InvalidPowerupsConfigException {

      try {
        return buildPowerupsRunner(powerupsFilepath, bounds);
      } catch (Exception e) {
        LogUtils.error("powerupsbuilder",e.getClass());
        throw new InvalidPowerupsConfigException(e.getMessage());
      }
  }

  /**
   * iterates through the powerup types and callsl buildPowerup
   */
  private static List<PowerupInterface> buildPowerupsRunner(String powerupsFilepath,Location bounds)
      throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidPowerupsConfigException {
    List<PowerupInterface> powerupsList = new ArrayList<>();

    JSONObject PowerupsJSONObject = JsonParser.loadFile(new File(powerupsFilepath));

    for (String powerupType : PowerupsJSONObject.keySet()) {
      List<Location> powerupLocations = getPowerupLocations(PowerupsJSONObject.getJSONArray(powerupType), bounds);
      powerupsList.add(buildPowerUp(powerupType,powerupLocations, bounds));
    }
    return powerupsList;
  }

  /**
   * converts the jsonarray of string locations to a list of locations. Also checks that the locations
   * are inbounds
   */
  private static List<Location> getPowerupLocations(JSONArray jsonArray,Location bounds)
      throws InvalidPowerupsConfigException {
    List<Location> locations = new ArrayList<>();
    List<String> stringLocations = convertJSONArrayOfStrings(jsonArray);
    for (String s : stringLocations){
      String[] location = s.split(COMMA);
      int row = parseInt(location[0].strip());
      int col = parseInt(location[1].strip());
      if (outOfBounds(row,col,bounds)){
        throw new InvalidPowerupsConfigException(mappings.getString(OUT_OF_BOUNDS_ERROR));
      }
      locations.add(new Location(row,col));
    }
    return locations;
  }

  /**
   * helper method for checking that a location is in side the 8x8 bounds
   */
  private static boolean outOfBounds(int row, int col,Location bounds) {
    if (row >= bounds.getRow() || row < ZERO_BOUNDS || col >= bounds.getCol() || col < ZERO_BOUNDS){
      return true;
    }
    return false;
  }

  /**
   * builds the powerup objects using reflection
   */
  private static PowerupInterface buildPowerUp(String powerupType, List<Location> powerupLocations, Location bounds)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    PowerupInterface powerup;

    Class<?> clazz = Class.forName(POWERUPS_PREFIX + powerupType + POWERUPS_SUFFIX);
    powerup = (PowerupInterface) clazz.getDeclaredConstructor(List.class, Location.class)
        .newInstance(powerupLocations, bounds);

    return powerup;
  }
}
