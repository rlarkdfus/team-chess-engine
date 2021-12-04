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

public class LocationWriter {
  public static final String TEAM_PIECETYPE_FORMAT = "%s_%s";
  public static final String COMMA = ",";

  private List<String> csvGrid;

  public void saveCSV(String filePath, List<PlayerInterface> players) throws IOException {
    makeGrid(players);
    FileWriter fileWriter = new FileWriter(filePath);
    BufferedWriter writer = new BufferedWriter(fileWriter);
    for (String row : csvGrid) {
        writer.write(row);
        writer.newLine();
    }
    writer.close();
  }

  private void makeGrid(List<PlayerInterface> players) {
    csvGrid = new ArrayList<>();
    List<String[]> tempGrid = new ArrayList<>();
    setZeroes(tempGrid);
    setPieces(players, tempGrid);
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

  private void setPieces(List<PlayerInterface> players, List<String[]> tempGrid) {
    for(PlayerInterface player: players) {
      List<PieceInterface> pieces = player.getPieces();
      pieces.stream().forEach(piece -> {
        Location location = piece.getLocation();
        int col = location.getCol();
        int row = location.getRow();
        tempGrid.get(row)[col] = String.format(TEAM_PIECETYPE_FORMAT, piece.getTeam(), piece.getName());
      });
    }
  }

  private void listStringArrToListString(List<String[]> tempGrid) {
    tempGrid.stream().forEach(pieceRow -> csvGrid.add(String.join(COMMA, pieceRow)));
  }
}
