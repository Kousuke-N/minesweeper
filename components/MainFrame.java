package components;

import java.awt.Container;

import javax.swing.JFrame;

import components.layout.*;

/**
 * MineFrame
 */
public class MainFrame extends JFrame {
  public static final int FRAME_WIDTH = 800;
  public static final int FRAME_HEIGHT = 630;
  public String[] panelNames = { "start", "game", "config", "help", "result" };
  public String[] GAME_DIFFICULTY = { "easy", "normal", "hard", "popteamepic" };

  Container container;

  StartPanel startPanel;
  GamePanel gamePanel;
  ConfigPanel configPanel;
  HelpPanel helpPanel;
  ResultPanel resultPanel;

  public MainFrame() {
    super("Minesweeper");
    setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);

    startPanel = new StartPanel(this);

    container = getContentPane();
    container.add(startPanel);

    startPanel.setVisible(true);

  }

  public void panelChange(String toPanelName) {
    panelChange(toPanelName, GAME_DIFFICULTY[0], true);
  }

  public void panelChange(String toPanelName, boolean result) {
    panelChange(toPanelName, "", true, result);
  }

  public void panelChange(String toPanelName, String difficulty, boolean withPlayer) {
    panelChange(toPanelName, difficulty, withPlayer, true);
  }

  public void panelChange(String toPanelName, String difficulty, boolean withPlayer, boolean result) {
    container.removeAll();
    if (toPanelName == panelNames[0]) {
      startPanel = new StartPanel(this);
      container.add(startPanel);
    } else if (toPanelName == panelNames[1]) {
      gamePanel = new GamePanel(this, difficulty, withPlayer);
      container.add(gamePanel);
    } else if (toPanelName == panelNames[2]) {
      configPanel = new ConfigPanel(this);
      container.add(configPanel);
    } else if (toPanelName == panelNames[3]) {
      helpPanel = new HelpPanel(this);
      container.add(helpPanel);
    } else if (toPanelName == panelNames[4]) {
      resultPanel = new ResultPanel(this, result);
      container.add(resultPanel);
    }
    validate();
    repaint();
  }
}