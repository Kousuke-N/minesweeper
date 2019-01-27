package components.layout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import components.MainFrame;
import components.game.GameField;

/**
 * GamePanel
 */
public class GamePanel extends JPanel {
  static final int EASY_WIDTH = 9;
  static final int EASY_HEIGHT = 9;
  static final int EASY_BOMB_NUMBER = 10;

  static final int NORMAL_WIDTH = 16;
  static final int NORMAL_HEIGHT = 16;
  static final int NORMAL_BOMB_NUMBER = 40;

  static final int HARD_WIDTH = 22;
  static final int HARD_HEIGHT = 22;
  static final int HARD_BOMB_NUMBER = 99;

  static final int FIELD_DATA_EMPTY = 0;
  static final int FIELD_DATA_BOMB = -1;
  static final int OPEN_FIELD_VISIBLE = 0;
  static final int OPEN_FIELD_HIDDEN = -1;

  MainFrame mainFrame;
  GridBagLayout gridBagLayout;
  BufferedImage image;
  int width;
  int height;
  int bombnumber;

  // 以下３つのfieldは周囲に余分に一桝関係ないマスを置いているので注意。壁用。
  // 0: 何もなし、-1: 爆弾、1以上: 周囲の爆弾の数
  private int[][] fieldData;
  // -1：空いていない、0: 空いている、1以上：周囲の爆弾の数
  private int[][] openField;
  private JButton[][] field;
  private GameField gameField;

  boolean notClickedFlag = true;

  public GamePanel(MainFrame mf, String difficulty, boolean withPlayer) {
    mainFrame = mf;
    try {
      this.image = ImageIO.read(getClass().getResource("../../image/school.jpg"));
    } catch (IOException ex) {
      ex.printStackTrace();
      this.image = null;
    }
    setOpaque(false);

    if (difficulty == mainFrame.GAME_DIFFICULTY[0]) {
      width = EASY_WIDTH;
      height = EASY_HEIGHT;
      bombnumber = EASY_BOMB_NUMBER;
    } else if (difficulty == mainFrame.GAME_DIFFICULTY[1]) {
      width = NORMAL_WIDTH;
      height = NORMAL_HEIGHT;
      bombnumber = NORMAL_BOMB_NUMBER;
    } else if (difficulty == mainFrame.GAME_DIFFICULTY[2]) {
      width = HARD_WIDTH;
      height = HARD_HEIGHT;
      bombnumber = HARD_BOMB_NUMBER;
    }

    // TODO:プレイヤかソルバかを選択できるようにする。
    gameField = new GameField(this, 800, 600, width, height, bombnumber, withPlayer);
    add(gameField);
  }

  @Override
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

  public void toResult(boolean result) {
    mainFrame.panelChange(mainFrame.panelNames[4], result);
  }
}