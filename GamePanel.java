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
  // 0: 何もなし、-1: 爆弾、1以上: 周囲の爆弾の数
  int[][] fieldData;
  // 1: 空いている、0：空いていない
  int[][] openField;
  JButton[][] field;
  boolean notClickedFlag = true;

  GamePanel(MainFrame mf, String difficulty) {
    mainFrame = mf;
    if (difficulty == "easy") {
      width = 10;
      height = 15;
      bombnumber = 20;
      fieldData = new int[height][width];
      openField = new int[height][width];
      field = new JButton[height][width];
      setLayout(new GridLayout(width, height));
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        field[y][x] = new JButton();
        field[y][x].setActionCommand(y + ":" + x);
        add(field[y][x]);

        field[y][x].addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String[] splitedCommand = command.split(":", 0);
            int x = Integer.parseInt(splitedCommand[1]);
            int y = Integer.parseInt(splitedCommand[0]);

            openField[y][x] = 1;
            if (notClickedFlag) {
              construct(y, x);
              // 数字の計算
            }

            field[y][x].setEnabled(false);
            if (fieldData[y][x] == -1) {
              mainFrame.panelChange(mainFrame.panelNames[0]);
            }
          }
        });
      }
    }
  }

  void construct(int y, int x) {
    int constructedBombNumber = 0;
    while (constructedBombNumber < bombnumber) {
      int constructedX = (int) (Math.random() * width);
      int constructedY = (int) (Math.random() * height);
      if (constructedX == x && constructedY == y) {
        continue;
      }
      if (fieldData[constructedY][constructedX] == -1) {
        continue;
      }
      fieldData[constructedY][constructedX] = -1;
      constructedBombNumber++;
    }
  }
}