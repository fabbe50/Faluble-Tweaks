package com.fabbe50.dynlighthelmet;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
