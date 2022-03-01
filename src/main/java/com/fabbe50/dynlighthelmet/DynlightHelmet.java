package com.fabbe50.dynlighthelmet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class DynlightHelmet implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");

	//Register ItemGroup
	public static final ItemGroup COMPRESSED_BLOCKS = FabricItemGroupBuilder.build(new Identifier("dynlighthelmet", "compressedblocks"), () -> new ItemStack(Items.COBBLESTONE));

	//Initialize Particle Types
	public static final DefaultParticleType KCL_FIRE_FLAME = FabricParticleTypes.simple();

	//Initialize Block
	public static final Block POTTED_AMETHYST_CLUSTER = new FlowerPotBlock(Blocks.AMETHYST_CLUSTER, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 5));
	public static final Block POTTED_AMETHYST_BUD_SMALL = new FlowerPotBlock(Blocks.SMALL_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 1));
	public static final Block POTTED_AMETHYST_BUD_MEDIUM = new FlowerPotBlock(Blocks.MEDIUM_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 2));
	public static final Block POTTED_AMETHYST_BUD_LARGE = new FlowerPotBlock(Blocks.LARGE_AMETHYST_BUD, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque().luminance(state -> 4));
	public static final Block POTASSIUM_CHLORIDE_FIRE_BLOCK = new PotassiumChlorideFireBlock(AbstractBlock.Settings.of(Material.FIRE).noCollision().breakInstantly().luminance(state -> 11).sounds(BlockSoundGroup.WOOL));
	public static final Block POTASSIUM_CHLORIDE_CAMPFIRE = new CampfireBlock(false, 3, AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(11)).nonOpaque());
	public static final Block POTASSIUM_CHLORIDE_TORCH = new ColoredTorchBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> 11).sounds(BlockSoundGroup.WOOD), KCL_FIRE_FLAME);
	public static final Block POTASSIUM_CHLORIDE_WALL_TORCH = new ColoredWallTorchBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> 11).sounds(BlockSoundGroup.WOOD).dropsLike(POTASSIUM_CHLORIDE_TORCH), KCL_FIRE_FLAME);
	public static final Block POTASSIUM_CHLORIDE_LANTERN = new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5f).sounds(BlockSoundGroup.LANTERN).luminance(state -> 11).nonOpaque());

	//Initialize Item
	public static final Item UPGRADE_KIT = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
	public static final Item MINING_HELMET_IRON = new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_GOLD = new ArmorItem(ArmorMaterials.GOLD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_DIAMOND = new ArmorItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_NETHERITE = new ArmorItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item MINING_HELMET_TURTLE = new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item POTASSIUM_CHLORIDE_CAMPFIRE_ITEM = new BlockItem(POTASSIUM_CHLORIDE_CAMPFIRE, new Item.Settings().group(ItemGroup.DECORATIONS));
	public static final Item POTASSIUM_CHLORIDE_TORCH_ITEM = new WallStandingBlockItem(POTASSIUM_CHLORIDE_TORCH, POTASSIUM_CHLORIDE_WALL_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS));
	public static final Item POTASSIUM_CHLORIDE_LANTERN_ITEM = new BlockItem(POTASSIUM_CHLORIDE_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));

	//Initialize Compressed Blocks
	public static final Block[] COMPRESSED_COBBLED_DEEPSLATE = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE));
	public static final Block[] COMPRESSED_TUFF = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.TUFF));

	//Initialize Compression Barrels
	public static final Block[] COMPRESSED_OAK_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));
	public static final Block[] COMPRESSED_SPRUCE_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));
	public static final Block[] COMPRESSED_BIRCH_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));
	public static final Block[] COMPRESSED_JUNGLE_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));
	public static final Block[] COMPRESSED_ACACIA_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));
	public static final Block[] COMPRESSED_DARK_OAK_SAPLING = createCompressedBlock(AbstractBlock.Settings.copy(Blocks.BARREL));

	//Register Tags
	public static final Tag<Block> POTASSIUM_CHLORIDE_FIRE_BLOCKS = TagFactory.BLOCK.create(new Identifier("dynlighthelmet", "potassium_chloride_base_blocks"));

	@Override
	public void onInitialize() {
		//Register Particle Type
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("dynlighthelmet", "kcl_fire_flame"), KCL_FIRE_FLAME);

		//Register Blocks
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_cluster"), POTTED_AMETHYST_CLUSTER);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_small"), POTTED_AMETHYST_BUD_SMALL);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_medium"), POTTED_AMETHYST_BUD_MEDIUM);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potted_amethyst_bud_large"), POTTED_AMETHYST_BUD_LARGE);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potassium_chloride_fire"), POTASSIUM_CHLORIDE_FIRE_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potassium_chloride_campfire"), POTASSIUM_CHLORIDE_CAMPFIRE);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potassium_chloride_torch"), POTASSIUM_CHLORIDE_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potassium_chloride_wall_torch"), POTASSIUM_CHLORIDE_WALL_TORCH);
		Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", "potassium_chloride_lantern"), POTASSIUM_CHLORIDE_LANTERN);

		//Register Items
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "upgrade_kit"), UPGRADE_KIT);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet"), MINING_HELMET_IRON);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_gold"), MINING_HELMET_GOLD);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_diamond"), MINING_HELMET_DIAMOND);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_netherite"), MINING_HELMET_NETHERITE);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "mining_helmet_turtle"), MINING_HELMET_TURTLE);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "potassium_chloride_campfire"), POTASSIUM_CHLORIDE_CAMPFIRE_ITEM);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "potassium_chloride_torch"), POTASSIUM_CHLORIDE_TORCH_ITEM);
		Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", "potassium_chloride_lantern"), POTASSIUM_CHLORIDE_LANTERN_ITEM);

		//Register Compressed Blocks
		registerCompressedBlock("compressed_cobbled_deepslate", COMPRESSED_COBBLED_DEEPSLATE);
		registerCompressedBlock("compressed_tuff", COMPRESSED_TUFF);
		registerCompressedBlock("compressed_oak_sapling", COMPRESSED_OAK_SAPLING);
		registerCompressedBlock("compressed_spruce_sapling", COMPRESSED_SPRUCE_SAPLING);
		registerCompressedBlock("compressed_birch_sapling", COMPRESSED_BIRCH_SAPLING);
		registerCompressedBlock("compressed_jungle_sapling", COMPRESSED_JUNGLE_SAPLING);
		registerCompressedBlock("compressed_acacia_sapling", COMPRESSED_ACACIA_SAPLING);
		registerCompressedBlock("compressed_dark_oak_sapling", COMPRESSED_DARK_OAK_SAPLING);

		//Moss, Wood, Crate with Saplings0

		//Register Callback
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (player.getMainHandStack().getItem().equals(Items.FLINT_AND_STEEL)) {
				if (hitResult.getSide().equals(Direction.UP)) {
					if (world.getBlockState(hitResult.getBlockPos()).isIn(DynlightHelmet.POTASSIUM_CHLORIDE_FIRE_BLOCKS)) {
						world.setBlockState(hitResult.getBlockPos().up(), DynlightHelmet.POTASSIUM_CHLORIDE_FIRE_BLOCK.getDefaultState());
						return ActionResult.SUCCESS;
					}
				}
			}
			return ActionResult.PASS;
		});
	}

	public static Block[] createCompressedBlock(AbstractBlock.Settings settings) {
		List<Block> blocks = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			blocks.add(new Block(settings));
		}
		return blocks.toArray(new Block[0]);
	}

	public static void registerCompressedBlock(String name, Block[] blocks) {
		int i = 0;
		for (Block block : blocks) {
			Registry.register(Registry.BLOCK, new Identifier("dynlighthelmet", name + "_" + i), block);
			Registry.register(Registry.ITEM, new Identifier("dynlighthelmet", name + "_" + i), new BlockItem(block, new Item.Settings().group(COMPRESSED_BLOCKS)));
			i++;
		}
	}

	private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return state -> state.get(Properties.LIT) ? litLevel : 0;
	}

	public enum MiningHelmetArmorMaterial implements ArmorMaterial {
		IRON("iron_mining_helmet", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.IRON_INGOT)),
		GOLD("gold_mining_helmet", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.GOLD_INGOT)),
		DIAMOND("diamond_mining_helmet", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f, () -> Ingredient.ofItems(Items.DIAMOND)),
		TURTLE("turtle_mining_helmet", 25, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.SCUTE)),
		NETHERITE("netherite_mining_helmet", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, () -> Ingredient.ofItems(Items.NETHERITE_INGOT));

		private static final int[] BASE_DURABILITY;
		private final String name;
		private final int durabilityMultiplier;
		private final int[] protectionAmounts;
		private final int enchantability;
		private final SoundEvent equipSound;
		private final float toughness;
		private final float knockbackResistance;
		private final Lazy<Ingredient> repairIngredientSupplier;

		private MiningHelmetArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
			this.name = name;
			this.durabilityMultiplier = durabilityMultiplier;
			this.protectionAmounts = protectionAmounts;
			this.enchantability = enchantability;
			this.equipSound = equipSound;
			this.toughness = toughness;
			this.knockbackResistance = knockbackResistance;
			this.repairIngredientSupplier = new Lazy<Ingredient>(repairIngredientSupplier);
		}

		@Override
		public int getDurability(EquipmentSlot slot) {
			return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
		}

		@Override
		public int getProtectionAmount(EquipmentSlot slot) {
			return this.protectionAmounts[slot.getEntitySlotId()];
		}

		@Override
		public int getEnchantability() {
			return this.enchantability;
		}

		@Override
		public SoundEvent getEquipSound() {
			return this.equipSound;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairIngredientSupplier.get();
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public float getToughness() {
			return this.toughness;
		}

		@Override
		public float getKnockbackResistance() {
			return this.knockbackResistance;
		}

		static {
			BASE_DURABILITY = new int[]{13, 15, 16, 11};
		}
	}
}
