package net.deadlydiamond98.familiar_friends.init.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CompanionKeybindings {
    public static final KeyBinding COMPANION_ACTION = register("action", GLFW.GLFW_KEY_R);

    public static String getKeyLang() {
        return COMPANION_ACTION.getBoundKeyLocalizedText().getString();
    }

    public static KeyBinding register(String name, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.familiar_friends." + name,
                InputUtil.Type.KEYSYM,
                key,
                "key.category.familiar_friends.companion_keys"
        ));
    }

    public static void register() {}
}
