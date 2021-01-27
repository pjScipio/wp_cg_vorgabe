package wpcg.ui;

import com.jme3.math.Vector2f;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.awt.AwtPanelsContext;
import wpcg.base.Scene;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class represents and generates the Java Swing user interface.
 */
public class ComputergraphicsFrame extends JFrame implements MouseListener, MouseMotionListener {

  /**
   * Reference to the current scene.
   */
  private Scene scene;

  /**
   * Mouse click state
   */
  private boolean mouseLeftPressed, mouseRightPressed, mouseMiddlePressed;

  /**
   * Save the current mouse pos.
   */
  private Vector2f lastMousePos;

  /**
   * This is the reference to the JME canvas which is controlled by the mouse interface
   */
  private CameraControlled cameraControlledJmeFrame;

  /**
   * Window size of the application
   */
  private static final int WINDOW_WIDTH = 900;
  private static final int WINDOW_HEIGHT = 600;

  /**
   * Width share for the 3D view, Must be in [0,1]
   */
  private static final float WINDOW_WIDTH_3DVIEW_SHARE = 0.7f;


  public ComputergraphicsFrame(JmeCanvasContext jmeCanvasContext, CameraControlled cameraControlled, Scene scene) {
    super(scene.getTitle());
    this.cameraControlledJmeFrame = cameraControlled;
    this.scene = scene;
    this.mouseLeftPressed = false;
    this.mouseRightPressed = false;
    this.mouseMiddlePressed = false;

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


    jmeCanvas.setMinimumSize(new Dimension(500,500));

    jmeCanvas.addMouseListener(this);
    jmeCanvas.addMouseMotionListener(this);
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

    //setPreferredSize(new Dimension(700, 500));
    //setSize(700, 500);
    //jmeCanvas.setSize(700,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("Click");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == 1) {
      this.mouseLeftPressed = true;
    }
    if (e.getButton() == 2) {
      this.mouseMiddlePressed = true;
    }
    if (e.getButton() == 3) {
      this.mouseRightPressed = true;
    }
    lastMousePos = new Vector2f(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (e.getButton() == 1) {
      this.mouseLeftPressed = false;
    }
    if (e.getButton() == 2) {
      this.mouseMiddlePressed = false;
    }
    if (e.getButton() == 3) {
      this.mouseRightPressed = false;
    }
    lastMousePos = null;
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (lastMousePos != null) {
      float dx = e.getX() - lastMousePos.x;
      float dy = e.getY() - lastMousePos.y;

      if (mouseRightPressed) {
        // Zoom
        cameraControlledJmeFrame.zoom(dy);
      }

      if (mouseLeftPressed) {
        // Rotation
        cameraControlledJmeFrame.rotateHorizontal(-dx);
        cameraControlledJmeFrame.rotateVertical(dy);
      }
    }
    lastMousePos = new Vector2f(e.getX(), e.getY());
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }
}
