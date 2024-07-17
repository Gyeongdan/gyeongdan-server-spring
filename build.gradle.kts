plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "gyeongdan"
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
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging") // Spring Boot 기본 로깅

    // QueryDSL
    implementation("com.querydsl:querydsl-collections:5.0.0")
    implementation("com.querydsl:querydsl-spatial:5.0.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.6.0")

    // OpenAI-Java 외부 라이브러리 추가 (GPT-4 사용을 위한 최신 버전)
    implementation("com.theokanning.openai-gpt3-java:api:0.18.2")
    implementation("com.theokanning.openai-gpt3-java:client:0.18.2")
    implementation("com.theokanning.openai-gpt3-java:service:0.18.2")

    // KOMORAN for Korean NLP
    implementation("com.github.shin285:KOMORAN:3.3.4")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
