package com.innersloth.amogus.client;

import com.innersloth.amogus.block.custom.FloorBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeybindBlockHandler implements ClientModInitializer {
    private InputUtil.Key prevSprintKey = null;
    private InputUtil.Key prevSneakKey = null;
    private boolean keysOverwritten = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tryHandleTick(client);
        });
    }

    private void tryHandleTick(MinecraftClient client) {
        if (client == null) return;

        if (client.player == null || client.world == null) {
            if (keysOverwritten) restoreKeys(client);
            return;
        }

        World world = client.world;
        BlockPos posUnder = client.player.getBlockPos().down();

        boolean standingOnFloorBlock = world.getBlockState(posUnder).getBlock() instanceof FloorBlock;

        KeyBinding sprint = client.options.sprintKey;
        KeyBinding sneak = client.options.sneakKey;

        if (standingOnFloorBlock) {
            if (!keysOverwritten) {
                prevSprintKey = sprint.getDefaultKey();
                prevSneakKey = sneak.getDefaultKey();

                sprint.setBoundKey(InputUtil.UNKNOWN_KEY);
                sneak.setBoundKey(InputUtil.UNKNOWN_KEY);

                keysOverwritten = true;
            } else {
                // dont do anything
            }
        } else {
            if (keysOverwritten) {
                restoreKeys(client);
            }
        }
    }

    private void restoreKeys(MinecraftClient client) {
        if (client == null) return;

        KeyBinding sprint = client.options.sprintKey;
        KeyBinding sneak = client.options.sneakKey;

        if (prevSprintKey != null) sprint.setBoundKey(prevSprintKey);
        if (prevSneakKey != null) sneak.setBoundKey(prevSneakKey);

        prevSprintKey = null;
        prevSneakKey = null;
        keysOverwritten = false;
    }
}
