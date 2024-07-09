plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "gyeongdan"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JWT Token
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // PostgreSQL Driver
    implementation("org.postgresql:postgresql:42.6.0")

    // OpenAI-Java 외부 라이브러리 추가 (GPT-4o 사용을 위한 최신 버전)
    implementation("com.theokanning.openai-gpt3-java:api:0.18.2") // API 모듈 추가
    implementation("com.theokanning.openai-gpt3-java:client:0.18.2") // 클라이언트 모듈 추가
    implementation("com.theokanning.openai-gpt3-java:service:0.18.2") // 서비스 모듈 추가

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
