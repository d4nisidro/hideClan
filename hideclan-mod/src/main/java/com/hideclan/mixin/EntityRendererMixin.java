package com.hideclan.mixin;

import com.hideclan.HideClanState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Intercepta o render de QUALQUER entidade. Filtramos para só agir em
 * PlayerEntity e, se for da sua facção (mesmo time do scoreboard) com o
 * Hide Clan ativado, cancelamos o render — isso esconde o modelo, a
 * armadura, os itens na mão e o nametag de uma vez só.
 */
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void hideclan$onRender(
            Entity entity,
            float yaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci
    ) {
        if (entity instanceof PlayerEntity player && HideClanState.shouldHide(player)) {
            ci.cancel();
        }
    }
}
