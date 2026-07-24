import net.neoforged.moddevgradle.legacyforge.dsl.LegacyForgeExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.language.jvm.tasks.ProcessResources
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    idea
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "2.3.20"
    id("net.neoforged.moddev.legacyforge") version "2.0.91"
}

val modId = providers.gradleProperty("mod_id").get()
val minecraftVersionValue = providers.gradleProperty("minecraft_version").get()
val forgeVersionValue = providers.gradleProperty("forge_version").get()
val kffJar = layout.projectDirectory.file("lib/kotlinforforge-4.12.0-all.jar")

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("mod_group_id").get()

base {
    archivesName.set(modId)
}

repositories {
    mavenLocal()
    flatDir { dirs("lib") }
    exclusiveContent {
        forRepository {
            maven { url = uri("https://maven.tterrag.com/") }
        }
        filter { includeGroup("com.tterrag.registrate") }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

kotlin {
    jvmToolchain(17)
}

extensions.configure<LegacyForgeExtension> {
    version = "$minecraftVersionValue-$forgeVersionValue"

    parchment {
        mappingsVersion = providers.gradleProperty("parchment_mappings_version").get()
        minecraftVersion = providers.gradleProperty("parchment_minecraft_version").get()
    }

    runs {
        register("client") {
            client()
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
        register("data") {
            data()
            programArguments.addAll(
                "--mod", modId,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.named("main") {
    resources.srcDir("src/generated/resources")
}

val modLocalRuntime = configurations.maybeCreate("modLocalRuntime")
val localRuntimeMods = fileTree("lib") {
    include("*.jar")
    exclude(kffJar.asFile.name)
}
configurations.named("runtimeClasspath") {
    extendsFrom(modLocalRuntime)
}
configurations.named("additionalRuntimeClasspath") {
    extendsFrom(modLocalRuntime)
}

dependencies {
    // Registrate is used by registry declarations and its data generators.
    jarJar(modApi(libs.registrate.get())!!)

    // KFF is a required local mod dependency. The flatDir repository keeps this
    // local-only and modImplementation keeps it outside the generated jar.
    modImplementation("local:kotlinforforge:4.12.0-all")

    // Every other jar in lib is available to runClient, never embedded in our jar.
    add("modLocalRuntime", localRuntimeMods)
}

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to providers.gradleProperty("minecraft_version").get(),
        "minecraft_version_range" to providers.gradleProperty("minecraft_version_range").get(),
        "forge_version" to providers.gradleProperty("forge_version").get(),
        "forge_version_range" to providers.gradleProperty("forge_version_range").get(),
        "loader_version_range" to providers.gradleProperty("loader_version_range").get(),
        "mod_id" to modId,
        "mod_name" to providers.gradleProperty("mod_name").get(),
        "mod_license" to providers.gradleProperty("mod_license").get(),
        "mod_version" to providers.gradleProperty("mod_version").get(),
        "mod_authors" to providers.gradleProperty("mod_authors").get(),
        "mod_description" to providers.gradleProperty("mod_description").get()
    )

    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into(layout.buildDirectory.dir("generated/sources/modMetadata"))
}

sourceSets.named("main") {
    resources.srcDir(generateModMetadata)
}

extensions.configure<LegacyForgeExtension> {
    ideSyncTask(generateModMetadata)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven { url = uri(layout.projectDirectory.dir("repo")) }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
