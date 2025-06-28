package aeternal.ecoenergistics.common;

import aeternal.ecoenergistics.common.config.EcoConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static aeternal.ecoenergistics.common.EcoEnergisticsBlocks.EcoOreBlock;
import static aeternal.ecoenergistics.common.EcoEnergisticsItems.Dust;

public class EcoEnergisticsOreDict {

    public static void registerOreDict() {
        OreDictionary.registerOre("dustLapis", new ItemStack(EcoEnergisticsItems.MoreDust, 1, 0));
        OreDictionary.registerOre("dustEmerald", new ItemStack(EcoEnergisticsItems.MoreDust, 1, 1));
        OreDictionary.registerOre("dustActivatedGlowstone", new ItemStack(EcoEnergisticsItems.MoreDust, 1, 2));
        OreDictionary.registerOre("dustTitanium", new ItemStack(Dust, 1, 0));
        OreDictionary.registerOre("dustUranium", new ItemStack(Dust, 1, 1));
        OreDictionary.registerOre("dustIridium", new ItemStack(Dust, 1, 2));

        OreDictionary.registerOre("itemCompressedLapis", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 0));
        OreDictionary.registerOre("itemCompressedEmerald", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 1));
        OreDictionary.registerOre("itemCompressedGlowstone", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 2));
        OreDictionary.registerOre("itemCompressedGold", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 3));
        OreDictionary.registerOre("itemCompressedTitanium", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 4));
        OreDictionary.registerOre("itemCompressedUranium", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 5));
        OreDictionary.registerOre("itemCompressedIridium", new ItemStack(EcoEnergisticsItems.MoreCompressed, 1, 6));

        OreDictionary.registerOre("ingotActivatedGlowstone", new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 0));
        OreDictionary.registerOre("ingotTitanium", new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 1));
        OreDictionary.registerOre("ingotUranium", new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 2));
        OreDictionary.registerOre("ingotIridium", new ItemStack(EcoEnergisticsItems.MoreIngot, 1, 3));

        OreDictionary.registerOre("nuggetActivatedGlowstone", new ItemStack(EcoEnergisticsItems.MoreNugget, 1, 0));
        OreDictionary.registerOre("nuggetTitanium", new ItemStack(EcoEnergisticsItems.MoreNugget, 1, 1));
        OreDictionary.registerOre("nuggetUranium", new ItemStack(EcoEnergisticsItems.MoreNugget, 1, 2));
        OreDictionary.registerOre("nuggetIridium", new ItemStack(EcoEnergisticsItems.MoreNugget, 1, 3));

        OreDictionary.registerOre("dustDirtyTitanium", new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 0));
        OreDictionary.registerOre("dustDirtyUranium", new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 1));
        OreDictionary.registerOre("dustDirtyIridium", new ItemStack(EcoEnergisticsItems.MoreDirtyDust, 1, 2));

        OreDictionary.registerOre("clumpTitanium", new ItemStack(EcoEnergisticsItems.MoreClump, 1, 0));
        OreDictionary.registerOre("clumpUranium", new ItemStack(EcoEnergisticsItems.MoreClump, 1, 1));
        OreDictionary.registerOre("clumpIridium", new ItemStack(EcoEnergisticsItems.MoreClump, 1, 2));

        OreDictionary.registerOre("shardTitanium", new ItemStack(EcoEnergisticsItems.MoreShard, 1, 0));
        OreDictionary.registerOre("shardUranium", new ItemStack(EcoEnergisticsItems.MoreShard, 1, 1));
        OreDictionary.registerOre("shardIridium", new ItemStack(EcoEnergisticsItems.MoreShard, 1, 2));

        OreDictionary.registerOre("crystalTitanium", new ItemStack(EcoEnergisticsItems.MoreCrystal, 1, 0));
        OreDictionary.registerOre("crystalUranium", new ItemStack(EcoEnergisticsItems.MoreCrystal, 1, 1));
        OreDictionary.registerOre("crystalIridium", new ItemStack(EcoEnergisticsItems.MoreCrystal, 1, 2));

        OreDictionary.registerOre("oreTitanium", new ItemStack(EcoOreBlock, 1, 0));
        OreDictionary.registerOre("oreUranium", new ItemStack(EcoOreBlock, 1, 1));
        OreDictionary.registerOre("oreIridium", new ItemStack(EcoOreBlock, 1, 2));

        OreDictionary.registerOre("blockTitanium", new ItemStack(EcoEnergisticsBlocks.EcoBlockBasic, 1, 0));
        OreDictionary.registerOre("blockUranium", new ItemStack(EcoEnergisticsBlocks.EcoBlockBasic, 1, 1));
        OreDictionary.registerOre("blockIridium", new ItemStack(EcoEnergisticsBlocks.EcoBlockBasic, 1, 2));

        OreDictionary.registerOre("circuitAdvancedVariation", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 0));
        OreDictionary.registerOre("circuitHybrid", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 1));
        OreDictionary.registerOre("circuitPerfectHybrid", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 2));
        OreDictionary.registerOre("circuitQuantum", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 3));
        OreDictionary.registerOre("circuitSpectral", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 4));
        OreDictionary.registerOre("circuitProtonic", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 5));
        OreDictionary.registerOre("circuitSingular", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 6));
        OreDictionary.registerOre("circuitDiffractive", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 7));
        OreDictionary.registerOre("circuitPhotonic", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 8));
        OreDictionary.registerOre("circuitNeutron", new ItemStack(EcoEnergisticsItems.MoreControlCircuit, 1, 9));

        OreDictionary.registerOre("alloyAdvancedVariation", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 0));
        OreDictionary.registerOre("alloyHybrid", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 1));
        OreDictionary.registerOre("alloyPerfectHybrid", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 2));
        OreDictionary.registerOre("alloyQuantum", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 3));
        OreDictionary.registerOre("alloySpectral", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 4));
        OreDictionary.registerOre("alloyProtonic", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 5));
        OreDictionary.registerOre("alloySingular", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 6));
        OreDictionary.registerOre("alloyDiffractive", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 7));
        OreDictionary.registerOre("alloyPhotonic", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 8));
        OreDictionary.registerOre("alloyNeutron", new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 9));

        if (EcoEnergistics.hooks.AvaritiaLoaded &&  EcoConfig.current().integration.AvaritiaEnable.val()) {
            OreDictionary.registerOre("alloyCrystal", new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 0));
            OreDictionary.registerOre("alloyNeutronium", new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 1));
            OreDictionary.registerOre("alloyInfinity", new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 2));

            OreDictionary.registerOre("circuitCrystal", new ItemStack(EcoEnergisticsItems.ControlCircuitAvaritia, 1, 0));
            OreDictionary.registerOre("circuitNeutronium", new ItemStack(EcoEnergisticsItems.ControlCircuitAvaritia, 1, 1));
            OreDictionary.registerOre("circuitInfinity", new ItemStack(EcoEnergisticsItems.ControlCircuitAvaritia, 1, 2));

            OreDictionary.registerOre("dustCrystalMatrix", new ItemStack(EcoEnergisticsItems.DustAvaritia, 1, 0));
            OreDictionary.registerOre("dustCosmicNeutronium", new ItemStack(EcoEnergisticsItems.DustAvaritia, 1, 1));
            OreDictionary.registerOre("dustInfinity", new ItemStack(EcoEnergisticsItems.DustAvaritia, 1, 2));

            OreDictionary.registerOre("itemCompressedCrystal", new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, 0));
            OreDictionary.registerOre("itemCompressedNeutronium", new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, 1));
            OreDictionary.registerOre("itemCompressedInfinity", new ItemStack(EcoEnergisticsItems.CompressedAvaritia, 1, 2));

            OreDictionary.registerOre("cellCrystal", new ItemStack(EcoEnergisticsItems.SolarCellAvaritia, 1, 0));
            OreDictionary.registerOre("cellNeutronium", new ItemStack(EcoEnergisticsItems.SolarCellAvaritia, 1, 1));
            OreDictionary.registerOre("cellInfinity", new ItemStack(EcoEnergisticsItems.SolarCellAvaritia, 1, 2));
        }
    }
}
