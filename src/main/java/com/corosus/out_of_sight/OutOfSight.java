package com.corosus.out_of_sight;

import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


@Mod(
    modid = OutOfSight.MOD_ID,
    name = OutOfSight.MOD_NAME,
    version = OutOfSight.VERSION
)
public class OutOfSight
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "out_of_sight";
    public static final String MOD_NAME = "Out Of Sight";
    public static final String VERSION = "1.0.1";

    @Mod.Instance(MOD_ID)
    public static OutOfSight INSTANCE;

    public static Map<Class<?>, Boolean> cacheClassToModdedState = new HashMap<>();

    public OutOfSight() {

    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        OutOfSightConfig.calcSquares();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    public static Boolean isModded(Class<?> clazz) {
        if (!cacheClassToModdedState.containsKey(clazz)) {
            cacheClassToModdedState.put(clazz, !clazz.getCanonicalName().startsWith("net.minecraft"));
        }
        return cacheClassToModdedState.get(clazz);
    }
}
