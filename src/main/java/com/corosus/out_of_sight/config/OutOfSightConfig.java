package com.corosus.out_of_sight.config;

import com.corosus.out_of_sight.OutOfSight;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

//@Config(modid = OutOfSight.MOD_ID)
public class OutOfSightConfig
{
//    public static final ConfigOptions config = new ConfigOptions();
//
//    public static class ConfigOptions {
//        @Config.Name("General")
//        @Config.Comment("General mod settings")
//        public General general = new General();
//
//        public static class General {
//            @Config.Name("Tile Entity Render Range Max")
//            @Config.Comment("The maximum range that a tile entity is rendered")
//            @Config.RangeDouble(min = 1, max = 30000)
//            public double tileEntityRenderRangeMax = 24;
//            @Config.Name("Entity Render Range Max")
//            @Config.Comment("The maximum range that an entity is rendered")
//            @Config.RangeDouble(min = 1, max = 30000)
//            public double entityRenderRangeMax = 64;
//
//            @Config.Name("Tile Entity Render Limit Modded Only")
//            @Config.Comment("Whether the tile entity render limit should be limited to modded")
//            public boolean tileEntityRenderLimitModdedOnly = true;
//            @Config.Name("Entity Render Limit Modded Only")
//            @Config.Comment("Whether the entity render limit should be limited to modded")
//            public boolean entityRenderLimitModdedOnly = true;
//        }
//    }
//
//    @Mod.EventBusSubscriber(modid = OutOfSight.MOD_ID)
//    private static class EventHandler {
//        @SubscribeEvent
//        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
//            if(event.getModID().equals(OutOfSight.MOD_ID)) {
//                ConfigManager.sync(OutOfSight.MOD_ID, Config.Type.INSTANCE);
//
//                System.out.printf("Config has been reloaded. New values: \n" +
//                                      "tileEntityRenderRangeMax: %s \n" +
//                                      "entityRenderRangeMax: %s \n" +
//                                      "tileEntityRenderLimitModdedOnly: %s \n" +
//                                      "entityRenderLimitModdedOnly: %s%n",
//                                  OutOfSightConfig.config.general.tileEntityRenderRangeMax,
//                                  OutOfSightConfig.config.general.entityRenderRangeMax,
//                                  OutOfSightConfig.config.general.tileEntityRenderLimitModdedOnly,
//                                  OutOfSightConfig.config.general.entityRenderLimitModdedOnly);
//            }
//        }
//    }

    public static Configuration config;
    public static File configFile;

    public static double tileEntityRenderRangeMax;
    public static double entityRenderRangeMax;

    public static boolean tileEntityRenderLimitModdedOnly;
    public static boolean entityRenderLimitModdedOnly;


    public static void init(File file) {
        config = new Configuration(file);

        String category = "general";
        config.addCustomCategoryComment(category, "General mod settings");
        tileEntityRenderRangeMax = config.getFloat("tileEntityRenderRangeMax", category, 24, 1, 30000, "The maximum range that a tile entity is rendered");
        entityRenderRangeMax = config.getFloat("entityRenderRangeMax", category, 64, 1, 30000, "The maximum range that an entity is rendered");
        tileEntityRenderLimitModdedOnly = config.getBoolean("tileEntityRenderLimitModdedOnly", category, true, "Whether the tile entity render limit should be limited to modded");
        entityRenderLimitModdedOnly = config.getBoolean("entityRenderLimitModdedOnly", category, true, "Whether the entity render limit should be limited to modded");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        configFile = new File(event.getModConfigurationDirectory(), OutOfSight.MOD_ID + ".cfg");
        init(configFile);
    }

    public static void reloadConfig() {
        init(configFile);
    }

//    private static final Builder CLIENT_BUILDER = new Builder();
//
//    public static final CategoryGeneral GENERAL = new CategoryGeneral();
//
//    public static final class CategoryGeneral {
//
//        public final DoubleValue tileEntityRenderRangeMax;
//        public final DoubleValue entityRenderRangeMax;
//
//        public final BooleanValue tileEntityRenderLimitModdedOnly;
//        public final BooleanValue entityRenderLimitModdedOnly;
//
//        private CategoryGeneral() {
//            CLIENT_BUILDER.comment("General mod settings").push("general");
//
//            tileEntityRenderRangeMax = CLIENT_BUILDER
//                    .defineInRange("tileEntityRenderRangeMax", 24, 1D, 30000);
//
//            entityRenderRangeMax = CLIENT_BUILDER
//                    .defineInRange("entityRenderRangeMax", 64, 1D, 30000);
//
//            tileEntityRenderLimitModdedOnly = CLIENT_BUILDER
//                    .define("tileEntityRenderLimitModdedOnly", true);
//
//            entityRenderLimitModdedOnly = CLIENT_BUILDER
//                    .define("entityRenderLimitModdedOnly", true);
//
//            CLIENT_BUILDER.pop();
//        }
//    }
//    public static final ForgeConfigSpec CLIENT_CONFIG = CLIENT_BUILDER.build();
}
