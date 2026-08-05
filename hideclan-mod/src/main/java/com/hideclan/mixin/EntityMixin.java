package com.hideclan.mixin;

import com.hideclan.HideClanState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Faz o jogador oculto virar "fantasma" de verdade no seu client:
 * - canHit(): impede que ele seja alvo de ataque/interação (não dá pra hitar).
 * - collidesWith(Entity): remove a colisão física com ele (dá pra atravessar
 *   e colocar bloco onde ele está, como se não tivesse ninguém ali).
 *
 * Isso é 100% client-side e cosmético: no servidor, pra todo mundo, o
 * jogador continua normal, sólido e ocupando espaço.
 */
@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void hideclan$canHit(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;
        if (self instanceof PlayerEntity player && HideClanState.shouldHide(player)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "collidesWith", at = @At("HEAD"), cancellable = true)
    private void hideclan$collidesWith(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof PlayerEntity player && HideClanState.shouldHide(player)) {
            cir.setReturnValue(false);
        }
    }
}
