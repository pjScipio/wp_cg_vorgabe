# Base Framework for "Einführung in die Computergrafik"

## Prerequisites

* JDK 14

## Dependencies

* cg_math
* cg_ui

The dependencies are handled via submodules. In order to use the submodules, one
needs du subsequently run

    git submodule init

und

    git submodule update

in all three subdirectories *cg_math* and *cg_ui*.

## Build

Open *wp_cg_vorgabe* in IntelliJ as Gradle project

or

Run in the *wp_cg_vorgabe*-directory:

    gradle build

or

    ./gradlew build
