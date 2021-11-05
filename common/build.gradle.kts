plugins {
    kotlin("android")
    id("com.android.library")
    `maven-publish`
}

dependencies {
    compileOnly(project(":hideapi"))
//    compileOnly("com.github.kr328.clash:hideapi:${extra.get("buildVersionName")}")
    implementation(deps.kotlin.coroutine)
    implementation(deps.androidx.core)
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
