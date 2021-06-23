package com.corosus.out_of_sight.config;

import com.corosus.out_of_sight.OutOfSight;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class OutOfSightConfig {
    public static Configuration config;
    public static File configFile;

    public static boolean tileEntityRenderRangeEnabled;
    public static double tileEntityRenderRangeMax;
    public static boolean tileEntityRenderLimitModdedOnly;
    public static double tileEntityRenderRangeMaxSq;

    public static boolean entityRenderRangeEnabled;
    public static double entityRenderRangeMax;
    public static boolean entityRenderLimitModdedOnly;
    public static double entityRenderRangeMaxSq;

    public static boolean particleRenderRangeEnabled;
    public static double particleRenderRangeMax;
    public static boolean particleRenderLimitModdedOnly;
    public static double particleRenderRangeMaxSq;

    public static void init(File file) {
        config = new Configuration(file);

        String category = "general";
        config.addCustomCategoryComment(category, "General mod settings");

        tileEntityRenderRangeEnabled = config.getBoolean(
            "tileEntityRenderRangeEnabled", category, true,
            "Whether tile entity range is enabled");
        tileEntityRenderRangeMax = config.getFloat(
            "tileEntityRenderRangeMax", category, 24, 1, 30000,
            "The maximum range that a tile entity is rendered");
        tileEntityRenderLimitModdedOnly = config.getBoolean(
            "tileEntityRenderLimitModdedOnly", category, true,
            "Whether the tile entity render limit should be limited to modded");
        tileEntityRenderRangeMaxSq = tileEntityRenderRangeMax * tileEntityRenderRangeMax;

        entityRenderRangeEnabled = config.getBoolean(
            "entityRenderRangeEnabled", category, true,
            "Whether entity range is enabled");
        entityRenderRangeMax = config.getFloat(
            "entityRenderRangeMax", category, 64, 1, 30000,
            "The maximum range that an entity is rendered");
        entityRenderLimitModdedOnly = config.getBoolean(
            "entityRenderLimitModdedOnly", category, true,
            "Whether the entity render limit should be limited to modded");
        entityRenderRangeMaxSq = entityRenderRangeMax * entityRenderRangeMax;

        particleRenderRangeEnabled = config.getBoolean(
            "particleRenderRangeEnabled", category, false,
            "Whether particle range is enabled");
        particleRenderRangeMax = config.getFloat(
            "particleRenderRangeMax", category, 24, 1, 30000,
            "The maximum range that a particle is rendered");
        particleRenderLimitModdedOnly = config.getBoolean(
            "particleRenderLimitModdedOnly", category, true,
            "Whether the particle render limit should be limited to modded");
        particleRenderRangeMaxSq = particleRenderRangeMax * particleRenderRangeMax;

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        configFile = new File(event.getModConfigurationDirectory(), OutOfSight.MOD_ID + ".cfg");
        init(configFile);
    }

    public static void reloadConfig() {
        init(configFile);
    }
}
