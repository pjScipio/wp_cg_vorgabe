# Base Framework for "Einführung in die Computergrafik"

## Install

IntelliJ Import -> Gradle project

## General usage

* Main Application (used for all exercises): 

        wpcg.ComputergraphicsApplication
        
* for each exercise:
    * create Scene = class implementing interface 
    
            wpcg.base.scene

    * set scene in 

            wpcg.ComputergraphicsApplication.main()