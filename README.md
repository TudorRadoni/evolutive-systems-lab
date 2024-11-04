# Evolutive Systems Lab

## Setup Instructions

### Prerequisites

- Java Development Kit (JDK) 17
- Maven (make sure it is added to the system PATH)

### Download and Set Up JDK 17 in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Go to `File` > `Project Structure` (or press `Ctrl+Alt+Shift+S`).
3. In the `Project Structure` dialog, go to `Platform Settings` > `SDKs`.
4. Click the `+` button and select `Add JDK`.
5. In the dialog that appears, select `Download JDK`.
6. Choose `17` from the list of available JDK versions and click `Download`.

### Set the JDK in the Project

1. In the `Project Structure` dialog, go to `Project Settings` > `Project`.
2. Set the `Project SDK` to the newly downloaded JDK 17.
3. Set the `Project language level` to `17 - Sealed types, always-strict floating-point semantics`.

### Update `pom.xml`

Ensure your `pom.xml` file specifies the correct Java version:

```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <lib.dir>${project.basedir}/lib</lib.dir>
</properties>
```

### Add JGAP Dependency

Download the JGAP library from [here](https://sourceforge.net/projects/jgap/files/jgap/JGAP%203.6.3/jgap_3.6.3_full.zip/download) and extract the contents to the `lib` directory in the project root.

Add the following dependency to the `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.jgap</groupId>
        <artifactId>jgap</artifactId>
        <version>3.6.3</version>
        <scope>system</scope>
        <systemPath>${lib.dir}/jgap_3.6.3_full/jgap.jar</systemPath>
    </dependency>
    <dependency>
        <groupId>org.jgap</groupId>
        <artifactId>jgap-examples</artifactId>
        <version>3.6.3</version>
        <scope>system</scope>
        <systemPath>${lib.dir}/jgap_3.6.3_full/jgap-examples.jar</systemPath>
    </dependency>
</dependencies>
```

### Build the Project

Run the following command to build the project:

```shell
mvn clean install
```
