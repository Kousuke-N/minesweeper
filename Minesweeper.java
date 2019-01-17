import javax.swing.JFrame;

import components.MainFrame;

/**
 * Minesweeper
 */
public class Minesweeper {

  public static void main(String[] args) {
    MainFrame mainFrame = new MainFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);
  }
}