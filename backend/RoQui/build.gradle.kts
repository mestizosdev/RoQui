import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
	kotlin("kapt") version "1.9.21"
}

group = "dev.mestizos"
version = "0.0.2"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// SystemUtils
	implementation("org.apache.commons:commons-lang3:3.13.0")
	// AutoMapper Enity to DTO
	implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
//	kapt("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
	// Printer
	implementation("dev.mestizos.printer:RoquiPrinter:1.0.0")
	implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.1")
	// Signer
	implementation("dev.mestizos.signer:RoquiSigner:1.0.0")
	implementation("com.googlecode.xades4j:xades4j:1.7.0")
	implementation("com.sun.xml.bind:jaxb-impl:2.3.9")
	// Client SRI
	implementation("dev.mestizos.client:RoquiClientSri:1.0.0")
	implementation("com.sun.xml.ws:jaxws-rt:4.0.0")
	implementation("com.thoughtworks.xstream:xstream:1.4.20")
	implementation("commons-io:commons-io:2.12.0")
		}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

