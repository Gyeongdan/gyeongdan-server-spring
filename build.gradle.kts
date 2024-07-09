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
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("com.googlecode.json-simple:json-simple:1.1.1")
	implementation("com.h2database:h2")
	implementation("org.slf4j:slf4j-api:2.0.7")
	implementation("ch.qos.logback:logback-classic:1.4.7")

	// WebJars dependencies
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
