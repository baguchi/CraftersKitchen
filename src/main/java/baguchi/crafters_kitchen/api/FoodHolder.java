package baguchi.crafters_kitchen.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record FoodHolder(List<FoodMaterial> stack, List<FoodDecoration> foodDecorations) {
    public static final Codec<FoodHolder> CODEC = RecordCodecBuilder.create((p_258963_) -> {
        return p_258963_.group(FoodMaterial.CODEC.listOf().fieldOf("materials").forGetter(FoodHolder::stack),
                FoodDecoration.CODEC.listOf().fieldOf("decorations").forGetter(FoodHolder::foodDecorations)).apply(p_258963_, FoodHolder::new);
    });
}