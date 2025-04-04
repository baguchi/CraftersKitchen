package baguchi.crafters_kitchen.client.render.block;

import baguchi.crafters_kitchen.api.FoodMaterial;
import baguchi.crafters_kitchen.block.SandwichBlock;
import baguchi.crafters_kitchen.blockentity.SandwichBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class SandwichBlockEntityRender implements BlockEntityRenderer<SandwichBlockEntity> {
    private final RandomSource random = RandomSource.create();

    public SandwichBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SandwichBlockEntity plateBlockEntity, float p_112308_, PoseStack poseStack, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
        if (plateBlockEntity.foodHolder != null) {
            Direction direction = plateBlockEntity.getBlockState().getValue(SandwichBlock.FACING).getOpposite();
            List<FoodMaterial> foodMaterials = plateBlockEntity.foodHolder.stack();
            int posLong = (int) plateBlockEntity.getBlockPos().asLong();
            int j = foodMaterials.size();
            int i = 187;

            this.random.setSeed((long) i);
            poseStack.pushPose();
            poseStack.translate(0, 1 * 0.1F * 0.5F, 0);

            if (!foodMaterials.isEmpty()) {
                for (int k = 0; k < j; ++k) {
                    FoodMaterial foodMaterial = foodMaterials.get(k);
                    poseStack.pushPose();
                    ItemRenderer itemRenderer = Minecraft.getInstance()
                            .getItemRenderer();
                    boolean isBlockItem = itemRenderer.getModel(foodMaterial.stack(), plateBlockEntity.getLevel(), null, 0)
                            .isGui3d();
                    if (isBlockItem) {
                        if (k > 0) {
                            poseStack.translate(foodMaterial.pos().x, k * 0.1F * 0.5F + foodMaterial.pos().y, foodMaterial.pos().z);
                        }
                        renderBlock(poseStack, direction);
                    } else {
                        if (k > 0) {
                            poseStack.translate(foodMaterial.pos().x, k * 0.1F * 0.5F + foodMaterial.pos().y, foodMaterial.pos().z);
                        }
                        renderItemLayingDown(poseStack, direction);
                    }
                    Minecraft.getInstance().getItemRenderer().renderStatic(foodMaterial.stack(), ItemDisplayContext.FIXED, p_112311_, p_112312_, poseStack, p_112310_, plateBlockEntity.getLevel(), posLong);
                    poseStack.popPose();
                }


            }
            poseStack.pushPose();
            float f = -direction.toYRot();
            poseStack.translate(0.5D, 0.07D, 0.5D);
            poseStack.mulPose(Axis.YP.rotationDegrees(f));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.scale(0.6F, 0.6F, 0.6F);
            Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.BREAD), ItemDisplayContext.FIXED, p_112311_, p_112312_, poseStack, p_112310_, plateBlockEntity.getLevel(), posLong);
            poseStack.popPose();

            poseStack.popPose();
        }
    }

    protected int getRenderAmount(ItemStack p_115043_) {
        int i = 1;
        if (p_115043_.getCount() > 48) {
            i = 5;
        } else if (p_115043_.getCount() > 32) {
            i = 4;
        } else if (p_115043_.getCount() > 16) {
            i = 3;
        } else if (p_115043_.getCount() > 1) {
            i = 2;
        }

        return i;
    }

    public void renderItemLayingDown(PoseStack matrixStackIn, Direction direction) {
        matrixStackIn.translate(0.5D, 0.07D, 0.5D);
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public void renderBlock(PoseStack matrixStackIn, Direction direction) {
        matrixStackIn.translate(0.5D, 0.25D, 0.5D);
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));
        matrixStackIn.scale(0.8F, 0.8F, 0.8F);
    }
}