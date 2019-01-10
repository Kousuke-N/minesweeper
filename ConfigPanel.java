import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ConfigPanel
 */
public class ConfigPanel extends JPanel {
  MainFrame mainFrame;
  JButton buttonToStart;

  ConfigPanel(MainFrame mf) {
    mainFrame = mf;

    buttonToStart = new JButton("スタート画面に遷移");
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