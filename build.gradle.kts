plugins {
    `java-library`
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    implementation("org.bstats:bstats-bukkit:2.2.1")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

group = "me.tofpu"
version = "1.0.0"
description = "MemorizeTheBlock"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveClassifier = ""

        relocate("org.bstats", "io.tofpu.memorizetheblock.libs.bstats")
    }

    build {
        dependsOn.add(shadowJar)
    }

    runServer {
        minecraftVersion("1.8.8")
    }
}
