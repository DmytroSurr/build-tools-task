import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  	id("org.springframework.boot") version "2.7.14"
  	id("io.spring.dependency-management") version "1.1.0"
  	kotlin("jvm") version "1.8.21"
  	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.serialization") version "1.8.21"
}

group = "academy.softserve"
version = "0.0.1-SNAPSHOT"
java {
	sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}


// Frontend build tasks for Vite + React + TypeScript

tasks.register<Exec>("npmInstall") {
    workingDir = file("ui")
    commandLine = listOf("npm", "install")
}

tasks.register<Exec>("compileUi") {
    workingDir = file("ui")
    commandLine = listOf("npm", "run", "build")
    dependsOn("npmInstall")
}

tasks.register<Copy>("copyUi") {
    from("ui/dist")
    into("src/main/resources/static")
    dependsOn("compileUi")
}

tasks.named<ProcessResources>("processResources") {
    dependsOn("copyUi")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    dependsOn("copyUi")
}
