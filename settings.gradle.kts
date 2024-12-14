pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ABNAMROAssignment"
include(":app")
include(":core:base")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":core:designsystem")
include(":feature:repolist")
include(":feature:repodetail")