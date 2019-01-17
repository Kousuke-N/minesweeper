package components;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.layout.*;

/**
 * MineFrame
 */
public class MainFrame extends JFrame {
  public static final int FRAME_WIDTH = 1000;
  public static final int FRAME_HEIGHT = 700;
  public String[] panelNames = { "start", "game", "config", "help" };
  public String[] GAME_DIFFICULTY = { "easy", "normal", "hard", "popteamepic" };

  Container container;

  StartPanel startPanel;
  GamePanel gamePanel;
  ConfigPanel configPanel;
  HelpPanel helpPanel;

  public MainFrame() {
    super("Minesweeper");
    setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);

    startPanel = new StartPanel(this);

    container = getContentPane();
    container.add(startPanel);

    startPanel.setVisible(true);

  }

  public void panelChange(String toPanelName) {
    panelChange(toPanelName, GAME_DIFFICULTY[0]);
  }

  public void panelChange(String toPanelName, String difficulty) {
    container.removeAll();
    if (toPanelName == panelNames[0]) {
      startPanel = new StartPanel(this);
      container.add(startPanel);
    } else if (toPanelName == panelNames[1]) {
      gamePanel = new GamePanel(this, difficulty);
      container.add(gamePanel);
    } else if (toPanelName == panelNames[2]) {
      configPanel = new ConfigPanel(this);
      container.add(configPanel);
    } else if (toPanelName == panelNames[3]) {
      helpPanel = new HelpPanel(this);
      container.add(helpPanel);
    }
    validate();
    repaint();
  }
}