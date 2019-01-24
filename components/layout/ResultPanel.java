package components.layout;

import javax.swing.JPanel;

import components.MainFrame;

/**
 * ResultPanel
 */
public class ResultPanel extends JPanel {

  MainFrame mainFrame;
  boolean result;

  public ResultPanel(MainFrame mf, boolean result) {
    mainFrame = mf;
    this.result = result;
  }
}