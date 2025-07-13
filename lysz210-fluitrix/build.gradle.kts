plugins {
    id("java")
}

group = "it.lysz210"
version = "1.0-SNAPSHOT"

val processingVersion = "3.3.7"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.processing/core
    implementation("org.processing:core:$processingVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}