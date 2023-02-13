# AsusProfiler
Power Controlling for ASUS laptops

This application is created based on the scripts provided by **u/desentizised** in this Reddit post ["How to gain even more control of your Flow X13"](https://www.reddit.com/r/FlowX13/comments/t32gra/how_to_gain_even_more_control_of_your_flow_x13/). There are 4 profiles you can choose from (ULTRA, SILENT, BALANCED and PERFORMANCE), which are fully customizable through the **applications.propreties**. You may compile the application yourself, which is created with Java and JavaFX, or run it straight away through the [ready executable](https://github.com/javasuns/AsusProfiler/releases/download/AsusProfiler/AsusProfiler.zip). 

[![BSD-3 license](https://img.shields.io/badge/license-BSD--3-%230778B9.svg)](https://opensource.org/licenses/BSD-3-Clause)

## Screenshots
|  Windows |
|  :----:  |
|  <img width="215" alt="AsusProfiler" src="https://user-images.githubusercontent.com/13131668/218494119-19387f42-22f0-4f2c-8106-3f4afd93f7d3.png"> <img width="329" alt="LogViewer" src="https://user-images.githubusercontent.com/13131668/218494165-bd26cc6b-4a77-4ef1-94e2-30c03ef336fa.png">|

## Builds

|  Windows |
|  :----:  |
|  [Download](https://github.com/javasuns/AsusProfiler/releases/download/AsusProfiler/AsusProfiler.zip)|

## Important Note
Since version 1.1 the executable image is not created with GraalVM and Gluon Client, since there is luck of AWT support which is needed for System Tray icons. Hence, jpackage is used to create the application image.

## Getting started

To compile the software code yourself in Windows you would need to apply the following steps:

### 1. Setting JAVA_HOME
**Windows Sample**

    set JAVA_HOME=C:\Programs\Java\jdk-17.0.4
    
### 2. Create executable Jar file.
The below gradle command will create **AsusProfiler-1.1.jar** under ./build/libs

    gradlew.bat clean build 
    
### 3. Use JPackage to create a Windows executable image

#### 3a. Setting Windows Path
    set PATH=%PATH%;%JAVA_HOME%\bin

#### 3b. Download Jmods for JavaFX
Since the application GUI is created with JavaFX you will need to download the latest jmods from [here](https://gluonhq.com/products/javafx/).
Once downloaded, extract the zip file in your preffered path. In this example I have download jmods 18.0.2 and extracted them under C:\Programs\Java\javafx-jmods-18.0.2.

#### 3c. Run JPackage to create image.

    jpackage --input build/libs ^
    --name AsusProfiler ^
    --main-jar AsusProfiler-1.1.jar ^
    --type app-image ^
    --module-path "C:\Programs\Java\javafx-jmods-18.0.2" ^
    --add-modules javafx.controls,javafx.fxml,javafx.graphics ^
    --app-version 1.1 ^
    --vendor "JavaSuns" ^
    --copyright "Copyright © 2022-23 JavaSuns" ^
    --icon src\main\resources\javasuns\profiler\asus\image\Logo.ico
    
#### 3d. Locate the executable.
The AsusProfiler directory is created with the Windows executable in it. You would need to add the **application.properties** file in it before being able to run it.

## Getting started \[Old Method\] (Native image with GraalVM and GluonHQ for v1.0)

To compile the software code yourself in Windows you would need to apply the following steps:
### 1. Install Microsoft Visual Studio

In order to create Microsft Windows native apps you will need Microsoft Visual Studio. Instructions can be found in [Gluon's website](https://docs.gluonhq.com/#platforms_windows).

### 2. Setting JAVA_HOME and GRAALVM_HOME

GraalVM is mandatory for creating native apps. It is available for download on the [GraalVM website](https://www.graalvm.org/downloads/). If you have a JDK already installed on your PC, it would be a wise to place GraalVM binaries under the same Java directory for consolidation. 
ASUS Profiler app has been tested against OpenJDK version 17.0.4 and GraalVM Community Edition 22.0.

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
    
    


