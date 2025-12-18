plugins {
    // Kotlin 2.0.21로 업데이트
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.spring") version "2.0.21"
    kotlin("plugin.jpa") version "2.0.21"

    id("org.springframework.boot") version "3.3.6"
    id("io.spring.dependency-management") version "1.1.7"
}

val springCloudVersion by extra("2023.0.3")

group = "NoNamed"
version = "0.0.1-SNAPSHOT"
description = "OSJ_V3_BE"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // 1. Web & Data
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // 2. Kotlin 2.0 (의존성들도 최신 버전과 호환)
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // 3. Feign Client (Discord)
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // 4. DB
    runtimeOnly("com.mysql:mysql-connector-j")

    // 5. Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 6. Firebase Admin SDK
    implementation("com.google.firebase:firebase-admin:9.7.0")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

kotlin {
    // Kotlin 2.0에서 권장되는 컴파일러 옵션 설정 방식
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        // K2 컴파일러가 기본으로 활성화됩니다.
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}