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
### 1. Install Microsoft Visual Studio

In order to create Microsft Windows native apps you will need Microsoft Visual Studio. Instructions can be found in [Gluon's website](https://docs.gluonhq.com/#platforms_windows).

### 2. Setting JAVA_HOME and GRAALVM_HOME

GraalVM is mandatory for creating native apps. It is available for download on the [GraalVM website](https://www.graalvm.org/downloads/). If you have a JDK already installed on your PC, it would be a wise to place GraalVM binaries under the same Java directory for consolidation. 
JavaFX Services app has been tested against OpenJDK version 17.0.4 and GraalVM Community Edition 22.0.

**Windows Sample**

    set JAVA_HOME=C:\Programs\Java\jdk-17.0.4
    set GRAALVM_HOME=C:\Programs\Java\graalvm-ce-java17-22.1.0

### 3. Tasks

You can run the regular tasks to build and run your project as a regular Java project:

    gradlew.bat clean build
    gradlew.bat run
    
Once you are able to run the pure Java project, you proceed with the native taks:    

#### `Desktop Native Application`

This task does the AOT compilation. It is a very intensive and lengthy task (several minutes, depending on your project and CPU).

Run the below commands through **x64 Native Tools Command Prompt for VS 2022**:

    gradlew.bat nativeCompile  # AOT Compilation
    gradlew.bat nativeLink     # Generates native executable
    gradlew.bat nativeRun      # Runs the generated executable

Alternatively, you may combine all the above commands into one:

    gradlew.bat nativeBuild nativeRun  # "nativeBuild" combines nativeCompile and nativeLink.
    
    


