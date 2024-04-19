package net.darktree.interference.mixin;

import net.minecraft.component.ComponentChanges;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.SetComponentsLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(SetComponentsLootFunction.class)
public interface SetComponentLootFunctionInvoker {

	@Invoker("<init>")
	static SetComponentsLootFunction invoke_SetComponentsLootFunction(List<LootCondition> conditions, ComponentChanges changes) {
		return null;
	}

}
