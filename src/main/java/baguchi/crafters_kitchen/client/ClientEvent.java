package baguchi.crafters_kitchen.client;


import baguchi.crafters_kitchen.CraftersKitchen;
import baguchi.crafters_kitchen.blockentity.SandwichBlockEntity;
import baguchi.crafters_kitchen.registry.ModBlocks;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraftersKitchen.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void renderEvent(RenderHighlightEvent.Block event) {
        if (Minecraft.getInstance().level != null) {

            VertexConsumer vertexconsumer = event.getMultiBufferSource().getBuffer(RenderType.lines());
            Vec3 vec3 = event.getTarget().getLocation();
            float f = (1 / 16F) * 0.5F;
            if (Minecraft.getInstance().level.getBlockState(event.getTarget().getBlockPos()).is(ModBlocks.SANDWICH.get())) {
                if (Minecraft.getInstance().level.getBlockEntity(event.getTarget().getBlockPos()) instanceof SandwichBlockEntity sandwichBlockEntity) {
                    BlockPos blockPos = event.getTarget().getBlockPos();
                    Vec3 camera = event.getCamera().getPosition();
                    float offsetY = sandwichBlockEntity.foodHolder.stack().size() * 0.1F * 0.5F + 0.08F;
                    event.getPoseStack().pushPose();
                    LevelRenderer.renderLineBox(event.getPoseStack(), vertexconsumer, vec3.x - f - camera.x, blockPos.getY() + offsetY - camera.y, vec3.z - f - camera.z, vec3.x + f - camera.x, blockPos.getY() + offsetY + f * 0.25F - camera.y, vec3.z + f - camera.z, 0.9F, 1F, 0.9F, 1.0F, 0.9F, 1F, 0.9F);
                    event.getPoseStack().popPose();
                }
            }

        }
    }

}
