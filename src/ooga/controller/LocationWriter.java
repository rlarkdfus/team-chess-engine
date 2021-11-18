package ooga.controller;

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
  private List<String> csvGrid;

  public void saveCSV(String filePath, List<PlayerInterface> players) throws Exception {
    makeGrid(players);
    try {
      FileWriter fileWriter = new FileWriter(filePath);
      BufferedWriter writer = new BufferedWriter(fileWriter);
      csvGrid.forEach(row -> {
        try {
          writer.write(row);
          writer.newLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void makeGrid(List<PlayerInterface> players) {
    csvGrid = new ArrayList<>();
    ArrayList<String[]> tempGrid = new ArrayList<>();
    setZeroes(tempGrid);
    setPieces(players, tempGrid);
    listStringArrToListString(tempGrid);
  }

  private void setZeroes(ArrayList<String[]> tempGrid) {
    for (int i = 0; i < 8; i++) {
      int[] gridRowInts = new int[8];
      String[] gridRow = Arrays.stream(gridRowInts)
          .mapToObj(String::valueOf)
          .toArray(String[]::new);
      tempGrid.add(gridRow);
    }
  }

  private void setPieces(List<PlayerInterface> players, ArrayList<String[]> tempGrid) {
    for(PlayerInterface player: players) {
      List<PieceInterface> pieces = player.getPieces();
      pieces.forEach(piece -> {
        Location location = piece.getLocation();
        int col = location.getCol();
        int row = location.getRow();
        tempGrid.get(row)[col] = String.format("%s_%s", piece.getTeam(), piece.getName());
      });
    }
  }

  private void listStringArrToListString(ArrayList<String[]> tempGrid) {
    for(String[] pieceRow : tempGrid) {
      String csvRow = String.join(",", pieceRow);
      csvGrid.add(csvRow);
    }
  }
}
