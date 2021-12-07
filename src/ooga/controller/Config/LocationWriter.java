package ooga.controller.Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

/**
 * Purpose: LocationWriter handles the task of converting the data from the pieces contained in players to a
 * csv file containing initial states so that the user can play a new game at a later time with
 * pieces at the locations of the last game.
 * Assumptions: The file path passed to save file will be a correct path to the user's local
 * computer
 * Dependencies: PlayerInterface API, and the ability to get all the pieces from this.
 * @author Luis Pereda
 */
public class LocationWriter {
  public static final String TEAM_PIECETYPE_FORMAT = "%s_%s";
  public static final String COMMA = ",";

  private List<String> csvGrid;

  /**
   * saveCSV() saves the locations of all the pieces to a csv file which can later be uploaded to
   * initialize a new game. This uses the PlayerInterface API to get all the pieces from the players.
   * @param filePath String representing the path where the file will be saved.
   * @param pieces API representing the pieces on the board.
   * @throws IOException
   */
  public void saveCSV(String filePath, List<PieceInterface> pieces) throws IOException {
    makeGrid(pieces);
    FileWriter fileWriter = new FileWriter(filePath);
    BufferedWriter writer = new BufferedWriter(fileWriter);
    for (String row : csvGrid) {
        writer.write(row);
        writer.newLine();
    }
    writer.close();
  }

  private void makeGrid(List<PieceInterface> pieces) {
    csvGrid = new ArrayList<>();
    List<String[]> tempGrid = new ArrayList<>();
    setZeroes(tempGrid);
    setPieces(pieces, tempGrid);
    listStringArrToListString(tempGrid);
  }

  private void setZeroes(List<String[]> tempGrid) {
    for (int i = 0; i < 8; i++) {
      int[] gridRowInts = new int[8];
      String[] gridRow = Arrays.stream(gridRowInts)
          .mapToObj(String::valueOf)
          .toArray(String[]::new);
      tempGrid.add(gridRow);
    }
  }

  private void setPieces(List<PieceInterface> pieces, List<String[]> tempGrid) {
    pieces.forEach(piece -> {
      Location location = piece.getLocation();
      int col = location.getCol();
      int row = location.getRow();
      tempGrid.get(row)[col] = String.format(TEAM_PIECETYPE_FORMAT, piece.getTeam(), piece.getName());
    });
  }

  private void listStringArrToListString(List<String[]> tempGrid) {
    tempGrid.forEach(pieceRow -> csvGrid.add(String.join(COMMA, pieceRow)));
  }
}
