package com.corosus.out_of_sight;

import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;


@Mod(
    modid = OutOfSight.MOD_ID,
    name = OutOfSight.MOD_NAME,
    version = OutOfSight.VERSION,
    guiFactory = "com.corosus.out_of_sight.config.ConfigGuiFactory"
)
public class OutOfSight
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "out_of_sight";
    public static final String MOD_NAME = "Out Of Sight";
    public static final String VERSION = "1.0.1";

    @Mod.Instance(MOD_ID)
    public static OutOfSight INSTANCE;

    public static HashMap<Class<?>, String> cacheClassToCanonicalName = new HashMap<>();

    public OutOfSight() {

    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        OutOfSightConfig.registerConfig(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    public static String getCanonicalNameCached(Class<?> clazz) {
        if (!cacheClassToCanonicalName.containsKey(clazz)) {
            cacheClassToCanonicalName.put(clazz, clazz.getCanonicalName());
        }
        return cacheClassToCanonicalName.get(clazz);
    }

    public static boolean isModded(Class<?> clazz) {
        return OutOfSight.getCanonicalNameCached(clazz).startsWith("net.minecraft");
    }
}
