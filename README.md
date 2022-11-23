Calculate & Find Dependencies of items
======================================
[![Build and test](https://github.com/epnikolov/calculate-dependencies/actions/workflows/gradle.yaml/badge.svg)](https://github.com/epnikolov/calculate-dependencies/actions/workflows/gradle.yaml)
## Description
The app takes as input arguments as a set of lines where the first token is the name of an item.
The remaining tokens are the names of things that this first item depends on. Given the following input,
we know that A directly depends on B and C, B depends on C and E.<br />
A B C<br />
B C E<br />
The program should use this data to calculate and display the full set of dependencies and
inverse dependencies.

## Requirements
Java 17

## Running the app
Run directly:
```bash
- cd ~/CalculateDependencies/src/main/java
- javac com/urbanise/Main.java com/urbanise/DependencyGraph.java
- java com.urbanise.Main "A B C" "B C E" "C G" "D A F" "E F" "F H"
```
---
Run with Gradle:
```bash
- cd ~/CalculateDependencies
- gradlew jar
- java -jar /build/libs/NAME_OF_THE_JAR.jar "A B C" "B C E" "C G" "D A F" "E F" "F H"
```
---
Run tests:
```bash
- cd ~/CalculateDependencies
- gradlew test
```