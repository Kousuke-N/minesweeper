import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * MineFrame
 */
public class MainFrame extends JFrame {
  public String[] panelNames = { "start", "game", "config", "help" };
  public String[] GAME_DIFFICULTY = { "easy", "normal", "hard", "popteamepic" };

  Container container;

  StartPanel startPanel;
  GamePanel gamePanel;
  ConfigPanel configPanel;
  HelpPanel helpPanel;

  MainFrame() {
    super("Minesweeper");
    setBounds(100, 100, 800, 600);

    startPanel = new StartPanel(this);

    container = getContentPane();
    container.add(startPanel);

    startPanel.setVisible(true);
  }

  public static void main(String[] args) {
    MainFrame mainFrame = new MainFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);
  }

  public void panelChange(String toPanelName) {
    panelChange(toPanelName, GAME_DIFFICULTY[0]);
  }

  public void panelChange(String toPanelName, String difficulty) {
    container.removeAll();
    if (toPanelName == panelNames[0]) {
      startPanel = new StartPanel(this);
      add(startPanel);
    } else if (toPanelName == panelNames[1]) {
      gamePanel = new GamePanel(this, difficulty);
      add(gamePanel);
    } else if (toPanelName == panelNames[2]) {
      configPanel = new ConfigPanel(this);
      add(configPanel);
    } else if (toPanelName == panelNames[3]) {
      helpPanel = new HelpPanel(this);
      add(helpPanel);
    }
    validate();
    repaint();
  }
}