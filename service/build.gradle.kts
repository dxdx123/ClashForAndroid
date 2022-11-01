plugins {
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.google.devtools.ksp")
    `maven-publish`
}

dependencies {
    ksp(deps.kaidl.compiler)
    kapt(deps.androidx.room.compiler)

    implementation(project(":core"))
//    implementation("com.github.kr328.clash:core:${extra.get("buildVersionName")}")
    implementation(project(":common"))
//    implementation("com.github.kr328.clash:common:${extra.get("buildVersionName")}")
    implementation(deps.kotlin.coroutine)
    implementation(deps.kotlin.serialization.json)
    implementation(deps.androidx.core)
    implementation(deps.androidx.room.runtime)
    implementation(deps.androidx.room.ktx)
    implementation(deps.kaidl.runtime)
    implementation(deps.rikkax.multiprocess)
}

afterEvaluate {
    android {
        libraryVariants.forEach {
            sourceSets[it.name].java.srcDir(buildDir.resolve("generated/ksp/${it.name}/kotlin"))
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create("release", type = MavenPublication::class) {
                pom {
                    name.set("${project.name}")
                    description.set("${project.name}")
                    url.set("https://github.com/Kr328/ClashForAndroid")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("http://www.opensource.org/licenses/mit-license.php")
                        }
                    }
                    developers {
                        developer {
                            id.set("kr328")
                            name.set("Kr328")
                            email.set("kr328app@outlook.com")
                        }
                    }
                }

                from(components["fossRelease"])
                artifactId = "${project.name}"
                groupId = "com.github.kr328.clash"
                version = "${extra.get("buildVersionName")}"
            }
        }

        repositories {
            maven {
                name = "project local repo "
                url = uri("${rootProject.buildDir}/../release")
            }
        }
    }
}
