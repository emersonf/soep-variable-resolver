import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.20"
    val springBootVersion = "2.5.2" // duplicated below

    java
    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
}

group = "internal"
version = "0.1.1"

val springBootVersion = "2.5.2" // duplicated above

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework:spring-web")
    implementation("org.jsoup:jsoup:1.13.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("org.hsqldb:hsqldb")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
