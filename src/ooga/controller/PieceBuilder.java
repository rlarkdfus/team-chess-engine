package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Piece;
import org.json.JSONArray;
import org.json.JSONObject;

public class PieceBuilder {
  private ResourceBundle mappings;
  private List<List<String>> csvData;
  private JsonParser jsonParser;
  private String gameType;
  private String bottomColor;
  public PieceBuilder(ResourceBundle mappings, String gameType, String bottomColor){
    this.mappings = mappings;
    jsonParser = new JsonParser();
    this.gameType = gameType;
    this.bottomColor = bottomColor;

  }
  public Piece buildPiece(String[] square, int r, int c) throws FileNotFoundException, InvalidPieceConfigException {

    String team = square[0];
    String pieceName = square[1];
    Location location = new Location(r, c);

    String pieceJsonPath = "data/" + gameType + "/pieces/" + pieceName + ".json";
    JSONObject pieceJSON = jsonParser.loadFile(new File(pieceJsonPath));

    List<Move> moves;
    Map<String, Boolean> attributes;
    int value;
    String errorKey = null;
    try {
      errorKey = mappings.getString("moves");
      moves = getMoves(pieceJSON, team);
      errorKey = mappings.getString("attributes");
      attributes = getAttributes(pieceJSON);
      errorKey = mappings.getString("value");
      value = pieceJSON.getInt(mappings.getString("value"));
    } catch (Throwable e) {
      throw new InvalidPieceConfigException(r, c, pieceJsonPath, errorKey);
    }
    return new Piece(team, pieceName, location, moves, attributes, value);

  }


  private List<Move> getMoves(JSONObject pieceObj, String team) throws Throwable {
    JSONObject moveTypes = pieceObj.getJSONObject(mappings.getString("moves"));
    List<Move> moveList = new ArrayList<>();
    for (String moveType : moveTypes.keySet()) {
      List<Move> newMoves = makeTypeOfMove(moveType, (JSONArray) moveTypes.get(moveType), team);
      moveList.addAll(newMoves);
    }
    return moveList;
  }

  private List<Move> makeTypeOfMove(String moveType, JSONArray arguments, String team)
      throws Throwable {
    List<Move> moves = new ArrayList<>();
    for (int i = 0; i < arguments.length(); i++) {
      Move newMove = makeNewMove(moveType);
      if (newMove == null) {
        continue;
      }
      setMoveArgs(team, newMove, arguments.getString(i));
      moves.add(newMove);
    }
    return moves;
  }

  private Move makeNewMove(String moveType) throws Throwable {
    Class<?> clazz = Class.forName("ooga.model.Moves." + moveType);
    Move newMove = (Move) clazz.getDeclaredConstructor().newInstance();
    return newMove;
  }

  private void setMoveArgs(String team, Move newMove, String arg) throws Exception {
    String[] args = arg.split(mappings.getString("jsonDelimiter"));
    if (args.length == BoardBuilder2.ARG_LENGTH) {
      int dRow = team.equals(bottomColor) ? -parseInt(args[0]) : parseInt(args[0]);
      int dCol = parseInt(args[1].strip());
      boolean takes = args[2].strip().equals(mappings.getString("takes"));
      boolean limited = args[3].strip().equals(mappings.getString("limited"));
      newMove.setMove(dRow, dCol, takes, limited);
    } else {
      throw new Exception();
    }
  }
  /**
   * @param pieceJSON - JSON object of the piece
   * @return a map of all the attributes and their values
   */
  private Map<String, Boolean> getAttributes(JSONObject pieceJSON) {
    JSONObject attributes = pieceJSON.getJSONObject(mappings.getString("attributes"));
    Map<String, Boolean> map = new HashMap<>();
    for (String attribute : attributes.keySet()) {
      map.put(attribute, attributes.getBoolean(attribute));
    }

    return map;
  }

}
