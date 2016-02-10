# Dandy LRS (DLRS)
DLRS allows you to quickly record and retrieve discreet user activities. DLRS is anything but an LRS (Learning Record Store) but both have some features in common. DLRS is not written conforming to the LRS specification so obviously won't work with applications supporting the xAPI/LRS. DLRS was quickly written to act as a dandy and lightweight LRS that you can use as a record store to store all user activities.

This software is still under development. Don't even think of using it.

To build a JAR from the sources, run:

```
$ gradle assemble
```
You will find the JAR file from the libs dir.

To test DLRS, directly run:

```
$ ./gradlew run
```
