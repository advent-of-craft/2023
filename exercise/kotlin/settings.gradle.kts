pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "advent-of-craft"
include(
    "day01",
    "day02",
    "day03",
    "day04",
    "day05",
    "day06",
    "day07",
    "day08",
    "day09",
    "day10",
    "day11",
    "day12",
    "day13",
    "day14",
    "day15",
    "day16",
    "day17",
    "day18",
    "day19",
    "day20",
    "day21",
    "day22",
    "day23",
    "day24"
)
project(":day01").name = "edible"
project(":day02").name = "fizzbuzz"
project(":day03").name = "people"
project(":day04").name = "blog"
project(":day05").name = "people-part2"
project(":day06").name = "fizzbuzz-part2"
project(":day07").name = "pipeline"
project(":day08").name = "password-validation"
project(":day09").name = "accountability"
project(":day10").name = "fizzbuzz-part3"
project(":day11").name = "roman"
project(":day12").name = "greeter"
project(":day13").name = "blog-part2"
project(":day14").name = "fizzbuzz-part4"
project(":day15").name = "documents"
project(":day16").name = "blog-part3"
project(":day17").name = "fizzbuzz-part5"
project(":day18").name = "lap"
project(":day19").name = "blog-part4"
project(":day20").name = "yahtzee"
project(":day21").name = "audit"
project(":day22").name = "diamond"
project(":day23").name = "trip-service"
project(":day24").name = "dive"