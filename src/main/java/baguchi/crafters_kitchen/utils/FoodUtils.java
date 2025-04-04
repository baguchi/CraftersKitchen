package baguchi.crafters_kitchen.utils;

import baguchi.crafters_kitchen.api.FoodHolder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static baguchi.crafters_kitchen.CraftersKitchen.LOGGER;

public class FoodUtils {
    public static void writeFoodDataToItemStack(ItemStack stack, FoodHolder foodHolder) {

        CompoundTag tag = stack.getOrCreateTag();
        FoodHolder.CODEC.encodeStart(NbtOps.INSTANCE, foodHolder).resultOrPartial(LOGGER::error).ifPresent((p_35454_) -> {
            tag.put("food_data", p_35454_);
        });
    }

    @Nullable
    public static FoodHolder readFoodDataFromItemStack(ItemStack stack) {

        final FoodHolder[] foodHolder = {new FoodHolder(new ArrayList<>(), new ArrayList<>())};
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("food_data", 10)) {
            DataResult<FoodHolder> dataresult = FoodHolder.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("food_data")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(foodHolder1 -> foodHolder[0] = foodHolder1);
        }
        return foodHolder[0];
    }

    public static CompoundTag writeFoodData(CompoundTag tag, FoodHolder foodHolder) {
        if (tag != null) {
            FoodHolder.CODEC.encodeStart(NbtOps.INSTANCE, foodHolder).resultOrPartial(LOGGER::error).ifPresent((p_35454_) -> {
                tag.put("food_data", p_35454_);
            });
        }
        return tag;
    }

    @Nullable
    public static FoodHolder readFoodData(CompoundTag tag) {

        final FoodHolder[] foodHolder = {new FoodHolder(new ArrayList<>(), new ArrayList<>())};
        if (tag != null && tag.contains("food_data", 10)) {
            DataResult<FoodHolder> dataresult = FoodHolder.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("food_data")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(foodHolder1 -> foodHolder[0] = foodHolder1);
        }
        return foodHolder[0];
    }
}
