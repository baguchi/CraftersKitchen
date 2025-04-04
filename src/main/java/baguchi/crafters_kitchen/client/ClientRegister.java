package baguchi.crafters_kitchen.client;


import baguchi.crafters_kitchen.CraftersKitchen;
import baguchi.crafters_kitchen.client.render.block.SandwichBlockEntityRender;
import baguchi.crafters_kitchen.client.render.model.StewModel;
import baguchi.crafters_kitchen.registry.ModBlockEntitys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraftersKitchen.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegister {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntitys.SANDWICH.get(), SandwichBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StewModel.LAYER_LOCATION, StewModel::createBodyLayer);
    }

}
