plugins {
    id("neoforge-mod-conventions")
    id("neoforge-config-conventions")
}

// Mod Dependencies
dependencies {
    // JEI 19.64.0.455 (Release) for NeoForge 1.21.1; development runtime only.
    localRuntime("curse.maven:jei-238222:9205121")
    // MezzConfig 0.6.9 (Beta), required by JEI; development runtime only.
    localRuntime("curse.maven:mezzconfig-1689768:9318719")
}
