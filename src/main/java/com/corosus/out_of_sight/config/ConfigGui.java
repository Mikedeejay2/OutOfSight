package com.corosus.out_of_sight.config;

import com.corosus.out_of_sight.OutOfSight;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(), OutOfSight.MOD_ID, false, false, "Out Of Sight");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> configElements = new ArrayList<>();

        ConfigCategory generalCategory = OutOfSightConfig.config.getCategory("general");
        configElements.addAll(new ConfigElement(generalCategory).getChildElements());

        return configElements;
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        OutOfSightConfig.config.save();
        OutOfSightConfig.init(OutOfSightConfig.configFile);
        System.out.println(String.format("Config has been reloaded. New values: \n" +
                                             "tileEntityRenderRangeMax: %s \n" +
                                             "entityRenderRangeMax: %s \n" +
                                             "tileEntityRenderLimitModdedOnly: %s \n" +
                                             "entityRenderLimitModdedOnly: %s",
                                         OutOfSightConfig.tileEntityRenderRangeMax,
                                         OutOfSightConfig.entityRenderRangeMax,
                                         OutOfSightConfig.tileEntityRenderLimitModdedOnly,
                                         OutOfSightConfig.entityRenderLimitModdedOnly));
    }


}
