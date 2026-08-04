package com.hideclan;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class HideClanClient implements ClientModInitializer {

    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hideclan.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "key.categories.hideclan"
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

                    // true = manda na action bar (acima da hotbar), não polui o chat
                    client.player.sendMessage(message, true);
                }
            }
        });
    }
}
