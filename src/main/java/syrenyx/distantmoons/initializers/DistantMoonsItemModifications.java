package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class DistantMoonsItemModifications {

  private static final ConsumableComponent NETHER_FUNGUS_CONSUMABLE = ConsumableComponent.builder()
      .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0), 0.45F))
      .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 0), 0.35F))
      .build();
  private static final ConsumableComponent RED_MUSHROOM_CONSUMABLE = ConsumableComponent.builder()
      .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 1), 0.95F))
      .build();

  static {

    //DEFAULT COMPONENTS
    DefaultItemComponentEvents.MODIFY.register(context -> {
      context.modify(Items.BROWN_MUSHROOM, component -> {
        component.add(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder().build());
        component.add(DataComponentTypes.FOOD, new FoodComponent(1, 0.6F, false));
      });
      context.modify(Items.CRIMSON_FUNGUS, component -> {
        component.add(DataComponentTypes.CONSUMABLE, NETHER_FUNGUS_CONSUMABLE);
        component.add(DataComponentTypes.FOOD, new FoodComponent(1, 0.6F, false));
      });
      context.modify(Items.GLISTERING_MELON_SLICE, component -> {
        component.add(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder().build());
        component.add(DataComponentTypes.FOOD, new FoodComponent(4, 9.6F, false));
      });
      context.modify(Items.RED_MUSHROOM, component -> {
        component.add(DataComponentTypes.CONSUMABLE, RED_MUSHROOM_CONSUMABLE);
        component.add(DataComponentTypes.FOOD, new FoodComponent(1, 0.6F, false));
      });
      context.modify(Items.WARPED_FUNGUS, component -> {
        component.add(DataComponentTypes.CONSUMABLE, NETHER_FUNGUS_CONSUMABLE);
        component.add(DataComponentTypes.FOOD, new FoodComponent(1, 0.6F, false));
      });
    });

    //COMPOSTING CHANCES
    CompostingChanceRegistry.INSTANCE.add(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, 8.5F);

    //FUEL
    FuelRegistryEvents.BUILD.register((builder, context) -> {
      builder.add(DistantMoonsBlocks.CHARCOAL_BLOCK.asItem(), context.baseSmeltTime() * 72);
      builder.add(DistantMoonsItems.COKE, context.baseSmeltTime() * 12);
      builder.add(DistantMoonsBlocks.COKE_BLOCK.asItem(), context.baseSmeltTime() * 96);
    });
  }

  public static void initialize() {}
}
