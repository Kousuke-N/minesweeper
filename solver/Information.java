package solver;

import java.util.HashSet;

import components.game.GameCell;
import solver.Cell;

public class Information{
  int y;
  int x;
  int numberOfBomb;
  HashSet<Cell> surroundingNotOpenedCells = new HashSet<Cell>();

  Information(int y, int x, int[][] fieldStatus) throws IllegalAccessException{

    if(GameCell.FIELD_DATA_EMPTY > fieldStatus[y][x] || fieldStatus[y][x] > GameCell.FIELD_DATA_MAX_NUMBER){
      throw new IllegalAccessException("0-8でない引数が渡されました: " + fieldStatus[y][x]);
    }
    
    this.x = x;
    this.y = y;
    
    numberOfBomb = fieldStatus[y][x];
    for(int i = y - 1; i <= y + 1; i++){
      for(int j = x - 1; j <= x + 1; j++){
        if(fieldStatus[i][j] == GameCell.FIELD_DATA_FLAG){
          numberOfBomb--;
        }
        else if(fieldStatus[i][j] == GameCell.FIELD_DATA_HIDDEN){
          surroundingNotOpenedCells.add(new Cell(j, i));
        }
      }
    }
  }
}