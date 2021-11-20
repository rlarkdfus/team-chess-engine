package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Piece;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder2 implements Builder {

  public static final String DEFAULT_STYLE = "companion";

  private String gameType;
  private String boardShape;
  private List<Integer> boardSize;
  private List<String> boardColors;
  private List<String> players;
  private String bottomColor;
  private String style;
  private String rules;
  private String csv;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private LocationParser locationParser;
  private JsonParser jsonParser;

  public BoardBuilder2(File file) {
    jsonParser = new JsonParser();
    locationParser = new LocationParser();
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    style = DEFAULT_STYLE;

    try {
      build(jsonParser.loadFile(file));
    } catch (Exception e) {
      System.out.println("invalid csv");
      e.printStackTrace();
    }
  }


  /**
   * Overridden interface method. builds a pieceinterface which is essentially the grid that holds
   * the pieces of the game.
   *
   * @param jsonObject - parsed .json file
   * @throws Exception - if the csv file in the JSONObject isn't valid
   * @returns - a pieceinterface grid
   */
  @Override
  public void build(JSONObject jsonObject) throws Exception {
    extractJSONObj(jsonObject);
    for (String player : players) {
      playerList.add(new Player(player));
    }
    csvData = locationParser.getInitialLocations(csv);
    iterateCSVData();
  }

  @Override
  public List<PieceViewBuilder> getInitialPieceViews() {
    return pieceList;
  }

  @Override
  public List<PlayerInterface> getInitialPlayers() {
    return playerList;
  }

  /**
   * Iterates through the list<list> as given by the csvParser. creates pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData() throws Exception {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = csvData.get(r).get(c).split("_");
        if (square.length < 2) {           //signifies that this square is empty
          continue;
        }

        String team = square[0];
        String pieceName = square[1];
        Location location = new Location(r, c);

        int playerListIdx = determinePlayer(team);

        String pieceJsonPath = "data/" + gameType + "/pieces/" + pieceName + ".json";
        JSONObject pieceJSON = jsonParser.loadFile(new File(pieceJsonPath));

        JSONObject moveObjects = pieceJSON.getJSONObject("moveObjects");
        List<Move> moves = makeMoveList(moveObjects.getJSONObject("move"), team);
        List<Move> takeMoves = makeMoveList(moveObjects.getJSONObject("take"), team);

        Map<String, Boolean> attributes = getAttributes(pieceJSON);
        int score = pieceJSON.getInt("value");

        Piece piece = new Piece(team, pieceName, location, moves, takeMoves, attributes, score);
        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(playerListIdx).addPiece(piece);
      }
    }
  }

  private List<Move> makeMoveList(JSONObject moveTypes, String team) {
    List<Move> moveList = new ArrayList<>();
    for (String moveType : moveTypes.keySet()) {
      List<Move> newMoves = makeTypeOfMove(moveType, (JSONArray) moveTypes.get(moveType), team);
      moveList.addAll(newMoves);
    }
    return moveList;
  }

  private List<Move> makeTypeOfMove(String moveType, JSONArray arguments, String team) {
    List<Move> moves = new ArrayList<>();
    for (int i = 0; i < arguments.length(); i++) {
      Move newMove = makeNewMove(moveType);
      if (newMove == null){
        continue;
      }
      setArgs(team, newMove, arguments.getString(i));
      moves.add(newMove);
    }
    return moves;
  }

  private Move makeNewMove(String moveType) {
    Move newMove;
    try {
      Class<?> clazz = Class.forName("ooga.model.Moves."+moveType);
      newMove = (Move) clazz.getDeclaredConstructor().newInstance();
      return newMove;
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
    InstantiationException | IllegalAccessException e){
      System.out.println("invalid moveType! found: " + moveType);
    }
    return null;
  }

    private void setArgs (String team, Move newMove, String arg){
      String[] args = arg.split(",");
      if (args.length == 2) {
        int dRow = team.equals(bottomColor) ? -parseInt(args[0]) : parseInt(args[0]);
        int dCol = parseInt(args[1].strip());
        newMove.setMove(dRow, dCol);
      }
    }


    private int determinePlayer (String team) throws Exception {
      int playerListIdx = -1;
      for (PlayerInterface p : playerList) {
        if (p.getTeam().equals(team)) {
          playerListIdx = playerList.indexOf(p);
        }
      }
      if (playerListIdx < 0) {
        //todo: handle exception
        throw new Exception();
      }
      return playerListIdx;
    }

    /**
     * @param pieceJSON - JSON object of the piece
     * @return a map of all the attributes and their values
     */
    private Map<String, Boolean> getAttributes (JSONObject pieceJSON){
      JSONObject attributes = pieceJSON.getJSONObject("attributes");
      Map<String, Boolean> map = new HashMap<>();
      for (String attribute : attributes.keySet()) {
        map.put(attribute, attributes.getBoolean(attribute));
      }

      return map;
    }

    /**
     * sets the instance variables to the values given by the inputted json object
     *
     * @param jsonObject - jsonobject representation of the .json file
     */
    private void extractJSONObj (JSONObject jsonObject){
      gameType = jsonObject.getString("type");
      boardShape = jsonObject.getString("board");
      style = jsonObject.getString("style");
      boardSize = new ArrayList<>();
      List<String> a = Arrays.asList(jsonObject.getString("boardSize").split("x"));
      a.forEach((num) -> boardSize.add(parseInt(num)));
      boardColors = extractColors(jsonObject.getJSONArray("boardColors"));
      players = extractColors(jsonObject.getJSONArray("players"));
      bottomColor = players.get(0); //assumes that bottom player is the first given player color
      rules = jsonObject.getString("rules");
      csv = jsonObject.getString("csv");
    }

    /**
     * @param jsonArray - jsonarray of strings that represent colors of the board/players;
     * @return - a List of strings version of the jsonarray
     */
    private List<String> extractColors (JSONArray jsonArray){
      List<String> ret = new ArrayList<>();
      for (int i = 0; i < jsonArray.length(); i++) {
        ret.add(jsonArray.getString(i));
      }
      return ret;
    }

//  public class PieceViewBuilder {
//
//    private String team;
//    private String name;
//    private Location location;
//
//    public PieceViewBuilder(Piece piece) {
//      this.team = piece.getTeam();
//      this.name = piece.getName();
//      this.location = piece.getLocation();
//    }
//
//    public String getTeam() {
//      return team;
//    }
//
//    public String getName() {
//      return name;
//    }
//
//    public Location getLocation() {
//      return location;
//    }
//  }
  }
