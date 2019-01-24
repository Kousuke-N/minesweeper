package components.game;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * GameCell
 */
public class GameCell extends JButton {
  static final int FIELD_DATA_EMPTY = 0;
  static final int FIELD_DATA_BOMB = -1;
  static final int FIELD_DATA_HIDDEN = -2;
  static final int FIELD_DATA_MAX_NUMBER = 8;

  private static ImageIcon flagIcon;
  ImageIcon smallIcon;

  // private int x;
  // private int y;
  private GameField gameField;

  private boolean isOpen = false;
  private boolean isFlag = false;
  private boolean isGameOver = false;
  private int fieldData = FIELD_DATA_EMPTY;

  public GameCell(GameField gf) {
    super();

    gameField = gf;

    // 旗用画像の処理
    flagIcon = new ImageIcon("./image/anctlogo.png");
    MediaTracker tracker = new MediaTracker(this);
    Image smallImg = flagIcon.getImage().getScaledInstance((int) (flagIcon.getIconWidth() * 0.17), -1,
        Image.SCALE_SMOOTH);
    tracker.addImage(smallImg, 1);
    smallIcon = new ImageIcon(smallImg);

    try {
      tracker.waitForAll();
    } catch (InterruptedException e) {
      System.out.println("なんかエラーでた。");
    }

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          System.out.println("右クリックされたよ！！");
          switchFlag();
          repaint();
        }
      }
    });
  }

  protected void construct() {
    fieldData = FIELD_DATA_BOMB;
  }

  protected void switchFlag() {
    isFlag = !isFlag;
    if (!isOpen) {
      if (isFlag) {
        setIcon(smallIcon);
      } else {
        setIcon(null);
      }
    }
  }

  protected boolean getIsFlag() {
    return isFlag;
  }

  protected void setFieldData(int data) {
    if (data < FIELD_DATA_BOMB || data > FIELD_DATA_MAX_NUMBER) {
      return;
    }
    fieldData = data;
  }

  protected void inclementFieldData() {
    if (fieldData >= FIELD_DATA_EMPTY) {
      fieldData++;
    }
  }

  protected void open() {
    isOpen = true;
    if (fieldData > FIELD_DATA_EMPTY) {
      setText(String.valueOf(fieldData));
      setEnabled(false);
    }
    if (fieldData == FIELD_DATA_EMPTY) {
      setEnabled(false);
    }
    if (fieldData == FIELD_DATA_BOMB) {
      isGameOver = true;
    }
    gameField.decrementClosedCellNumber();
  }

  protected boolean getIsGameOver() {
    return isGameOver;
  }

  protected boolean getIsOpen() {
    return isOpen;
  }

  protected boolean isHereBomb() {
    if (fieldData == FIELD_DATA_BOMB) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * フィールドデータを取得する関数
   * 
   * @return このcellが空いていればデータを、隠れていれば-2を返す
   */
  protected int getFieldData() {
    if (isOpen) {
      return fieldData;
    } else {
      return FIELD_DATA_HIDDEN;
    }
  }

  /**
   * フィールドデータを取得する 上との違いは爆弾含めわかる
   * 
   * @return このセルの実際のデータ
   */
  protected int getActualData() {
    return fieldData;
  }

  // protected int getCellX() {
  // return x;
  // }

  // protected int getCellY() {
  // return y;
  // }
}