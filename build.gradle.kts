import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.render.TextReportRenderer
import com.github.jk1.license.filter.LicenseBundleNormalizer

// Add buildscript dependency for the Nexus plugin
buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("io.github.gradle-nexus:publish-plugin:2.0.0")
    }
}

plugins {
    kotlin("jvm") version "2.0.10"
    `maven-publish`
    id("com.github.jk1.dependency-license-report") version "2.9"
    signing
}

// Check if this is a standalone project build
if (rootProject.name == "file-system-domain") {
    // Apply the Nexus plugin when built as a standalone project
    apply {
        plugin("io.github.gradle-nexus.publish-plugin")
    }
    
    // Access the extension directly after applying the plugin
    configure<io.github.gradlenexus.publishplugin.NexusPublishExtension> {
        repositories {
            sonatype {
                nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
                snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            }
        }
    }
}

licenseReport {
    renderers = arrayOf(
        TextReportRenderer()
    )
    filters = arrayOf<DependencyFilter>(LicenseBundleNormalizer())
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-api:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}

// Define group and version based on root project or use defaults for standalone
val projectGroup = "software.bevel"
val projectVersion = "1.2.0"

group = projectGroup
version = projectVersion

// Create Javadoc and source jars
java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = projectGroup
            artifactId = "file-system-domain"
            version = projectVersion

            pom {
                name.set("File System Domain")
                description.set("File system domain library for Bevel")
                url.set("https://bevel.software")

                licenses {
                    license {
                        name.set("Mozilla Public License 2.0")
                        url.set("https://www.mozilla.org/en-US/MPL/2.0/")
                    }
                }

                developers {
                    developer {
                        name.set("Razvan-Ion Radulescu")
                        email.set("razvan.radulescu@bevel.software")
                        organization.set("Bevel Software")
                        organizationUrl.set("https://bevel.software")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/Bevel-Software/file-system-domain.git")
                    developerConnection.set("scm:git:ssh://git@github.com:Bevel-Software/file-system-domain.git")
                    url.set("https://github.com/Bevel-Software/file-system-domain")
                }
            }
        }
    }
}

// Configure signing
signing {
    sign(publishing.publications["maven"])
}
