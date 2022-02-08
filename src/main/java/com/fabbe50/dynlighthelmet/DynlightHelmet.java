package com.fabbe50.dynlighthelmet;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynlightHelmet implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");

	//Register Helmet
	public static final Item UPGRADE_KIT = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
	public static final Item MINING_HELMET_IRON = new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_GOLD = new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_DIAMOND = new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_NETHERITE = new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_TURTLE = new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Block POTTED_AMETHYST_CLUSTER = new FlowerPotBlock(Blocks.AMETHYST_CLUSTER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 5));
	public static final Block POTTED_AMETHYST_BUD_SMALL = new FlowerPotBlock(Blocks.SMALL_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 1));
	public static final Block POTTED_AMETHYST_BUD_MEDIUM = new FlowerPotBlock(Blocks.MEDIUM_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 2));
	public static final Block POTTED_AMETHYST_BUD_LARGE = new FlowerPotBlock(Blocks.LARGE_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 4));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "upgrade_kit"), UPGRADE_KIT);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet"), MINING_HELMET_IRON);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_gold"), MINING_HELMET_GOLD);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_diamond"), MINING_HELMET_DIAMOND);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_netherite"), MINING_HELMET_NETHERITE);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_turtle"), MINING_HELMET_TURTLE);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_cluster"), POTTED_AMETHYST_CLUSTER);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_small"), POTTED_AMETHYST_BUD_SMALL);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_medium"), POTTED_AMETHYST_BUD_MEDIUM);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_large"), POTTED_AMETHYST_BUD_LARGE);
	}
}
