package com.hideclan;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;

/**
 * Guarda se o "Hide Clan" está ativo e decide se um jogador deve ser
 * escondido do render do cliente.
 *
 * A maioria dos plugins de Factions (ex: FactionsUUID, SavageFactions,
 * FactionsX) coloca cada facção como um TIME do scoreboard do Minecraft.
 * Por isso, aqui consideramos "da sua facção" = está no mesmo time
 * (client.player.getScoreboardTeam()) que você.
 */
public class HideClanState {

    /** true = jogadores da sua facção ficam invisíveis no seu client */
    public static boolean enabled = false;

    public static boolean shouldHide(PlayerEntity player) {
        if (!enabled) {
            return false;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return false;
        }

        // nunca esconda você mesmo
        if (player == client.player) {
            return false;
        }

        AbstractTeam myTeam = client.player.getScoreboardTeam();
        AbstractTeam otherTeam = player.getScoreboardTeam();

        if (myTeam == null || otherTeam == null) {
            return false;
        }

        return myTeam.getName().equals(otherTeam.getName());
    }
}
