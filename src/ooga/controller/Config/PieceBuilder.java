package ooga.controller.Config;

import static java.lang.Integer.parseInt;
import static ooga.controller.Config.BoardBuilder.JSON_DELIMITER;
import static ooga.controller.Config.BoardBuilder.mappings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Piece;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Authors Albert
 * purpose - this class builds piece objects that will be used by the model play the game. This
 *  class takes in a team, pieceType, and location to build the piece. With these 3 bits of information,
 *  we can put the piece in the correct location on the board, and build the piece by reference the
 *  piece's json file
 * assumptions - we assume that that there is a valid piece json file for the piece information given.
 *  if there isn't a json file, we throw an InvalidPieceConfigException
 * dependencies - this class depends on the jsonParser, and the JSONObject package
 * To use - The user will buildPiece() with a team, pieceType, and a location. (ie. w,P,new Location(2,1))
 *  This will return a Piece Object.
 */
public class PieceBuilder {

  public static final String PIECE_JSON_PREFIX = "data/chess/pieces/";
  public static final String PIECE_JSON_SUFFIX = ".json";
  public static final String MOVE_PREFIX = "ooga.model.Moves.";
  public static final String MOVES = "moves";
  public static final String VALUE = "value";
  public static final String TAKES = "takes";
  public static final String LIMITED = "limited";


  /**
   * This return a Piece object. This will be used by the model to play the game.
   * @param team - string of the team (ie. "w" for white)
   * @param pieceName - string of the piece type (ie. "P" for pawn)
   * @param location - location of where the piece is on the board
   * @return - Piece object with all of it's moves, score value, and location
   * @throws FileNotFoundException - if the piece's json file is unable to be found
   * @throws InvalidPieceConfigException - if the piece's json file is not valid (ie. missing key)
   */
  public static Piece buildPiece(String team, String pieceName, Location location)
      throws FileNotFoundException, InvalidPieceConfigException {

    String pieceJsonPath = PIECE_JSON_PREFIX + team + pieceName + PIECE_JSON_SUFFIX;
    JSONObject pieceJSON = JsonParser.loadFile(new File(pieceJsonPath));

    List<Move> moves;
    int value;
    String errorKey = null;
    try {
      errorKey = mappings.getString(MOVES);
      moves = getMoves(pieceJSON.getJSONObject(mappings.getString(MOVES)));
      errorKey = mappings.getString(VALUE);
      value = pieceJSON.getInt(mappings.getString(VALUE));
    } catch (Throwable e) {
      throw new InvalidPieceConfigException(location, pieceJsonPath, errorKey);
    }
    return new Piece(team, pieceName, location, moves, value);

  }

  /**
   * makes the move Objects that are used by the piece to determine legal moves
   */
  private static List<Move> getMoves(JSONObject moveTypes) throws Throwable {
    List<Move> moveList = new ArrayList<>();

    for (String moveType : moveTypes.keySet()) {
      List<Move> newMoves = makeTypeOfMove(moveType, (JSONArray) moveTypes.get(moveType));
      moveList.addAll(newMoves);
    }
    return moveList;
  }

  /**
   * makes multiple of the same type of move with different directions and arguments
   */
  private static List<Move> makeTypeOfMove(String moveType, JSONArray arguments)
      throws Throwable {
    List<Move> moves = new ArrayList<>();
    for (int i = 0; i < arguments.length(); i++) {
      Move newMove = makeMove(moveType,arguments.getString(i));
      moves.add(newMove);
    }
    return moves;
  }

  /**
   * uses reflection to actually create the move object
   */
  private static Move makeMove(String moveType, String arg) throws Throwable {
    String[] args = arg.split(mappings.getString(JSON_DELIMITER));
    if (args.length == BoardBuilder.ARG_LENGTH) {
      int dRow = parseInt(args[0].strip());
      int dCol = parseInt(args[1].strip());
      boolean takes = args[2].strip().equals(mappings.getString(TAKES));
      boolean limited = args[3].strip().equals(mappings.getString(LIMITED));
      Class<?> clazz = Class.forName(MOVE_PREFIX + moveType);
      return (Move) clazz.getDeclaredConstructor(int.class, int.class, boolean.class,
              boolean.class)
          .newInstance(dRow, dCol, takes, limited);
    } else {
      throw new Exception();
    }
  }
}
