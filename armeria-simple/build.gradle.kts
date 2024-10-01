import com.google.protobuf.gradle.id

plugins {
    application
    kotlin("jvm") version "1.9.24"
    id("com.google.protobuf") version "0.9.3"
}

group = "org.cyk"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc")
            }
        }
    }
}

dependencies {
    implementation ("com.alibaba.nacos:nacos-client:2.3.2")
    implementation ("com.linecorp.armeria:armeria:1.30.1")
    implementation ("com.linecorp.armeria:armeria-grpc:1.30.1")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    runtimeOnly ("ch.qos.logback:logback-classic:1.4.14")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation ("com.linecorp.armeria:armeria-junit5:1.30.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
    options.encoding = "UTF-8"
    options.isDebug = true
    options.compilerArgs.add("-parameters")
}

application {
    mainClass.set("MainKt")
}