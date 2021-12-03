# Base Framework for "Einführung in die Computergrafik"

## Projekt importieren

File - Open - Verzeichnis mit der Datei _buid.gradle_ auswählen
* als Gradle-Projekt

## Einstellungen

* Gradle-Einstellungen (Einstellungen - Preferences - Build,Execution,Deployment - Build Tools - Gradle)
  * Build and run using: Gradle
  * Run tests: Gradle
  * Use gradle from: 'gradle-wrapper-properties' file
  * Gradle JVM: 17
    * Gradle Wrapper 7.2 oder neuer notwendig, IntelliJ bietet bei Bedarf Update-Option an
* Projekteinstellungen (Einstellungen -> Project Structure -> Project)
  * SDK: 17
  * Language Level: 17

### Main Application (used for all exercises)

* `wpcg.CG3D`
* for each exercise:
  * create Scene = class implementing interface `wpcg.base.scene`
* set scene in `wpcg.CG3D.main()`
* Camera
  * Rotate: left mouse button press + move
  * Zoom: right mouse button + move

### 2D Main Application:

* `wpcg.CG2D`
* with example content `wpcg.base.canvas2d.Canvas2D` 
