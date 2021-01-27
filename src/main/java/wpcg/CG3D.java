package wpcg;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeContext;
import com.jme3.system.awt.AwtPanelsContext;
import wpcg.a1.IntroScene;
import wpcg.base.Scene;
import wpcg.ui.ComputergraphicsJMEApp;
import wpcg.ui.ComputergraphicsFrame;

import javax.swing.*;
import java.awt.*;
import java.util.SimpleTimeZone;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This launcher is used to start the 3D application with a Java Swing user interface.
 */
public class CG3D {

  private static ComputergraphicsJMEApp app;
  private static Scene scene;
  private static JmeCanvasContext jmeContext;

  public static void createJmeCanvas(){
    AppSettings settings = new AppSettings(true);
    settings.setWidth(500);
    settings.setHeight(500);
    app = new ComputergraphicsJMEApp(scene);
    app.setSettings(settings);
    app.setShowSettings(false);
    app.createCanvas();
    //app.setPauseOnLostFocus(false);
    jmeContext= (JmeCanvasContext) app.getContext();
    jmeContext.setSystemListener(app);
    jmeContext.getCanvas().setPreferredSize(new Dimension(500, 500));

  }

  public static void startApp(){
   app.startCanvas();
   app.enqueue(() -> {
     if ( app instanceof SimpleApplication){
       SimpleApplication simpleApplication = (SimpleApplication) app;
       simpleApplication.getFlyByCamera().setDragToRotate(true);
     }
   });
  }

  public static void main(String[] args) {
    // Change scene object here
    scene = new IntroScene();
    createJmeCanvas();

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    SwingUtilities.invokeLater( () -> {
              ComputergraphicsFrame cgFrame = new ComputergraphicsFrame(jmeContext, app, scene);
              startApp();
            });

    // no change here
    Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
  }
}
