package baguchi.crafters_kitchen.registry;

import baguchi.crafters_kitchen.CraftersKitchen;
import baguchi.crafters_kitchen.item.SandwichBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreatives {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CraftersKitchen.MODID);

    public static final RegistryObject<CreativeModeTab> TABS = CREATIVE_MODE_TABS.register("crafters_kitchen", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + CraftersKitchen.MODID))
            .icon(() -> ModBlocks.SANDWICH.get().asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.SANDWICH.get());
                output.acceptAll(SandwichBlockItem.generateFood());
            }).build());
}