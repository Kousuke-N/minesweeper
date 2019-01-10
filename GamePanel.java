import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GamePanel
 */
public class GamePanel extends JPanel {

  private JLabel difficultyLabel;

  GamePanel(MainFrame mf, String difficulty) {
    difficultyLabel = new JLabel(difficulty);
  }
}