# Base Framework for "Einführung in die Computergrafik"

## Install

IntelliJ Import -> Gradle project

## General usage

### Main Application (used for all exercises) 

* `wpcg.ComputergraphicsApplication`
* for each exercise:
    * create Scene = class implementing interface `wpcg.base.scene`
* set scene in `wpcg.ComputergraphicsApplication.main()`
* Camera
    * Rotate: left mouse button press + move
    * Zoom: Mouse wheel oder middle mouse button + move

### 2D Main Application: 

* `wpcg.ComputergraphicsApplication2D`
* with example content `pcg.base.canvas2d.Canvas2D` 
            
