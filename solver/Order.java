package solver;

public class Order{
  public Cell cell;
  private boolean shouldOpen;

  public Order(Cell cell, boolean shouldOpen){
    this.cell = cell;
    this.shouldOpen = shouldOpen;
  }

  public boolean getShouldOpen(){
    return shouldOpen;
  }
}
