plugins {
    `java-library`
    `maven-publish`
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

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
