import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.render.TextReportRenderer
import com.github.jk1.license.filter.LicenseBundleNormalizer

plugins {
    kotlin("jvm") version "2.0.10"
    `maven-publish`
    id("com.github.jk1.dependency-license-report") version "2.9"
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
val projectVersion = "1.0.0"

group = projectGroup
version = projectVersion

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
    
    // Configure repository for Maven publishing
    repositories {
        maven {
            name = "ProjectRepository"
            url = uri(layout.buildDirectory.dir("repo"))
            // For actual remote repositories, uncomment and configure these:
            // url = uri("https://your.maven.repo/releases")
            // credentials {
            //     username = findProperty("mavenUsername") as String?
            //     password = findProperty("mavenPassword") as String?
            // }
        }
    }
}