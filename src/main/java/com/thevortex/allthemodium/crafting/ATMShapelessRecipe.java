package com.thevortex.allthemodium.crafting;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class 	ATMShapelessRecipe implements IATMShapelessRecipe {

	private final ShapelessRecipe recipe;

	public ATMShapelessRecipe(ShapelessRecipe recipe) {
		this.recipe = recipe;

	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ATMCraftingSetup.ATM_SHAPELESS_DATA.get();
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		// Note: We do not override the matches method if it matches ignoring NBT,
		// to ensure that we return the proper value for if there is a match that gives
		// a proper output
		return recipe.matches(inv, world) && !getCraftingResult(inv).isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		if (getRecipeOutput().isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack toReturn = getRecipeOutput().copy();

		Map<Enchantment,Integer> enchant = new HashMap<>();

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty() && (!stack.getEnchantmentTagList().isEmpty())) {
				Map<Enchantment,Integer> temp = EnchantmentHelper.getEnchantments(stack);
				for(Enchantment e : temp.keySet()) {
					if(enchant.containsKey(e) && (enchant.get(e) == temp.get(e))) {	enchant.put(e, temp.get(e) + 1); }
					else { enchant.put(e,temp.get(e)); }
				}
			}
		}
		EnchantmentHelper.setEnchantments(enchant,toReturn);
		return toReturn;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		return recipe.getRemainingItems(inv);
	}

	@Override
	public ItemStack getIcon() {
		// TODO Auto-generated method stub
		return recipe.getIcon();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return recipe.getIngredients();
	}

	@Override
	public boolean canFit(int width, int height) {
		return recipe.canFit(width, height);
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return recipe.getRecipeOutput();
	}

	@Override
	public ResourceLocation getId() {
		// TODO Auto-generated method stub
		return recipe.getId();
	}

}