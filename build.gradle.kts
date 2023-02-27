/*
 * Copyright 2023 Mihir Paldhikar
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the “Software”), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
}

group = "com.mihirpaldhikar"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        localImageName.set("proxima")
    }
}


repositories {
    mavenCentral()
}

dependencies {
    // Core
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-caching-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-compression-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")

    // Koin
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    // JSoup
    implementation("org.jsoup:jsoup:1.15.4")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("commons-validator:commons-validator:1.7")

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}