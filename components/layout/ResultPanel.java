package components.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.MainFrame;

/**
 * ResultPanel
 */
public class ResultPanel extends JPanel {

  MainFrame mainFrame;
  boolean result;

  BufferedImage image;
  JLabel text;
  JButton buttonToStart;

  public ResultPanel(MainFrame mf, boolean result) {
    mainFrame = mf;
    this.result = result;

    try {
      if (this.result) {
        this.image = ImageIO.read(getClass().getResource("../../image/rainbow.jpg"));
        this.text = new JLabel(new ImageIcon(getClass().getResource("../../image/congratulations.png")));
      } else {
        this.image = ImageIO.read(getClass().getResource("../../image/explosion.jpg"));
        this.text = new JLabel(new ImageIcon(getClass().getResource("../../image/gameover.png")));
      }
    } catch (IOException ex) {
      ex.printStackTrace();
      this.image = null;
    }
    setOpaque(false);

    buttonToStart = new JButton("スタート画面に戻る");
    buttonToStart.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        panelChangeToStart();
      }
    });

    text.setAlignmentX(Component.CENTER_ALIGNMENT);
    buttonToStart.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);
    infoPanel.add(text);
    infoPanel.add(buttonToStart);

    add(infoPanel, BorderLayout.CENTER);
  }

  @Override // 上位クラスのメソッドを定義しなおしていることを示すJavaの注釈。なくても構いません
  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;

    double imageWidth = image.getWidth();
    double imageHeight = image.getHeight();
    double panelWidth = this.getWidth();
    double panelHeight = this.getHeight();

    // 画像がコンポーネントの何倍の大きさか計算
    double sx = (panelWidth / imageWidth);
    double sy = (panelHeight / imageHeight);

    // スケーリング
    AffineTransform af = AffineTransform.getScaleInstance(sx, sy);
    g2D.drawImage(image, af, this);
    super.paint(g);
  }

  public void panelChangeToStart() {
    mainFrame.panelChange(mainFrame.panelNames[0]);
  }
}