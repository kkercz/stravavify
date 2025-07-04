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
    implementation("de.u-mass:lastfm-java:0.1.2")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
    implementation("org.slf4j:slf4j-nop:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}