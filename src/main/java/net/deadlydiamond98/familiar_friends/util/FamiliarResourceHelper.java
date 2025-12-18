package net.deadlydiamond98.familiar_friends.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FamiliarResourceHelper {
    public static void loadJsonData(ResourceManager manager, String startingPath, FamiliarJsonLoader jsonLoader) {
        manager.findAllResources(startingPath, path -> path.getPath().endsWith(".json")).forEach((identifier, resources) -> {
            try (InputStream input = resources.get(0).getInputStream(); InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                jsonLoader.load(identifier, JsonParser.parseReader(reader).getAsJsonObject());
            } catch (Exception e) {
                FamiliarFriends.LOGGER.error("Failed to load Familiar: {}", identifier.toString());
                throw new RuntimeException(e);
            }
        });
    }

    public static int getInt(JsonObject json, String field, int fallback) {
        return json.has(field) ? json.get(field).getAsInt() : fallback;
    }

    public static float getFloat(JsonObject json, String field, float fallback) {
        return json.has(field) ? json.get(field).getAsFloat() : fallback;
    }

    public static boolean getBl(JsonObject json, String field, boolean fallback) {
        return json.has(field) ? json.get(field).getAsBoolean() : fallback;
    }

    public static String getStr(JsonObject json, String field, String fallback) {
        return json.has(field) ? json.get(field).getAsString() : fallback;
    }

    @FunctionalInterface
    public interface FamiliarJsonLoader {
        void load(Identifier file, JsonObject json);
    }
}
