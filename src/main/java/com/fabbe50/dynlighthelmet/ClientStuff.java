package com.fabbe50.dynlighthelmet;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class ClientStuff implements ClientModInitializer {
    public Set<Item> armorItems = new HashSet<>();
    public Set<Item> compassItems = new HashSet<>();
    public Set<Item> clockItems = new HashSet<>();

    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTTED_AMETHYST_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTTED_AMETHYST_BUD_SMALL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTTED_AMETHYST_BUD_MEDIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTTED_AMETHYST_BUD_LARGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTASSIUM_CHLORIDE_FIRE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTASSIUM_CHLORIDE_CAMPFIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTASSIUM_CHLORIDE_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTASSIUM_CHLORIDE_WALL_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DynlightHelmet.POTASSIUM_CHLORIDE_LANTERN, RenderLayer.getCutout());
        for (Block block : DynlightHelmet.COMPRESSED_COBBLED_DEEPSLATE) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_TUFF) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_OAK_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_SPRUCE_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_BIRCH_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_JUNGLE_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_ACACIA_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }
        for (Block block : DynlightHelmet.COMPRESSED_DARK_OAK_SAPLING) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
        }

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier("dynlighthelmet", "particle/kcl_fire_flame"));
        }));
        ParticleFactoryRegistry.getInstance().register(DynlightHelmet.KCL_FIRE_FLAME, FlameParticle.Factory::new);

        FabricLoader.getInstance().getModContainer("dynlighthelmet").ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("dynlighthelmet:animatedcampfire"), modContainer, ResourcePackActivationType.NORMAL);
        });

        armorItems.add(DynlightHelmet.MINING_HELMET_IRON);
        armorItems.add(DynlightHelmet.MINING_HELMET_GOLD);
        armorItems.add(DynlightHelmet.MINING_HELMET_DIAMOND);
        armorItems.add(DynlightHelmet.MINING_HELMET_NETHERITE);
        armorItems.add(DynlightHelmet.MINING_HELMET_TURTLE);
        compassItems.add(Items.COMPASS);
        clockItems.add(Items.CLOCK);
        HudRenderCallback.EVENT.register((matrixStack, delta) ->
                {
                    matrixStack.push();
                    MinecraftClient mc = MinecraftClient.getInstance();
                    ClientPlayerEntity player = MinecraftClient.getInstance().player;
                    ClientWorld world = MinecraftClient.getInstance().world;
                    if (player != null && world != null) {
                        BlockPos playerPos = player.getBlockPos();
                        Direction playerFacing = player.getHorizontalFacing();
                        Direction.AxisDirection playerDirection = playerFacing.getDirection();
                        String pos = StringUtils.capitalize(player.getHorizontalFacing().asString().substring(0, 1)) + " (" + (playerDirection.offset() == -1 ? "-" : "+") + StringUtils.capitalize(playerFacing.getAxis().asString()) + ")" + ": " + playerPos.toShortString();
                        String clock = parseTime(world.getTimeOfDay());
                        boolean flag = false;
                        if (player.getInventory().containsAny(armorItems)) {
                            for (ItemStack stack : player.getArmorItems()) {
                                if (armorItems.contains(stack.getItem())) {
                                    MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, pos, 5, 5, 0xbf6f6f);
                                    MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, clock, 5, 17, 0xbf6f6f);
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            int clockPos = 5;
                            if (player.getInventory().containsAny(compassItems)) {
                                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, pos, 5, 5, 0xbf6f6f);
                                clockPos += 12;
                            }
                            if (player.getInventory().containsAny(clockItems)) {
                                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, clock, 5, clockPos, 0xbf6f6f);
                            }
                        }
                    }
                }
        );
    }

    private String parseTime(long time) {
        long hours = time / 1000 + 6;
        hours %= 24;
        if (hours == 24)
            hours = 0;
        long minutes = (time % 1000) * 60 / 1000;
        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2, mm.length());
        return hours + ":" + mm;
    }
}
