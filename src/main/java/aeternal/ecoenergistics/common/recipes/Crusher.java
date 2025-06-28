package aeternal.ecoenergistics.common.recipes;

import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.EcoEnergisticsItems;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.enums.AvaritiaTiers;
import aeternal.ecoenergistics.common.enums.Ingot;
import aeternal.ecoenergistics.common.enums.MoreDust;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.RecipeHandler;
import morph.avaritia.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import static aeternal.ecoenergistics.common.Infusers.*;
import static aeternal.ecoenergistics.common.Infusers.glowstoneDusts;

public class Crusher {
    public static void InitCustomCrusherRecipes() {
        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.CRUSHER)) {
            ItemStack titaniumOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 0);
            ItemStack uraniummOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 1);
            ItemStack iridiumOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 2);

            ItemStack titaniumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 0);
            ItemStack uraniumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 1);
            ItemStack iridiumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 2);

            ItemStack titaniumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 0);
            ItemStack uraniumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 1);
            ItemStack iridiumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 2);

            ItemStack titaniumIngot = new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 1);
            ItemStack uraniumIngot = new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 2);
            ItemStack iridiumIngot = new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 3);

            ItemStack titaniumDust = new ItemStack(EcoEnergisticsItems.Dust, 1, 0);
            ItemStack titaniumDust2 = titaniumDust.copy();
            titaniumDust2.setCount(titaniumDust2.getCount() * 2);
            ItemStack uraniumDust = new ItemStack(EcoEnergisticsItems.Dust, 1, 1);
            ItemStack uraniumDust2 = uraniumDust.copy();
            uraniumDust2.setCount(uraniumDust2.getCount() * 2);
            ItemStack iridiumDust = new ItemStack(EcoEnergisticsItems.Dust, 1, 2);
            ItemStack iridiumDust2 = iridiumDust.copy();
            iridiumDust2.setCount(iridiumDust2.getCount() * 2);


            RecipeHandler.addCrusherRecipe(new ItemStack(Items.DYE, 1, 4), new ItemStack(EcoEnergisticsItems.MoreDust, 1, MoreDust.LAPIS.ordinal()));
            RecipeHandler.addCrusherRecipe(new ItemStack(EcoEnergisticsItems.MoreIngot, 1, Ingot.ACTIVATEDGLOWSTONE.ordinal()), new ItemStack(EcoEnergisticsItems.MoreDust, 1, MoreDust.ACTIVATEDGLOWSTONE.ordinal()));
            RecipeHandler.addCrusherRecipe(new ItemStack(Items.EMERALD), new ItemStack(EcoEnergisticsItems.MoreDust, 1, MoreDust.EMERALD.ordinal()));
            RecipeHandler.addCrusherRecipe(new ItemStack(EcoEnergisticsItems.MoreIngot, 1, Ingot.ACTIVATEDGLOWSTONE.ordinal()), new ItemStack(EcoEnergisticsItems.MoreDust, 1, MoreDust.ACTIVATEDGLOWSTONE.ordinal()));

            RecipeHandler.addCrusherRecipe(titaniumClump, titaniumDirty);
            RecipeHandler.addCrusherRecipe(uraniumClump, uraniumDirty);
            RecipeHandler.addCrusherRecipe(iridiumClump, iridiumDirty);

            RecipeHandler.addCrusherRecipe(titaniumIngot, titaniumDust);
            RecipeHandler.addCrusherRecipe(uraniumIngot, uraniumDust);
            RecipeHandler.addCrusherRecipe(iridiumIngot, iridiumDust);

            if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
                if (!NeutroniumDusts.isEmpty()){
                    ItemStack neutroniumIngot = ModItems.neutronium_ingot;
                    ItemStack neutroniumDust = NeutroniumDusts.get(0).copy();
                    RecipeHandler.addCrusherRecipe(neutroniumIngot,neutroniumDust);
                }
                if (!CrystalMatrixDusts.isEmpty()){
                    ItemStack crystalIngot = ModItems.crystal_matrix_ingot;
                    ItemStack crystalDust = CrystalMatrixDusts.get(0).copy();
                    RecipeHandler.addCrusherRecipe(crystalIngot,crystalDust);
                }
                if (!InfinityDusts.isEmpty()){
                    ItemStack infinityIngot = ModItems.infinity_ingot;
                    ItemStack infinityDust = InfinityDusts.get(0).copy();
                    RecipeHandler.addCrusherRecipe(infinityIngot,infinityDust);
                }
            }
        }
    }
}
