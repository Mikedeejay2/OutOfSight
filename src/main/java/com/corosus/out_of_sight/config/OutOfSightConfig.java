package com.corosus.out_of_sight.config;

import com.corosus.out_of_sight.OutOfSight;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class OutOfSightConfig {
    public static Configuration config;
    public static File configFile;

    public static double tileEntityRenderRangeMax;
    public static double entityRenderRangeMax;
    public static double particleRenderRangeMax;

    public static boolean tileEntityRenderLimitModdedOnly;
    public static boolean entityRenderLimitModdedOnly;
    public static boolean particleRenderLimitModdedOnly;

    public static double tileEntityRenderRangeMaxSq;
    public static double entityRenderRangeMaxSq;
    public static double particleRenderRangeMaxSq;


    public static void init(File file) {
        config = new Configuration(file);

        String category = "general";
        config.addCustomCategoryComment(category, "General mod settings");
        tileEntityRenderRangeMax = config.getFloat(
            "tileEntityRenderRangeMax", category, 24, 1, 30000,
            "The maximum range that a tile entity is rendered");
        entityRenderRangeMax = config.getFloat(
            "entityRenderRangeMax", category, 64, 1, 30000,
            "The maximum range that an entity is rendered");
        particleRenderRangeMax = config.getFloat(
            "particleRenderRangeMax", category, 24, 1, 30000,
            "The maximum range that a particle is rendered");

        tileEntityRenderLimitModdedOnly = config.getBoolean(
            "tileEntityRenderLimitModdedOnly", category, true,
            "Whether the tile entity render limit should be limited to modded");
        entityRenderLimitModdedOnly = config.getBoolean(
            "entityRenderLimitModdedOnly", category, true,
            "Whether the entity render limit should be limited to modded");
        particleRenderLimitModdedOnly = config.getBoolean(
            "particleRenderLimitModdedOnly", category, true,
            "Whether the particle render limit should be limited to modded");

        tileEntityRenderRangeMaxSq = tileEntityRenderRangeMax * tileEntityRenderRangeMax;
        entityRenderRangeMaxSq = entityRenderRangeMax * entityRenderRangeMax;
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
