plugins {
    id("neoforge-mod-conventions")
    id("neoforge-config-conventions")
}

// Mod Dependencies
dependencies {
    // JEI 29.47.0.110 (Release) for NeoForge 26.1.2; development runtime only.
    localRuntime("curse.maven:jei-238222:9193052")
    // MezzConfig 0.6.9 (Beta), required by JEI; development runtime only.
    localRuntime("curse.maven:mezzconfig-1689768:9318723")
}
