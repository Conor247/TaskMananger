plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"

    kotlin("jvm") version "1.8.21"
    id("jacoco")

    id ("info.solidsoft.pitest") version "1.15.0"
}

group = "com.conor"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.mongodb:mongodb-driver-reactivestreams")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")

    pitest("org.pitest:pitest-junit5-plugin:0.14")

    testImplementation("org.mockito:mockito-core:5.12.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // Ensure tests run before generating the report

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

pitest {
    junit5PluginVersion.set("1.2.0")
    testPlugin.set("junit5")
    threads.set(4)
    outputFormats.set(listOf("HTML", "XML"))
    timestampedReports.set(false)
    mutators.set(listOf("DEFAULTS"))
    targetClasses.set(listOf("com.conor.*"))
    targetTests.set(listOf("com.conor.*Test"))
}
