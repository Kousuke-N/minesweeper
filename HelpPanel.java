import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * HelpPanel
 */
public class HelpPanel extends JPanel {
  MainFrame mainFrame;
  JButton buttonToStart;

  HelpPanel(MainFrame mf) {
    mainFrame = mf;
    setBackground(Color.BLACK);

    buttonToStart = new JButton("スタート画面に戻る");
    add(buttonToStart);

    buttonToStart.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToStart();
      }
    });
  }

  public void panelChangeToStart() {
    mainFrame.panelChange(mainFrame.panelNames[0]);
  }
}