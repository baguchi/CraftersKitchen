package baguchi.crafters_kitchen.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public record FoodMaterial(ItemStack stack, Vec3 pos) {
    public static final Codec<FoodMaterial> CODEC = RecordCodecBuilder.create((p_258963_) -> {
        return p_258963_.group(ItemStack.CODEC.fieldOf("stack").forGetter(FoodMaterial::stack),
                Vec3.CODEC.fieldOf("pos").forGetter(FoodMaterial::pos)).apply(p_258963_, FoodMaterial::new);
    });
}