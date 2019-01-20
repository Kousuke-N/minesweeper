package components.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.MainFrame;

/**
 * StartPanel
 */
public class StartPanel extends JPanel {
  private MainFrame mainFrame;
  private JLabel titleLabel;

  private JButton buttonToEasyGame;
  private JButton buttonToNormalGame;
  private JButton buttonToHardGame;

  private JButton buttonToConfig;
  private JButton buttonToHelp;

  private JCheckBox checkBoxWithPlayer;

  public StartPanel(MainFrame mf) {
    mainFrame = mf;

    titleLabel = new JLabel("スタート画面");
    add(titleLabel);

    buttonToEasyGame = new JButton("Easyモード");
    add(buttonToEasyGame);
    buttonToNormalGame = new JButton("Normalモード");
    add(buttonToNormalGame);
    buttonToHardGame = new JButton("Hardモード");
    add(buttonToHardGame);

    buttonToConfig = new JButton("設定画面に遷移");
    add(buttonToConfig);
    buttonToHelp = new JButton("ヘルプ画面に遷移");
    add(buttonToHelp);

    checkBoxWithPlayer = new JCheckBox("プレイヤーが解く", true);
    add(checkBoxWithPlayer);

    buttonToEasyGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToGame(mainFrame.GAME_DIFFICULTY[0]);
      }
    });
    buttonToNormalGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToGame(mainFrame.GAME_DIFFICULTY[1]);
      }
    });
    buttonToHardGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToGame(mainFrame.GAME_DIFFICULTY[2]);
      }
    });
    buttonToConfig.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToConfig();
      }
    });
    buttonToHelp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToHelp();
      }
    });
  }

  public void panelChangeToGame(String difficulty) {
    mainFrame.panelChange(mainFrame.panelNames[1], difficulty, checkBoxWithPlayer.isSelected());
  }

  public void panelChangeToConfig() {
    mainFrame.panelChange(mainFrame.panelNames[2]);
  }

  public void panelChangeToHelp() {
    mainFrame.panelChange(mainFrame.panelNames[3]);
  }
}