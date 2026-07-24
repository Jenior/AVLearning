pluginManagement {
    repositories {

        maven {
            credentials {

                username = providers.gradleProperty("nexusUsername").get()
                password = providers.gradleProperty("nexusPassword").get()

            }
            url =uri( providers.gradleProperty("mavenUrl").get())
        }
//        google {
//            content {
//                includeGroupByRegex("com\\.android.*")
//                includeGroupByRegex("com\\.google.*")
//                includeGroupByRegex("androidx.*")
//            }
//        }
//        mavenCentral()
//        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        maven {
            credentials {

                username = providers.gradleProperty("nexusUsername").get()
                password = providers.gradleProperty("nexusPassword").get()

            }
            url =uri( providers.gradleProperty("mavenUrl").get())
        }
    }
}

rootProject.name = "AVLearning"
include(":app")

include(":lessons:lesson01-binary")
include(":lessons:lesson02-bmp")
include(":lessons:lesson04-wav")
include(":lessons:lesson05-pcm")
