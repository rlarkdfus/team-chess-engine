package ooga.controller.Config;

import static java.lang.Integer.parseInt;
import static ooga.controller.Config.BoardBuilder.mappings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Piece;
import org.json.JSONArray;
import org.json.JSONObject;

public class PieceBuilder {

  public static Piece buildPiece(String team, String pieceName, Location location)
      throws FileNotFoundException, InvalidPieceConfigException {

    String pieceJsonPath = "data/chess/pieces/" + team + pieceName + ".json";
    JSONObject pieceJSON = JsonParser.loadFile(new File(pieceJsonPath));

    List<Move> moves;
    Map<String, Boolean> attributes;
    int value;
    String errorKey = null;
    try {
      errorKey = mappings.getString("moves");
      moves = getMoves(pieceJSON);
      errorKey = mappings.getString("attributes");
      attributes = getAttributes(pieceJSON);
      errorKey = mappings.getString("value");
      value = pieceJSON.getInt(mappings.getString("value"));
    } catch (Throwable e) {
      throw new InvalidPieceConfigException(location, pieceJsonPath, errorKey);
    }
    return new Piece(team, pieceName, location, moves, attributes, value);

  }


  private static List<Move> getMoves(JSONObject pieceObj) throws Throwable {
    JSONObject moveTypes = pieceObj.getJSONObject(mappings.getString("moves"));
    List<Move> moveList = new ArrayList<>();
    for (String moveType : moveTypes.keySet()) {
      List<Move> newMoves = makeTypeOfMove(moveType, (JSONArray) moveTypes.get(moveType));
      moveList.addAll(newMoves);
    }
    return moveList;
  }

  private static List<Move> makeTypeOfMove(String moveType, JSONArray arguments)
      throws Throwable {
    List<Move> moves = new ArrayList<>();
    for (int i = 0; i < arguments.length(); i++) {
      Move newMove = makeMove(moveType,arguments.getString(i));
      if (newMove == null) {
        continue;
      }
      moves.add(newMove);
    }
    return moves;
  }

  private static Move makeMove(String moveType, String arg) throws Throwable {
    String[] args = arg.split(mappings.getString("jsonDelimiter"));
    if (args.length == BoardBuilder.ARG_LENGTH) {
      int dRow = parseInt(args[0].strip());
      int dCol = parseInt(args[1].strip());
      boolean takes = args[2].strip().equals(mappings.getString("takes"));
      boolean limited = args[3].strip().equals(mappings.getString("limited"));
      Class<?> clazz = Class.forName("ooga.model.Moves." + moveType);
      Move newMove = (Move) clazz.getDeclaredConstructor(int.class, int.class, boolean.class, boolean.class)
          .newInstance(dRow,dCol,takes,limited);
      return newMove;
    } else {
      throw new Exception();
    }
  }

//  private static void setMoveArgs(Move newMove, String arg) throws Exception {
//    String[] args = arg.split(mappings.getString("jsonDelimiter"));
//    if (args.length == BoardBuilder.ARG_LENGTH) {
//      int dRow = parseInt(args[0].strip());
//      int dCol = parseInt(args[1].strip());
//      boolean takes = args[2].strip().equals(mappings.getString("takes"));
//      boolean limited = args[3].strip().equals(mappings.getString("limited"));
//      newMove.setMove(dRow, dCol, takes, limited);
//    } else {
//      throw new Exception();
//    }
//  }

  /**
   * @param pieceJSON - JSON object of the piece
   * @return a map of all the attributes and their values
   */
  private static Map<String, Boolean> getAttributes(JSONObject pieceJSON) {
    JSONObject attributes = pieceJSON.getJSONObject(mappings.getString("attributes"));
    Map<String, Boolean> map = new HashMap<>();
    for (String attribute : attributes.keySet()) {
      map.put(attribute, attributes.getBoolean(attribute));
    }

    return map;
  }

}
