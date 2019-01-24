package solver;

import java.util.ArrayList;
import java.util.HashSet;

import components.game.GameCell;

public class Solver{
  int height;
  int width;
  int remainBomb;
  int[][] fieldStatus;
  boolean isTouched;
  Cell[][] cells;

  ArrayList<Information> informations = new ArrayList<>();

  public Solver(int height, int width, int remainBomb, int[][] fieldStatus, boolean isTouched){
    this.height = height;
    this.width = width;
    this.remainBomb = remainBomb;
    this.fieldStatus = fieldStatus;
    this.isTouched = isTouched;
    
    cells = new Cell[height + 2][width + 2];
    for(int i = 1; i <= height; i++){
      for(int j = 1; j <= width; j++){
        cells[i][j] = new Cell(i, j);
      }
    }
  }

  public Order execute(){

    if(!isTouched){
      return new Order(cells[height/2][width/2], true);
    }

    // informationの作成

    for(int y = 1; y <= height; y++){
      for(int x = 1; x <= width; x++){
        if(fieldStatus[y][x] >= 1){
          if(GameCell.FIELD_DATA_EMPTY <= fieldStatus[y][x] && fieldStatus[y][x] <= GameCell.FIELD_DATA_MAX_NUMBER){

            int bombs = fieldStatus[y][x];
            HashSet<Cell> adjacentHiddenCells = new HashSet<Cell>();
            
            for(int i = y - 1; i <= y + 1; i++){
              for(int j = x - 1; j <= x + 1; j++){
                if(fieldStatus[i][j] == GameCell.FIELD_DATA_FLAG){
                  bombs--;
                }
                else if(fieldStatus[i][j] == GameCell.FIELD_DATA_HIDDEN){
                  adjacentHiddenCells.add(cells[i][j]);
                }
              }
            }
            
            if(adjacentHiddenCells.size() > 0){
              informations.add(new Information(bombs, adjacentHiddenCells));
            }

          }
        }
      }
    }

    //1つの数字で決定
    System.out.println("1つの数字で決定");

    for(Information info:informations){
      if(info.allSafe()){
        return new Order(info.adjacentHiddenCells.iterator().next(), true);
      }
      else if(info.allBomb()){
        return new Order(info.adjacentHiddenCells.iterator().next(), false);
      }
    }

    //2つの数字で決定
    System.out.println("2つの数字で決定");

    for(Information info1: informations){
      for(Information info2: informations){
        if(info1 == info2){
          continue;
        }
        if(info1.intersectableWith(info2)){
          Information[] newInfo = { info1.difference(info2), info2.difference(info1), info1.intersection(info2) };
          
          for (Information info : newInfo) {
            //↓とりあえず仮 もっとまともな例外処理を考える
            if(info.adjacentHiddenCells.size() > 0){
              if(info.allSafe()){
                return new Order(info.adjacentHiddenCells.iterator().next(), true);
              }
              else if(info.allBomb()){
                return new Order(info.adjacentHiddenCells.iterator().next(), false);
              }
            }
          }
        }
      }
    }

    System.out.println("ランダム選択");

    //ランダムに開ける
    int x = (int) (Math.random() * width) + 1;
    int y = (int) (Math.random() * height) + 1;
    return new Order(cells[y][x], true);
  }
}