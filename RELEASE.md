# Release

1. Drop '-SNAPSHOT' from the version string. For example, `1.0.0-SNAPSHOT` becomes `1.0.0`. Do this in:
  * `build.gradle` (version specification)
  * `README.md` (usage instruction)
  * `CHANGELOG.md` (the topmost version)
2. Commit the changes with message `Release 1.0.0`.
3. Tag this commit with the new version `1.0.0`.
4. Publish the plugin to Gradle's plugin portal with `gradle publishPlugins`.
5. Bump to the next -SNAPSHOT version. For example, `version '1.0.0'` becomes `version '2.0.0-SNAPSHOT'`.
6. Commit the changes with message `Set version to 2.0.0-SNAPSHOT`.

In the future, consider using the [axion-release-plugin](https://github.com/allegro/axion-release-plugin).
