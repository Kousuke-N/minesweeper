import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GamePanel
 */
public class GamePanel extends JPanel {
  MainFrame mainFrame;
  int width;
  int height;
  int bombnumber;

  // 以下３つのfieldは周囲に余分に一桝関係ないマスを置いているので注意。壁用。
  // 0: 何もなし、-1: 爆弾、1以上: 周囲の爆弾の数
  int[][] fieldData;
  // -1: 空いている、0：空いていない、1以上：周囲の爆弾の数
  int[][] openField;
  JButton[][] field;

  boolean notClickedFlag = true;

  GamePanel(MainFrame mf, String difficulty) {
    mainFrame = mf;
    if (difficulty == mainFrame.GAME_DIFFICULTY[0]) {
      width = 8;
      height = 8;
      bombnumber = 10;
      fieldData = new int[height + 2][width + 2];
      openField = new int[height + 2][width + 2];
      field = new JButton[height + 2][width + 2];
      setLayout(new GridLayout(width, height));
    }

    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        field[y][x] = new JButton();
        field[y][x].setActionCommand(y + ":" + x);
        if (y == 0 || y == height + 1 || x == 0 || x == width + 1) {
          continue;
        }
        add(field[y][x]);

        field[y][x].addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String[] splitedCommand = command.split(":", 0);
            int x = Integer.parseInt(splitedCommand[1]);
            int y = Integer.parseInt(splitedCommand[0]);

            if (notClickedFlag) {
              notClickedFlag = !notClickedFlag;
              construct(y, x);
              calculate();
              printField();
            }

            if (fieldData[y][x] == -1) {
              // ゲームオーバー処理
              mainFrame.panelChange(mainFrame.panelNames[0]);
            }
            openCell(x, y);
          }
        });
      }
    }
  }

  void construct(int y, int x) {
    int constructedBombNumber = 0;
    while (constructedBombNumber < bombnumber) {
      int constructedX = (int) (Math.random() * width) + 1;
      int constructedY = (int) (Math.random() * height) + 1;
      if (constructedX >= x - 1 && constructedX <= x + 1 && constructedY >= y - 1 && constructedY <= y + 1) {
        continue;
      }
      if (fieldData[constructedY][constructedX] == -1) {
        continue;
      }
      fieldData[constructedY][constructedX] = -1;
      constructedBombNumber++;
    }
  }

  void calculate() {
    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        if (fieldData[y][x] == -1) {
          for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
              if (fieldData[y + i][x + j] == -1) {
                continue;
              }
              fieldData[y + i][x + j]++;
            }
          }
        }
      }
    }
  }

  void openCell(int x, int y) {
    System.out.println(x + "," + y);
    if (x < 1 || y < 1 || x >= width + 1 || y >= height + 1) {
      System.out.println("a");
      return;
    }
    if (openField[y][x] == -1) {
      System.out.println("b");
      return;
    }
    if (fieldData[y][x] == -1) {
      System.out.println("c");
      return;
    }
    if (fieldData[y][x] == 0) {
      System.out.println("d");
      openField[y][x] = -1;
      field[y][x].setEnabled(false);
    }
    if (fieldData[y][x] > 0) {
      System.out.println("e");
      openField[y][x] = -1;
      field[y][x].setText(String.valueOf(fieldData[y][x]));
      field[y][x].setEnabled(false);
      return;
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

  void printField() {
    for (int y = 0; y < height + 2; y++) {
      for (int x = 0; x < width + 2; x++) {
        System.out.print(fieldData[y][x] + ", ");
      }
      System.out.println();
    }
  }
}