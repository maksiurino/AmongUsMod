package com.innersloth.amogus.mixin;

import com.innersloth.amogus.block.custom.FloorBlock;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "onClientCommand", at = @At("HEAD"), cancellable = true)
    private void amogus$blockSprintAndSneak(ClientCommandC2SPacket packet, CallbackInfo ci) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler)(Object) this;
        ServerPlayerEntity player = handler.player;

        BlockPos posUnder = player.getBlockPos().down();

        if (player.getWorld().getBlockState(posUnder).getBlock() instanceof FloorBlock) {
            ClientCommandC2SPacket.Mode mode = packet.getMode();

            if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING ||
                    mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING ||
                    mode == ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY ||
                    mode == ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY) {

                ci.cancel();
                player.sendMessage(Text.of("Stop crouching/sprinting!"));
            }
        }
    }
}
