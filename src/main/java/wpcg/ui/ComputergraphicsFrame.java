package wpcg.ui;

import com.jme3.math.Vector2f;
import com.jme3.system.JmeCanvasContext;
import wpcg.base.Scene;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * This class represents and generates the Java Swing user interface.
 */
public class ComputergraphicsFrame extends JFrame {

  /**
   * Window size of the application
   */
  private static final int WINDOW_WIDTH = 900;
  private static final int WINDOW_HEIGHT = 600;

  /**
   * Width share for the 3D view, Must be in [0,1]
   */
  private static final float WINDOW_WIDTH_3DVIEW_SHARE = 0.7f;

  public ComputergraphicsFrame(JmeCanvasContext jmeCanvasContext, Scene scene) {
    super(scene.getTitle());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel);

    // JME
    Dimension dim = new Dimension((int) (WINDOW_WIDTH * WINDOW_WIDTH_3DVIEW_SHARE), WINDOW_HEIGHT);
    Canvas jmeCanvas = jmeCanvasContext.getCanvas();
    jmeCanvas.setPreferredSize(dim);
    JPanel jmePanel = new JPanel();
    jmePanel.setLayout(new BorderLayout());
    jmePanel.add(jmeCanvas, BorderLayout.CENTER);
    mainPanel.add(jmePanel);
    jmeCanvas.setMinimumSize(new Dimension(500, 500));
    jmeCanvas.requestFocus();
    jmeCanvas.requestFocusInWindow();

    // Settings
    JPanel uiPanel = new JPanel();
    uiPanel.setBorder(new TitledBorder(scene.getTitle()));
    uiPanel.setPreferredSize(new Dimension(WINDOW_WIDTH - (int) (WINDOW_WIDTH * WINDOW_WIDTH_3DVIEW_SHARE), WINDOW_HEIGHT));
    JPanel sceneUI = scene.getUI();
    if (sceneUI != null) {
      uiPanel.add(sceneUI);
    } else {
      uiPanel.add(new JLabel("no UI for scene"));
    }
    mainPanel.add(uiPanel, BorderLayout.EAST);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }
}
