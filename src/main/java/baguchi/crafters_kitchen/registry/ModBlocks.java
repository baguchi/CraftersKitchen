package baguchi.crafters_kitchen.registry;

import baguchi.crafters_kitchen.CraftersKitchen;
import baguchi.crafters_kitchen.block.SandwichBlock;
import baguchi.crafters_kitchen.client.render.item.SandwichBWLR;
import baguchi.crafters_kitchen.item.SandwichBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CraftersKitchen.MODID);
    public static final RegistryObject<Block> SANDWICH = register("sandwich", () -> new SandwichBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).pushReaction(PushReaction.DESTROY).noLootTable().sound(SoundType.WOOL)));

    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        Supplier<? extends Item> itemSupplier = item.apply(register);
        ModItems.ITEMS.register(name, itemSupplier);
        return register;
    }

    private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>) baseRegister(name, block, (object) -> ModBlocks.registerBlockItem(object));
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> {
            if (Objects.requireNonNull(block.get()) == SANDWICH.get()) {
                return new SandwichBlockItem(Objects.requireNonNull(block.get()), new Item.Properties().food(Foods.BREAD)) {
                    @Override
                    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
                        super.initializeClient(consumer);
                        consumer.accept(new IClientItemExtensions() {
                            @Override
                            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                return new SandwichBWLR();
                            }
                        });
                    }
                };
            } else {
                return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
            }
        };
    }
}
