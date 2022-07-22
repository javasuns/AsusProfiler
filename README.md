# AsusProfiler
Power Controlling for ASUS laptops

[![BSD-3 license](https://img.shields.io/badge/license-BSD--3-%230778B9.svg)](https://opensource.org/licenses/BSD-3-Clause)

## Screenshots

## Builds

|  Windows |
|  :----:  |
|  [Download](https://bitbucket.org/javasuns/javafx-services/downloads/JavaFX_Services.exe)|

## Getting started

To compile the software code yourself in Windows you would need to apply the following steps:

### 1. Setting JAVA_HOME and GRAALVM_HOME

GraalVM is mandatory for creating native apps. It is available for download on the [GraalVM website](https://www.graalvm.org/downloads/). If you have a JDK already installed on your PC, it would be a wise to place GraalVM binaries under the same Java directory for consolidation. 
JavaFX Services app has been tested against OpenJDK version 17.0.4 and GraalVM Community Edition 22.0.

**Windows Sample**

    JAVA_HOME=C:\Programs\Java\jdk-17.0.4
    GRAALVM_HOME=C:\Programs\Java\graalvm-ce-java17-22.1.0

### 2. Tasks

You can run the regular tasks to build and run your project as a regular Java project:

    ./gradlew clean build
    ./gradlew run
    
Once you are able to run the pure Java project, you proceed with the native taks:    

#### `Desktop Native Application`

This task does the AOT compilation. It is a very intensive and lengthy task (several minutes, depending on your project and CPU).

Run:

    ./gradlew nativeCompile  # AOT Compilation
    ./gradlew nativeLink     # Generates native executable
    ./gradlew nativeRun      # Runs the generated executable

Alternatively, you may combine all the above commands into one:

    ./gradlew nativeBuild nativeRun  # "nativeBuild" combines nativeCompile and nativeLink.
    
    


