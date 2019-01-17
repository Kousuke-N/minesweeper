package components.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * GameCell
 */
public class GameCell extends JButton {
  static final int FIELD_DATA_EMPTY = 0;
  static final int FIELD_DATA_BOMB = -1;
  static final int FIELD_DATA_HIDDEN = -2;
  static final int FIELD_DATA_MAX_NUMBER = 8;

  // private int x;
  // private int y;

  private boolean isOpen = false;
  private boolean isGameOver = false;
  private int fieldData = FIELD_DATA_EMPTY;

  protected void construct() {
    fieldData = FIELD_DATA_BOMB;
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