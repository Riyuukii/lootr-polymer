package tornato.lootr.mixin;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootr.fabric.init.ModBlockEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(ModBlockEntities.class)
public class ModBlockEntitiesMixin {
    @Unique
    // changed to CHEST - idk if it helps or not for geysermc but so far it fixed the issue *shrug*
    private final static Map<BlockEntityType<?>, BlockEntityType<?>> polymer$BLOCK_ENTITIES = Map.ofEntries(
            Map.entry(ModBlockEntities.BRUSHABLE_BLOCK, BlockEntityType.BRUSHABLE_BLOCK),
            Map.entry(ModBlockEntities.DECORATED_POT, BlockEntityType.DECORATED_POT)
    );

    @ModifyArg(method = "registerBlockEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Lnet/minecraft/resources/Identifier;Ljava/lang/Object;)Ljava/lang/Object;"), index = 2)
    private static Object registerPolymerBlockEntities(Object entry) {
        PolymerBlockUtils.registerBlockEntity((BlockEntityType<?>) entry, (_, _) ->
                // replaced BlockEntityType.BARREL with BlockEntityType.CHEST
                polymer$BLOCK_ENTITIES.getOrDefault(entry, BlockEntityType.CHEST));
        return entry;
    }
}