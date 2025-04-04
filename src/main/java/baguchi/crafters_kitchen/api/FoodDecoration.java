package baguchi.crafters_kitchen.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.phys.Vec3;

public record FoodDecoration(Vec3 position, int color) {
    public static final Codec<FoodDecoration> CODEC = RecordCodecBuilder.create((p_258963_) -> {
        return p_258963_.group(Vec3.CODEC.fieldOf("position").forGetter(FoodDecoration::position),
                Codec.INT.fieldOf("color").forGetter(FoodDecoration::color)).apply(p_258963_, FoodDecoration::new);
    });
}
