import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StartPanel
 */
public class StartPanel extends JPanel {
  private MainFrame mainFrame;
  private JLabel titleLabel;
  private JButton buttonToGame;
  private JButton buttonToConfig;
  private JButton buttonToHelp;

  StartPanel(MainFrame mf) {
    mainFrame = mf;

    titleLabel = new JLabel("スタート画面");
    add(titleLabel);
    buttonToGame = new JButton("ゲーム画面に遷移");
    add(buttonToGame);
    buttonToConfig = new JButton("設定画面に遷移");
    add(buttonToConfig);
    buttonToHelp = new JButton("ヘルプ画面に遷移");
    add(buttonToHelp);

    buttonToGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToGame();
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

  public void panelChangeToGame() {
    System.out.println("to");
    mainFrame.panelChange(mainFrame.panelNames[1]);
  }

  public void panelChangeToConfig() {
    System.out.println("to");
    mainFrame.panelChange(mainFrame.panelNames[2]);
  }

  public void panelChangeToHelp() {
    System.out.println("to");
    mainFrame.panelChange(mainFrame.panelNames[3]);
  }
}