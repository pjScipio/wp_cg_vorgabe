package wpcg.ui;

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
  public static final int JME_CANVAS_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 800;
  private static final int SETTINGS_CANVAS_WIDTH = 300;

  public ComputergraphicsFrame(JmeCanvasContext jmeCanvasContext, Scene scene) {
    super(scene.getTitle());

    getContentPane().setLayout(new BorderLayout());

    // JME
    Dimension dim = new Dimension(JME_CANVAS_WIDTH, WINDOW_HEIGHT);
    Canvas jmeCanvas = jmeCanvasContext.getCanvas();
    jmeCanvas.setPreferredSize(dim);
    getContentPane().add(jmeCanvas, BorderLayout.CENTER);

    // Settings
    JPanel uiPanel = new JPanel();
    uiPanel.setBorder(new TitledBorder(scene.getTitle()));
    uiPanel.setPreferredSize(new Dimension(SETTINGS_CANVAS_WIDTH, WINDOW_HEIGHT));
    JPanel sceneUI = scene.getUI();
    if (sceneUI != null) {
      uiPanel.add(sceneUI);
    } else {
      uiPanel.add(new JLabel("no UI for scene"));
    }
    getContentPane().add(uiPanel, BorderLayout.EAST);

    setSize(JME_CANVAS_WIDTH+SETTINGS_CANVAS_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }
}
