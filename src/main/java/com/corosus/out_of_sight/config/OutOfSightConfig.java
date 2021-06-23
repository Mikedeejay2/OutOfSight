package com.corosus.out_of_sight.config;

import com.corosus.out_of_sight.OutOfSight;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

@Config(modid = OutOfSight.MOD_ID, category = "", name = "Out Of Sight")
public class OutOfSightConfig {
    public static final ConfigOptions config = new ConfigOptions();

    public static class ConfigOptions {
        @Config.Name("Tile Entity")
        @Config.Comment("Tile entity settings")
        public final TileEntity tileEntity = new TileEntity();

        public static class TileEntity {
            @Config.Name("Tile Entity Render Range Enabled")
            @Config.Comment("Whether tile entity range is enabled")
            public boolean enabled = true;

            @Config.Name("Tile Entity Render Range Max")
            @Config.Comment("The maximum range that a tile entity is rendered")
            @Config.RangeDouble(min = 1, max = 30000)
            public double rangeMax = 24;

            @Config.Name("Tile Entity Render Limit Modded Only")
            @Config.Comment("Whether the tile entity render limit should be limited to modded")
            public boolean moddedOnly = true;

            // Internal
            @Config.Ignore
            public double rangeMaxSQ = -1;
        }

        public final Entity entity = new Entity();

        public static class Entity {
            @Config.Name("Entity Render Range Enabled")
            @Config.Comment("Whether entity range is enabled")
            public boolean enabled = true;

            @Config.Name("Entity Render Range Max")
            @Config.Comment("The maximum range that an entity is rendered")
            @Config.RangeDouble(min = 1, max = 30000)
            public double rangeMax = 64;

            @Config.Name("Entity Render Limit Modded Only")
            @Config.Comment("Whether the entity render limit should be limited to modded")
            public boolean moddedOnly = true;

            // Internal
            @Config.Ignore
            public double rangeMaxSQ = -1;
        }

        public final Particle particle = new Particle();

        public static class Particle {
            @Config.Name("Particle Render Range Enabled")
            @Config.Comment("Whether particle range is enabled")
            public boolean enabled = true;

            @Config.Name("Particle Render Range Max")
            @Config.Comment("The maximum range that a particle is rendered")
            @Config.RangeDouble(min = 1, max = 30000)
            public double rangeMax = 64;

            @Config.Name("Particle Render Limit Modded Only")
            @Config.Comment("Whether the particle render limit should be limited to modded")
            public boolean moddedOnly = true;

            // Internal
            @Config.Ignore
            public double rangeMaxSQ = -1;
        }
    }

    @Mod.EventBusSubscriber(modid = OutOfSight.MOD_ID)
    private static class EventHandler {
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(OutOfSight.MOD_ID)) {
                ConfigManager.sync(OutOfSight.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
