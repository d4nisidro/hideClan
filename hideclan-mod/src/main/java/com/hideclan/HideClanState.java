package com.hideclan;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Guarda se o "Hide Clan" está ativo e decide se um jogador deve ser
 * escondido do render do cliente.
 *
 * Nesse servidor (Factions Matrix), a facção não usa time de scoreboard —
 * ela aparece como uma tag no NOME DA TAB (lista de jogadores), no formato:
 *   NomeDoJogador [<símbolo><TAG>]
 * onde o símbolo indica o cargo (# líder, + capitão, × membro, - recruta)
 * e <TAG> é o nome curto da facção (ex: ACH, LLL). Pode ter também outra
 * tag de rank/doador ANTES do nome (ex: [HARDEN]), então sempre pegamos o
 * ÚLTIMO colchete do nome, que é o da facção.
 */
public class HideClanState {

    private static final Pattern BRACKET_TAG = Pattern.compile("\\[([^\\[\\]]+)\\]");

    /** true = jogadores da sua facção ficam invisíveis no seu client */
    public static boolean enabled = false;

    public static boolean shouldHide(PlayerEntity player) {
        if (!enabled) {
            return false;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) {
            return false;
        }

        // nunca esconda você mesmo
        if (player == client.player) {
            return false;
        }

        PlayerListEntry myEntry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        PlayerListEntry otherEntry = client.getNetworkHandler().getPlayerListEntry(player.getUuid());

        if (myEntry == null || otherEntry == null) {
            return false;
        }

        String myTag = extractFactionTag(myEntry.getDisplayName());
        String otherTag = extractFactionTag(otherEntry.getDisplayName());

        if (myTag == null || otherTag == null) {
            return false;
        }

        return myTag.equalsIgnoreCase(otherTag);
    }

    /**
     * Extrai a tag da facção do último colchete [ ] do nome da TAB,
     * removendo o primeiro caractere (símbolo de cargo).
     * Ex: "[HARDEN] Ferutii [+LLL]" -> "LLL"
     */
    private static String extractFactionTag(Text displayName) {
        if (displayName == null) {
            return null;
        }

        String raw = displayName.getString();
        Matcher matcher = BRACKET_TAG.matcher(raw);

        String lastGroup = null;
        while (matcher.find()) {
            lastGroup = matcher.group(1);
        }

        if (lastGroup == null || lastGroup.isEmpty()) {
            return null;
        }

        // remove o símbolo de cargo (#, +, ×, -), fica só a tag da facção
        return lastGroup.length() > 1 ? lastGroup.substring(1) : lastGroup;
    }
}
