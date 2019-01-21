package solver;

import java.util.ArrayList;

public class Solver{
  int height;
  int width;
  int remainBomb;
  int[][] fieldStatus;
  boolean isTouched;

  ArrayList<Information> informations = new ArrayList<>();

  public Solver(int height, int width, int remainBomb, int[][] fieldStatus, boolean isTouched){
    this.height = height;
    this.width = width;
    this.remainBomb = remainBomb;
    this.fieldStatus = fieldStatus;
    this.isTouched = isTouched;
  }

  public Order execute(){

    if(!isTouched){
      return new Order(new Cell(width/2, height/2), true);
    }

    for(int i = 1; i <= height; i++){
      for(int j = 1; j <= width; j++){
        if(fieldStatus[i][j] >= 1){
          try{
            Information info = new Information(i, j, fieldStatus);
            if(info.surroundingNotOpenedCells.size() > 0){
              informations.add(info);
            }
          }catch(IllegalAccessException e){
            System.err.println(e.getMessage());
          }
        }
      }
    }

    for(Information info:informations){
      if(info.numberOfBomb == 0 && info.surroundingNotOpenedCells.size() > 0){
        return new Order(info.surroundingNotOpenedCells.iterator().next(), true);
      }
      else if(info.numberOfBomb == info.surroundingNotOpenedCells.size()){
        return new Order(info.surroundingNotOpenedCells.iterator().next(), false);
      }
    }

    System.out.println("ランダム選択");
    //ランダムに開ける
    int x = (int) (Math.random() * width) + 1;
    int y = (int) (Math.random() * height) + 1;
    return new Order(new Cell(x, y), true);
  }
}