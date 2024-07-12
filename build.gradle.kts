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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.slf4j:slf4j-api:2.0.7")


    // QueryDSL
    implementation("com.querydsl:querydsl-collections:5.0.0")
    implementation("com.querydsl:querydsl-spatial:5.0.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")


    runtimeOnly("org.postgresql:postgresql:42.6.0")



    // WebJars dependencies
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:jquery:3.6.0")
    implementation("org.webjars:bootstrap:4.6.0")
    implementation("org.webjars:sockjs-client:1.5.1")
    implementation("org.webjars:stomp-websocket:2.3.3")


    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
