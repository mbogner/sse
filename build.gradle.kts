import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.7" apply (false)
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply (false)
    kotlin("jvm") version "1.6.21" apply (false)
    kotlin("plugin.spring") version "1.6.21" apply (false)
}

val javaVersionStr: String by project
val gradleVersionProperty: String by project

allprojects {
    group = "dev.mbo"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    apply(plugin = "io.spring.dependency-management")
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
        resolutionStrategy {
            cacheChangingModulesFor(0, "seconds")
        }
        dependencies {

        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersionStr
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.withType<Wrapper> {
    // https://gradle.org/releases/
    gradleVersion = gradleVersionProperty
    distributionType = Wrapper.DistributionType.ALL
}
