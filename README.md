# AsusProfiler
Power Controlling for ASUS laptops

This application is created based on the scripts provided by **u/desentizised** in this Reddit post ["How to gain even more control of your Flow X13"](https://www.reddit.com/r/FlowX13/comments/t32gra/how_to_gain_even_more_control_of_your_flow_x13/). There are 4 profiles you can choose from (ULTRA, SILENT, BALANCED and PERFORMANCE), which are fully customizable through the **applications.propreties**. You may compile the application yourself, which is created with Java and JavaFX, or run it straight away through the [ready executable](https://github.com/javasuns/AsusProfiler/releases/download/AsusProfiler/AsusProfiler.zip). 

[![BSD-3 license](https://img.shields.io/badge/license-BSD--3-%230778B9.svg)](https://opensource.org/licenses/BSD-3-Clause)

## Screenshots
|  Windows |
|  :----:  |
|  <img width="215" alt="AsusProfiler" src="https://user-images.githubusercontent.com/13131668/180485991-c9c1ae8b-5b28-4c46-90a4-2433e738aca6.PNG"> <img width="309" alt="LogViewer" src="https://user-images.githubusercontent.com/13131668/180486709-c997dc6d-3a65-46df-ab27-68659d29254b.PNG">|

## Builds

|  Windows |
|  :----:  |
|  [Download](https://github.com/javasuns/AsusProfiler/releases/download/AsusProfiler/AsusProfiler.zip)|

## Getting started

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
    
    


