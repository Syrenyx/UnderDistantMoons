package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.block.Block;
import syrenyx.distantmoons.content.data_component.BlastFurnaceFuelComponent;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class DistantMoonsItemModifications {

  private static final int COAL_SMELT_TIME_FACTOR = 8;
  private static final int COKE_SMELT_TIME_FACTOR = 12;
  private static final float WOOD_SMELT_TIME_FACTOR = 0.75F;
  private static final Consumable NETHER_FUNGUS_CONSUMABLE = Consumable.builder()
      .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 600, 0), 0.45F))
      .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 300, 0), 0.35F))
      .build();
  private static final Consumable RED_MUSHROOM_CONSUMABLE = Consumable.builder()
      .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 600, 1), 0.95F))
      .build();

  static {

    final HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);

    //DEFAULT COMPONENTS
    DefaultItemComponentEvents.MODIFY.register(context -> {
      context.modify(Items.BROWN_MUSHROOM, component -> {
        component.set(DataComponents.CONSUMABLE, Consumable.builder().build());
        component.set(DataComponents.FOOD, new FoodProperties(1, 0.6F, false));
      });
      context.modify(Items.CHARCOAL, component -> {
        component.set(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL, new BlastFurnaceFuelComponent(BlastFurnaceFuelComponent.COAL_HEAT_VALUE));
      });
      context.modify(Items.COAL, component -> {
        component.set(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL, new BlastFurnaceFuelComponent(BlastFurnaceFuelComponent.COAL_HEAT_VALUE));
      });
      context.modify(Items.COAL_BLOCK, component -> {
        component.set(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL, new BlastFurnaceFuelComponent(BlastFurnaceFuelComponent.DEFAULT_BURN_TIME * 9, BlastFurnaceFuelComponent.COAL_HEAT_VALUE));
      });
      context.modify(Items.CRIMSON_FUNGUS, component -> {
        component.set(DataComponents.CONSUMABLE, NETHER_FUNGUS_CONSUMABLE);
        component.set(DataComponents.FOOD, new FoodProperties(1, 0.6F, false));
      });
      context.modify(Items.GLISTERING_MELON_SLICE, component -> {
        component.set(DataComponents.CONSUMABLE, Consumable.builder().build());
        component.set(DataComponents.FOOD, new FoodProperties(4, 9.6F, false));
      });
      context.modify(Items.RED_MUSHROOM, component -> {
        component.set(DataComponents.CONSUMABLE, RED_MUSHROOM_CONSUMABLE);
        component.set(DataComponents.FOOD, new FoodProperties(1, 0.6F, false));
      });
      context.modify(Items.SHEARS, component -> component.set(DataComponents.TOOL, addRulesToToolComponent(
          Objects.requireNonNull(Items.SHEARS.components().get(DataComponents.TOOL)),
          Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(DistantMoonsBlockTags.DYED_PILLOW), 5.0F)
      )));
      context.modify(Items.WARPED_FUNGUS, component -> {
        component.set(DataComponents.CONSUMABLE, NETHER_FUNGUS_CONSUMABLE);
        component.set(DataComponents.FOOD, new FoodProperties(1, 0.6F, false));
      });
    });

    //COMPOSTING CHANCES
    CompostingChanceRegistry.INSTANCE.add(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, 8.5F);

    //FUEL
    FuelRegistryEvents.BUILD.register((builder, context) -> {

      //ITEMS
      builder.add(DistantMoonsBlocks.CHARCOAL_BLOCK.asItem(), context.baseSmeltTime() * COAL_SMELT_TIME_FACTOR * 9);
      builder.add(DistantMoonsItems.COKE, context.baseSmeltTime() * COKE_SMELT_TIME_FACTOR);
      builder.add(DistantMoonsBlocks.COKE_BLOCK.asItem(), context.baseSmeltTime() * COKE_SMELT_TIME_FACTOR * 9);

      //TAGS
      builder.add(DistantMoonsItemTags.SMELTING_FUEL_WOOD_BLOCK, (int) (context.baseSmeltTime() * WOOD_SMELT_TIME_FACTOR));
      builder.add(DistantMoonsItemTags.SMELTING_FUEL_WOOD_HALF_BLOCK, (int) (context.baseSmeltTime() * WOOD_SMELT_TIME_FACTOR * 0.5F));
    });
  }

  private static Tool addRulesToToolComponent(Tool component, Tool.Rule ... rules) {
    List<Tool.Rule> rulesList = new ArrayList<>(component.rules());
    rulesList.addAll(Arrays.asList(rules));
    return new Tool(
        rulesList,
        component.defaultMiningSpeed(),
        component.damagePerBlock(),
        component.canDestroyBlocksInCreative()
    );
  }

  public static void initialize() {}
}
