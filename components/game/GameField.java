package components.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import components.layout.GamePanel;

/**
 * GameField
 */
public class GameField extends JPanel {

  private GameCell[][] field;
  private int[][] cacheField;
  private int width;
  private int height;
  private int bombnumber;
  private boolean isTouched = false;
  private boolean withPlayer;

  public GameField(GamePanel gamePanel, int panelWidth, int panelHeight, int width, int height, int bombnumber,
      boolean withPlayer) {
    this.width = width;
    this.height = height;
    this.bombnumber = bombnumber;
    this.withPlayer = withPlayer;

    field = new GameCell[height + 2][width + 2];
    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        field[y][x] = new GameCell();
        field[y][x].setActionCommand(y + ":" + x);
        if (y == 0 || y == height + 1 || x == 0 || x == width + 1) {
          continue;
        }
        add(field[y][x]);
        if (withPlayer) {
          field[y][x].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              String command = e.getActionCommand();
              String[] splitedCommand = command.split(":", 0);
              int x = Integer.parseInt(splitedCommand[1]);
              int y = Integer.parseInt(splitedCommand[0]);

              if (!isTouched) {
                isTouched = true;
                construct(x, y);
                calculate();
              }

              if (!open(x, y)) {
                gamePanel.gameover();
              }
            }
          });
        }
        else{
          field[y][x].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              //とりあえず適当なセルを選択。ソルバが完成すると以下のようなコードとなるだろう。
              // Solver solver = new Solver(statusOfField);
              // result = solver.execute();
              // int y = result.y, x = result.x;

              int y = isTouched ? (int) (Math.random() * height) + 1 : height / 2;
              int x = isTouched ? (int) (Math.random() * width) + 1 : width / 2;
      
              //System.out.println(x + " " + y);
              
              // 以下重複・リファクタリングを検討
              if (!isTouched) {
                isTouched = true;
                construct(x, y);
                calculate();
              }
      
              if (!open(x, y)) {
                gamePanel.gameover();
              }

              // try{
              //   Thread.sleep(1000);
              // }catch(InterruptedException exception){}
            }
          });
        }
      }

    }
    cacheField = new int[height + 2][width + 2];
    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        cacheField[y][x] = GameCell.FIELD_DATA_HIDDEN;
      }
    }
    setPreferredSize(new Dimension(700, 600));
    setLayout(new GridLayout(width, height));
    setOpaque(false);
  }

  public boolean open(int x, int y) {
    if (!isTouched) {
      isTouched = true;
      construct(x, y);
    }
    if (field[y][x].isHereBomb()) {
      return false;
    }
    openCell(x, y);
    return !field[x][y].getIsGameOver();
  }

  private void openCell(int x, int y) {
    // 調べようとしてるのは壁だからカット
    if (x < 1 || y < 1 || x >= width + 1 || y >= height + 1) {
      return;
    }
    // すでに判明してるのでカット
    if (field[y][x].getIsOpen()) {
      return;
    }
    // 下にあるのは爆弾だからカット
    if (field[y][x].isHereBomb()) {
      return;
    }
    if (field[y][x].getActualData() >= GameCell.FIELD_DATA_EMPTY) {
      field[y][x].open();
      // System.out.println(cacheField[y][x]);
      // cacheField[y][x] = field[y][x].getFieldData();
      // そのセルが数字ならばそのセルを開けてストップ
      if (field[y][x].getActualData() > GameCell.FIELD_DATA_EMPTY) {
        return;
      }
    }

    for (int indexY = -1; indexY < 2; indexY++) {
      for (int indexX = -1; indexX < 2; indexX++) {
        if (indexX == 0 && indexY == 0) {
          continue;
        }
        openCell(x + indexX, y + indexY);
      }
    }
  }

  protected void construct(int x, int y) {
    int constructedBombNumber = 0;
    while (constructedBombNumber < bombnumber) {
      int constructedX = (int) (Math.random() * width) + 1;
      int constructedY = (int) (Math.random() * height) + 1;
      if (constructedX >= x - 1 && constructedX <= x + 1 && constructedY >= y - 1 && constructedY <= y + 1) {
        continue;
      }
      if (field[constructedY][constructedX].isHereBomb()) {
        continue;
      }
      field[constructedY][constructedX].construct();
      constructedBombNumber++;
    }
  }

  protected void calculate() {
    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        if (field[y][x].isHereBomb()) {
          for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
              if (field[y + i][x + j].isHereBomb()) {
                continue;
              }
              field[y + i][x + j].inclementFieldData();
            }
          }
        }
      }
    }
  }

  public int[][] getFieldData() {
    return cacheField;
  }
}