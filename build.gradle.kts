plugins {
    id("java")
    application
}

application {
    mainClass.set("io.github.kkercz.stravavify.Main")
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

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}