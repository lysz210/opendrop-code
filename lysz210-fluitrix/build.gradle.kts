plugins {
    id("java")
}

group = "it.lysz210"
version = "1.0-SNAPSHOT"

val logbackClassicVersion = "1.5.18";
val jsscVersion = "2.8.0";
val processingVersion = "3.3.7"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation("ch.qos.logback", "logback-classic", logbackClassicVersion)
    // https://mvnrepository.com/artifact/org.scream3r/jssc
    implementation("org.scream3r", "jssc", jsscVersion)
    // https://mvnrepository.com/artifact/org.processing/core
    implementation("org.processing", "core", processingVersion)
    // https://mvnrepository.com/artifact/org.processing/serial
    implementation("org.processing","serial",processingVersion)
    // https://mvnrepository.com/artifact/org.processing/net
    implementation("org.processing","net",processingVersion)

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}