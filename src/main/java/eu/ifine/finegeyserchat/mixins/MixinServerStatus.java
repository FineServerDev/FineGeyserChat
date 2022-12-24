package eu.ifine.finegeyserchat.mixins;

import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.players.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerStatus.class)
public class MixinServerStatus {

    //修复提示“无法验证聊天信息”
    @Inject(method = "enforcesSecureChat", at = @At("HEAD"), cancellable = true)
    public void onSecureChatCheck(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}