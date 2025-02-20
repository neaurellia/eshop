val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.10.1" // 🔥 Updated to 5.10.1 (Matches Spring Boot 3.4.2)

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "id.ui.ac.cs.advprog"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Lombok dependency (for compile-time code generation)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Spring Boot DevTools for development only
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Spring Boot Configuration Processor (for configuration metadata)
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Testing dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine") // 🚀 Exclude JUnit 4 to avoid conflicts
    }

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")

    // ✅ Use JUnit 5.10.1 to match Spring Boot 3.4.2
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")

    // Mockito for unit testing
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}

// 🚀 Ensure dependency resolution consistency
dependencyManagement {
    dependencies {
        dependencySet("org.junit.jupiter:$junitJupiterVersion") {
            entry("junit-jupiter-api")
            entry("junit-jupiter-engine")
            entry("junit-jupiter-params")
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform() // 🔥 Ensures JUnit 5 is used
}

// Test tasks
tasks.named<Test>("test") {
    filter {
        excludeTestsMatching("*FunctionalTest") // Exclude functional tests from regular test task
    }
    finalizedBy("jacocoTestReport") // Ensure JaCoCo always runs
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)
}
