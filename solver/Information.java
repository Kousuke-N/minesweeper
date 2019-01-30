package solver;

import java.util.HashSet;

import components.game.GameCell;
import solver.Cell;

public class Information{
  public int y;
  public int x;
  public int bombs;
  public HashSet<Cell> adjacentHiddenCells = new HashSet<Cell>();

  public Information(int bombs, HashSet<Cell> adjacentHiddenCells){
    this.bombs = bombs;
    this.adjacentHiddenCells = adjacentHiddenCells;
  }

  public boolean intersectableWith(Information another){
    return minBombs(another) == maxBombs(another);
  }

  public Information intersection(Information another){
    if(!intersectableWith(another)){
      throw new IllegalArgumentException("Intersectionを作成できません");
    }
    return new Information(minBombs(another), intersection(another.adjacentHiddenCells));
  }

  private HashSet<Cell> intersection(HashSet<Cell> another){
    HashSet<Cell> result = new HashSet<>();
    result.addAll(this.adjacentHiddenCells);
    result.retainAll(another);
    return result;
  }

  private int minBombs(Information another){
    HashSet<Cell> intersection = this.intersection(another.adjacentHiddenCells);
    return Math.max(0, Math.max(-this.adjacentHiddenCells.size() + intersection.size() + this.bombs,
                                -another.adjacentHiddenCells.size() + intersection.size() + another.bombs));
  }

  private int maxBombs(Information another){
    HashSet<Cell> intersection = this.intersection(another.adjacentHiddenCells);
    return Math.min(intersection.size(), Math.min(this.bombs, another.bombs));
  }

  public Information difference(Information another){

    Information intersection = intersection(another);
    int bombs = this.bombs - intersection.bombs;

    HashSet<Cell> adjacentHiddenCells = new HashSet<Cell>();
    adjacentHiddenCells.addAll(this.adjacentHiddenCells);
    adjacentHiddenCells.removeAll(intersection.adjacentHiddenCells);
    
    return new Information(bombs, adjacentHiddenCells);
  }

  public boolean allSafe(){
    return bombs == 0;
  }

  public boolean allBomb(){
    return bombs == adjacentHiddenCells.size();
  }
}
