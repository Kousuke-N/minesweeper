import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GamePanel
 */
public class GamePanel extends JPanel {
  MainFrame mainFrame;
  BufferedImage image;
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
    try {
      this.image = ImageIO.read(getClass().getResource("image/school.jpg"));
    } catch (IOException ex) {
      ex.printStackTrace();
      this.image = null;
    }
    setOpaque(false);

    if (difficulty == mainFrame.GAME_DIFFICULTY[0]) {
      width = 9;
      height = 9;
      bombnumber = 10;
    } else if (difficulty == mainFrame.GAME_DIFFICULTY[1]) {
      width = 16;
      height = 16;
      bombnumber = 40;
    } else if (difficulty == mainFrame.GAME_DIFFICULTY[2]) {
      width = 30;
      height = 16;
      bombnumber = 99;
    }
    fieldData = new int[height + 2][width + 2];
    openField = new int[height + 2][width + 2];
    field = new JButton[height + 2][width + 2];
    setLayout(new GridLayout(width, height));

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
            }

            if (fieldData[y][x] == -1) {
              // TODO:ゲームオーバー処理(アニメーションなど)
              mainFrame.panelChange(mainFrame.panelNames[0]);
            }
            openCell(x, y);
          }
        });
      }
    }
  }

  @Override // 上位クラスのメソッドを定義しなおしていることを示すJavaの注釈。なくても構いません
  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;

    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();
    double panelWidth = this.getWidth();
    double panelHeight = this.getHeight();

    // 画像がコンポーネントの何倍の大きさか計算
    double sx = (panelWidth / imageWidth);
    double sy = (panelHeight / imageHeight);

    // スケーリング
    AffineTransform af = AffineTransform.getScaleInstance(sx, sy);
    g2D.drawImage(image, af, this);
    super.paint(g);
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
    if (x < 1 || y < 1 || x >= width + 1 || y >= height + 1) {
      return;
    }
    if (openField[y][x] == -1) {
      return;
    }
    if (fieldData[y][x] == -1) {
      return;
    }
    if (fieldData[y][x] == 0) {
      openField[y][x] = -1;
      field[y][x].setEnabled(false);
      setForeground(Color.BLUE);
    }
    if (fieldData[y][x] > 0) {
      openField[y][x] = -1;
      field[y][x].setText(String.valueOf(fieldData[y][x]));
      field[y][x].setEnabled(false);
      setForeground(Color.BLUE);
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