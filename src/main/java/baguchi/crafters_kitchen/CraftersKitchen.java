package baguchi.crafters_kitchen;

import baguchi.crafters_kitchen.api.color.FoodColor;
import baguchi.crafters_kitchen.registry.ModBlockEntitys;
import baguchi.crafters_kitchen.registry.ModBlocks;
import baguchi.crafters_kitchen.registry.ModCreatives;
import baguchi.crafters_kitchen.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CraftersKitchen.MODID)
public class CraftersKitchen
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "crafters_kitchen";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public CraftersKitchen()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::dataSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntitys.BLOCK_ENTITIES.register(modEventBus);
        ModCreatives.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void dataSetup(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(FoodColor.REGISTRY_RESOURCE_KEY, FoodColor.CODEC, FoodColor.CODEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
