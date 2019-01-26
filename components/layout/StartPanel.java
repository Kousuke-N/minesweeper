package components.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

  private BufferedImage image;

  private GridBagLayout layout;
  private JPanel infoPanel;
  private GridBagConstraints gbc;
  private JButton buttonToEasyGame;
  private JButton buttonToNormalGame;
  private JButton buttonToHardGame;

  private JButton buttonToConfig;
  private JButton buttonToHelp;

  private JCheckBox checkBoxWithPlayer;

  public StartPanel(MainFrame mf) {
    mainFrame = mf;

    try {
      this.image = ImageIO.read(getClass().getResource("../../image/school.jpg"));
    } catch (IOException ex) {
      ex.printStackTrace();
      this.image = null;
    }
    setBackground(new Color(250, 250, 250, 125));

    layout = new GridBagLayout();
    setLayout(layout);
    gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    gbc.gridheight = 4;
    gbc.fill = GridBagConstraints.BOTH;
    titleLabel = new JLabel(new ImageIcon("./image/logo.png"));
    titleLabel.setLayout(new BorderLayout());
    titleLabel.setOpaque(false);
    titleLabel.setPreferredSize(new Dimension(mainFrame.FRAME_WIDTH / 2, mainFrame.FRAME_HEIGHT));
    layout.setConstraints(titleLabel, gbc);
    add(titleLabel);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0d;
    gbc.weighty = 0;
    gbc.gridheight = 1;
    infoPanel = new JPanel();
    infoPanel.setOpaque(false);
    layout.setConstraints(infoPanel, gbc);
    add(infoPanel);
    buttonToConfig = new JButton("設定画面に遷移");
    buttonToHelp = new JButton("ヘルプ画面に遷移");
    checkBoxWithPlayer = new JCheckBox("プレイヤーが解く", true);
    // 今んとこ使わないのでコメントアウト。使う時がきたらロゴ入れたい
    // infoPanel.add(buttonToConfig);
    // infoPanel.add(buttonToHelp);
    infoPanel.add(checkBoxWithPlayer);

    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    gbc.gridheight = 1;
    buttonToEasyGame = new JButton(new ImageIcon(getClass().getResource("../../image/easy.png")));
    buttonToEasyGame.setOpaque(false);
    buttonToEasyGame.setBorderPainted(false);
    buttonToEasyGame.setSize(new Dimension(mainFrame.FRAME_WIDTH / 2, mainFrame.FRAME_WIDTH / 4));
    layout.setConstraints(buttonToEasyGame, gbc);
    add(buttonToEasyGame);

    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    gbc.gridheight = 1;
    buttonToNormalGame = new JButton(new ImageIcon(getClass().getResource("../../image/normal.png")));
    buttonToNormalGame.setOpaque(false);
    buttonToNormalGame.setBorderPainted(false);
    buttonToNormalGame.setSize(new Dimension(mainFrame.FRAME_WIDTH / 2, mainFrame.FRAME_WIDTH / 4));
    layout.setConstraints(buttonToNormalGame, gbc);
    add(buttonToNormalGame);

    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridheight = 1;
    buttonToHardGame = new JButton(new ImageIcon(getClass().getResource("../../image/hard.png")));
    buttonToHardGame.setOpaque(false);
    buttonToHardGame.setBorderPainted(false);
    buttonToHardGame.setSize(new Dimension(mainFrame.FRAME_WIDTH / 2, mainFrame.FRAME_WIDTH / 4));
    layout.setConstraints(buttonToHardGame, gbc);
    add(buttonToHardGame);

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
}