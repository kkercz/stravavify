plugins {
    id("java")
}

group = "io.github.kkercz"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("se.michaelthelin.spotify:spotify-web-api-java:9.2.0")
}

tasks.test {
    useJUnitPlatform()
}