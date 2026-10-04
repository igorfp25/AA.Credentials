# AA Credentials

AA Credentials is a custom Automation Anywhere package that adds credential-related actions for bots:

- **Generate Random Password** generates a secure random password and returns it as a Credential variable.

## Requirements

- Java 11
- The package SDK libraries required by the Gradle build in the `libs` directory

## Build

From the repository root, run:

```text
./gradlew.bat clean build shadowJar
```

In PowerShell, the equivalent command is:

```powershell
.\gradlew.bat clean build shadowJar
```

The Gradle wrapper downloads and uses the configured Gradle version. The build and Shadow JAR outputs are written under `build/`.
