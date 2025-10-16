package com.innersloth.amogus.mixin;

import com.innersloth.amogus.block.custom.BenchBlock;
import com.innersloth.amogus.block.custom.EmergencyMeetingButtonBlock;
import com.innersloth.amogus.block.custom.FloorBlock;
import com.innersloth.amogus.block.custom.TableBlock;
import net.minecraft.block.Block;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.innersloth.amogus.AmongUsOptions;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "onClientCommand", at = @At("HEAD"), cancellable = true)
    private void amogus$blockSprintAndSneak(ClientCommandC2SPacket packet, CallbackInfo ci) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler)(Object) this;
        ServerPlayerEntity player = handler.player;

        BlockPos posUnder = player.getBlockPos().down();

        Block blockUnder = player.getWorld().getBlockState(posUnder).getBlock();

        if (blockUnder instanceof FloorBlock || blockUnder instanceof TableBlock || blockUnder instanceof EmergencyMeetingButtonBlock || blockUnder instanceof BenchBlock) {
            ClientCommandC2SPacket.Mode mode = packet.getMode();

            if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING ||
                    mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING ||
                    mode == ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY ||
                    mode == ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY) {

                ci.cancel();
                if (AmongUsOptions.showDisabledRunningMessages) {
                    player.sendMessage(Text.of("Stop sneaking/sprinting!"));
                }
            }
        }
    }
}
