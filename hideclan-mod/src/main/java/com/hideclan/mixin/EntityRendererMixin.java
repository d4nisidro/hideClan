package com.hideclan.mixin;

import com.hideclan.HideClanState;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Intercepta a checagem que decide se uma entidade deve ser processada
 * para desenho. Se for um jogador da sua facção (mesmo time do scoreboard)
 * com o Hide Clan ativado, forçamos "não renderizar" — isso pula o modelo,
 * armadura, itens na mão e nametag de uma vez só.
 */
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void hideclan$shouldRender(
            Entity entity,
            Frustum frustum,
            double x,
            double y,
            double z,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (entity instanceof PlayerEntity player && HideClanState.shouldHide(player)) {
            cir.setReturnValue(false);
        }
    }
}
