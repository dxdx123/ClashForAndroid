plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    `maven-publish`
}

dependencies {
    implementation(project(":core"))
//    implementation("com.github.kr328.clash:core:${extra.get("buildVersionName")}")
    implementation(project(":common"))
//    implementation("com.github.kr328.clash:common:${extra.get("buildVersionName")}")
    implementation(project(":service"))
//    implementation("com.github.kr328.clash:service:${extra.get("buildVersionName")}")

    implementation(deps.kotlin.coroutine)
    implementation(deps.androidx.core)
    implementation(deps.androidx.appcompat)
    implementation(deps.androidx.activity)
    implementation(deps.androidx.coordinator)
    implementation(deps.androidx.recyclerview)
    implementation(deps.androidx.fragment)
    implementation(deps.androidx.viewpager)
    implementation(deps.google.material)
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
