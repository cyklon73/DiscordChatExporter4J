![[Java CI]](https://github.com/cyklon73/DiscordChatExporter4J/actions/workflows/check.yml/badge.svg)
![[Latest Version]](https://maven.cyklon.dev/api/badge/latest/releases/de/cyklon/DiscordChatExporter4J?prefix=v&name=Latest%20Version&color=0374b5)

# JEvent

DiscordChatExporter4J exports discord chats as Serial file and HTML render

# Installation

DiscordChatExporter4J is hosted on a custom repository at [https://maven.cyklon.dev](https://maven.cyklon.dev/#/releases/de/cyklon/DiscordChatExporter4J). Replace VERSION with the lastest version (without the `v` prefix).
Alternatively, you can download the artifacts from jitpack (not recommended).

### Gradle

```groovy
repositories {
  maven { url "https://maven.cyklon.dev/releases" }
}

dependencies {
  implementation "de.cyklon:DiscordChatExporter:VERSION"
}
```

### Maven

```xml
<repositories>
  <repository>
    <id>cyklon</id>
    <url>https://maven.cyklon.dev/releases</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>de.cyklon</groupId>
    <artifactId>DiscordChatExporter</artifactId>
    <version>VERSION</version>
  </dependency>
</dependencies>
```
