# DLRS
DLRS (Distributed Learning Record Store) allows you to quickly record and retrieve discreet learning activities of users. DLRS is not an LRS ([Learning Record Store](https://tincanapi.com/learning-record-store/)) but is heavily based on the 'Statement' concept of LRS. DLRS is not written conforming to the LRS specification so obviously, it won't work with applications supporting the xAPI/LRS. DLRS was quickly written to act as a dandy and lightweight record store to store all user learning activities.

This software is still under development. Don't even think of using DLRS in production (for now).

## Building from source
Before you begin, [install Gradle](https://docs.gradle.org/current/userguide/installation.html) for your platform.

On Mac, it is as simple as:

```shell
$brew install gradle
```

To build DLRS from the sources, run:

```
$ gradle assemble
```

or:

```
$ gradle build
```

You need to have [Java SE 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or higher and [Gradle](http://gradle.org). 

## Testing DLRS
If you just want to test DLRS without building it from the sources, directly run:

```
$ ./gradlew run
```
You will see the following output:

```shell
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:run
 DLRS 1.0
  Loading config file...dlrsconfig.properties
  Starting default server...
  Listening on port...8678
  Loading DLRS data...DONE
  Enabling default context...
  Enabling default executor strategy...POOL
  DLRS Ready.
> Building 75% > :run
```

Change the default settings in ```dlrsconfig.properties``` under ```build\classes```.

When the DLRS server is running, open the following page to test:

```
http://localhost:8678/learn
```
## API Usage
For building clients that can work with DLRS, see the [Developer Guide](docs/API.md).
