package baguchi.crafters_kitchen.api.color;

import baguchi.crafters_kitchen.CraftersKitchen;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record FoodColor(int color) {
    public static final ResourceKey<Registry<FoodColor>> REGISTRY_RESOURCE_KEY = ResourceKey.createRegistryKey(new ResourceLocation(CraftersKitchen.MODID, "food_color"));
    public static final Codec<FoodColor> CODEC = RecordCodecBuilder.create((p_258963_) -> {
        return p_258963_.group(Codec.INT.fieldOf("color").forGetter(FoodColor::color)).apply(p_258963_, FoodColor::new);
    });
}
