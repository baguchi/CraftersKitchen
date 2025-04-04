package baguchi.crafters_kitchen.client.render.item;

import baguchi.crafters_kitchen.blockentity.SandwichBlockEntity;
import baguchi.crafters_kitchen.registry.ModBlocks;
import baguchi.crafters_kitchen.utils.FoodUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SandwichBWLR extends BlockEntityWithoutLevelRenderer {

    public SandwichBWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
        SandwichBlockEntity blockEntity = new SandwichBlockEntity(BlockPos.ZERO, ModBlocks.SANDWICH.get().defaultBlockState());
        blockEntity.foodHolder = FoodUtils.readFoodDataFromItemStack(pStack);
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, pPoseStack, pBuffer, pPackedLight, pOverlay);
    }
}