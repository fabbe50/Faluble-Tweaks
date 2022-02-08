package com.fabbe50.dynlighthelmet;

import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import net.minecraft.util.Identifier;

public class DynlightInit implements DynamicLightsInitializer {
    //Register Dynamic Light
    public static final ItemLightSource LIGHT_SOURCE_MINING_HELMET_IRON = new ItemLightSource.StaticItemLightSource(new Identifier("dynlighthelmet", "mining_helmet"), DynlightHelmet.MINING_HELMET_IRON, 11);
    public static final ItemLightSource LIGHT_SOURCE_MINING_HELMET_GOLD = new ItemLightSource.StaticItemLightSource(new Identifier("dynlighthelmet", "mining_helmet_gold"), DynlightHelmet.MINING_HELMET_GOLD, 15);
    public static final ItemLightSource LIGHT_SOURCE_MINING_HELMET_DIAMOND = new ItemLightSource.StaticItemLightSource(new Identifier("dynlighthelmet", "mining_helmet_diamond"), DynlightHelmet.MINING_HELMET_DIAMOND, 13);
    public static final ItemLightSource LIGHT_SOURCE_MINING_HELMET_NETHERITE = new ItemLightSource.StaticItemLightSource(new Identifier("dynlighthelmet", "mining_helmet_netherite"), DynlightHelmet.MINING_HELMET_NETHERITE, 15);
    public static final ItemLightSource LIGHT_SOURCE_MINING_HELMET_TURTLE = new ItemLightSource.StaticItemLightSource(new Identifier("dynlighthelmet", "mining_helmet_turtle"), DynlightHelmet.MINING_HELMET_TURTLE, 13);

    @Override
    public void onInitializeDynamicLights() {
        ItemLightSources.registerItemLightSource(LIGHT_SOURCE_MINING_HELMET_IRON);
        ItemLightSources.registerItemLightSource(LIGHT_SOURCE_MINING_HELMET_GOLD);
        ItemLightSources.registerItemLightSource(LIGHT_SOURCE_MINING_HELMET_DIAMOND);
        ItemLightSources.registerItemLightSource(LIGHT_SOURCE_MINING_HELMET_NETHERITE);
        ItemLightSources.registerItemLightSource(LIGHT_SOURCE_MINING_HELMET_TURTLE);
    }
}
