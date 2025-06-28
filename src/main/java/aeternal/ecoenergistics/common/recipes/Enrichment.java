package aeternal.ecoenergistics.common.recipes;


import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.EcoEnergisticsItems;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.enums.AvaritiaTiers;
import aeternal.ecoenergistics.common.enums.MoreDust;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.item.ItemStack;

import static aeternal.ecoenergistics.common.Infusers.*;

public class Enrichment {
    public static void InitCustomEnrichmentRecipes() {
        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.ENRICHMENT_CHAMBER)) {

            ItemStack titaniumOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 0);
            ItemStack uraniummOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 1);
            ItemStack iridiumOre = new ItemStack(EcoEnergisticsBlocks.EcoOreBlock, 1, 2);

            ItemStack titaniumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 0);
            ItemStack uraniumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 1);
            ItemStack iridiumClump = new ItemStack(EcoEnergisticsItems.MoreClump, 1, 2);

            ItemStack titaniumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 0);
            ItemStack uraniumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 1);
            ItemStack iridiumDirty = new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 2);

            ItemStack titaniumCompressed = new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 4);
            ItemStack uraniumCompressed = new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 5);
            ItemStack iridiumCompressed = new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 6);

            ItemStack glowstoneCompressed = new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 2);
            if (!titaniumDusts.isEmpty()){
                ItemStack titaniumDust = titaniumDusts.get(0).copy();
                ItemStack titaniumDust2 = titaniumDust.copy();
                titaniumDust2.setCount(titaniumDust2.getCount() * 2);
                RecipeHandler.addEnrichmentChamberRecipe(titaniumOre, titaniumDust2);
                RecipeHandler.addEnrichmentChamberRecipe(titaniumDirty, titaniumDust);
                RecipeHandler.addEnrichmentChamberRecipe(titaniumDust, titaniumCompressed);
            }
            if (!uraniumDusts.isEmpty()){
                ItemStack uraniumDust = uraniumDusts.get(0).copy();
                ItemStack uraniumDust2 = uraniumDust.copy();
                uraniumDust2.setCount(uraniumDust2.getCount() * 2);
                RecipeHandler.addEnrichmentChamberRecipe(uraniummOre, uraniumDust2);
                RecipeHandler.addEnrichmentChamberRecipe(uraniumDirty, uraniumDust);
                RecipeHandler.addEnrichmentChamberRecipe(uraniumDust, uraniumCompressed);
            }

            if (!iridiumDusts.isEmpty()){
                ItemStack iridiumDust = iridiumDusts.get(0).copy();
                ItemStack iridiumDust2 = iridiumDust.copy();
                iridiumDust2.setCount(iridiumDust2.getCount() * 2);
                RecipeHandler.addEnrichmentChamberRecipe(iridiumOre, iridiumDust2);
                RecipeHandler.addEnrichmentChamberRecipe(iridiumDirty, iridiumDust);
                RecipeHandler.addEnrichmentChamberRecipe(iridiumDust, iridiumCompressed);
            }

            //conflicting with existing Mek recipes
            /*
            RecipeHandler.addEnrichmentChamberRecipe(lapisDusts.get(0).copy(), new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 0));
            RecipeHandler.addEnrichmentChamberRecipe(emeraldDusts.get(0).copy(), new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 1));
            */
            if (!glowstoneDusts.isEmpty()){
                ItemStack glowstoneDust = glowstoneDusts.get(0).copy();
                RecipeHandler.addEnrichmentChamberRecipe(glowstoneDust,glowstoneCompressed);
            }

            if (!goldDusts.isEmpty()) {
                RecipeHandler.addEnrichmentChamberRecipe(goldDusts.get(0).copy(), new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 3));
            }


            if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
                if (!CrystalMatrixDusts.isEmpty()) {
                    ItemStack dustCrystal = CrystalMatrixDusts.get(0).copy();
                    ItemStack compressedCrystal = new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, AvaritiaTiers.CRYSTALMATRIX.ordinal());
                    RecipeHandler.addEnrichmentChamberRecipe(dustCrystal, compressedCrystal);
                }
                if (!NeutroniumDusts.isEmpty()) {
                    ItemStack dustNeutronium = NeutroniumDusts.get(0).copy();
                    ItemStack compressedNeutronium = new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, AvaritiaTiers.NEUTRONIUM.ordinal());
                    RecipeHandler.addEnrichmentChamberRecipe(dustNeutronium, compressedNeutronium);
                }
                if (!InfinityDusts.isEmpty()) {
                    ItemStack dustInfinity = InfinityDusts.get(0).copy();
                    ItemStack compressedInfinity = new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, AvaritiaTiers.INFINITY.ordinal());
                    RecipeHandler.addEnrichmentChamberRecipe(dustInfinity, compressedInfinity);
                }

            }

        }
    }
}
