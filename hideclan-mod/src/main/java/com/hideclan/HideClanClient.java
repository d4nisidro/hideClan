package com.hideclan;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class HideClanClient implements ClientModInitializer {

    private static final KeyBinding.Category HIDECLAN_CATEGORY =
            KeyBinding.Category.create(Identifier.of("hideclan", "main"));

    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hideclan.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                HIDECLAN_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                HideClanState.enabled = !HideClanState.enabled;

                if (client.player != null) {
                    Text message = Text.literal("[HideClan] ")
                            .formatted(Formatting.GOLD)
                            .append(Text.literal(
                                    HideClanState.enabled
                                            ? "Membros da facção OCULTOS."
                                            : "Membros da facção VISÍVEIS."
                            ).formatted(HideClanState.enabled ? Formatting.RED : Formatting.GREEN));

                    client.player.sendMessage(message, true);

                    // --- DEBUG TEMPORÁRIO ---
                    if (client.world != null && client.getNetworkHandler() != null) {
                        for (PlayerEntity p : client.world.getPlayers()) {
                            String entityName = p.getName().getString();
                            String customName = (p.getCustomName() != null) ? p.getCustomName().getString() : "sem custom name";

                            String tabName = "sem tab entry";
                            PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(p.getUuid());
                            if (entry != null && entry.getDisplayName() != null) {
                                tabName = entry.getDisplayName().getString();
                            }

                            String tag = (p == client.player) ? " (VOCÊ)" : "";

                            client.player.sendMessage(Text.literal(
                                    "[DEBUG]" + tag + " entidade=\"" + entityName
                                            + "\" customName=\"" + customName
                                            + "\" tab=\"" + tabName + "\""
                            ).formatted(Formatting.YELLOW), false);
                        }
                    }
                    // --- FIM DEBUG ---
                }
            }
        });
    }
}
