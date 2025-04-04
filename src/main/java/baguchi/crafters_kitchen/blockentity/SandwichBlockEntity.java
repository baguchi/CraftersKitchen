package baguchi.crafters_kitchen.blockentity;

import baguchi.crafters_kitchen.api.FoodDecoration;
import baguchi.crafters_kitchen.api.FoodHolder;
import baguchi.crafters_kitchen.api.FoodMaterial;
import baguchi.crafters_kitchen.api.color.FoodColor;
import baguchi.crafters_kitchen.registry.ModBlockEntitys;
import baguchi.crafters_kitchen.utils.FoodUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class SandwichBlockEntity extends SyncedBlockEntity {
    public FoodHolder foodHolder = new FoodHolder(new ArrayList<>(), new ArrayList<>());

    public SandwichBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.SANDWICH.get(), pos, state);

    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        FoodHolder foodHolder1 = FoodUtils.readFoodData(compound);
        if (foodHolder1 != null) {
            foodHolder = foodHolder1;
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        FoodUtils.writeFoodData(compound, foodHolder);
    }

    public boolean addFoodItem(ItemStack itemStack, Vec3 vec3) {
        if (!itemStack.isEmpty() && foodHolder.stack().size() < 8) {
            List<FoodMaterial> foodMaterials = new ArrayList<>();
            foodMaterials.addAll(foodHolder.stack());
            foodMaterials.add(new FoodMaterial(itemStack, vec3));
            foodHolder = new FoodHolder(foodMaterials, foodHolder.foodDecorations());
            inventoryChanged();
            return true;
        }
        return false;
    }

    public boolean addFoodDecoItem(ItemStack itemStack, Vec3 vec3) {
        if (!itemStack.isEmpty() && foodHolder.foodDecorations().size() < 32) {
            FoodColor foodColor = this.level.registryAccess().registryOrThrow(FoodColor.REGISTRY_RESOURCE_KEY).get(ForgeRegistries.ITEMS.getKey(itemStack.getItem()));
            if (foodColor != null) {
                List<FoodDecoration> foodDecorations = new ArrayList<>();
                foodDecorations.addAll(foodHolder.foodDecorations());
                foodDecorations.add(new FoodDecoration(vec3, foodColor.color()));
                foodHolder = new FoodHolder(foodHolder.stack(), foodDecorations);
                inventoryChanged();
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    public FoodHolder getFoodHolder() {
        return foodHolder;
    }
}