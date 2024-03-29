package aeternal.ecoenergistics.integration.avaritia.common.tile.solar.station;

import javax.annotation.Nonnull;

import aeternal.ecoenergistics.common.tile.solar.SolarConfig;
import aeternal.ecoenergistics.common.tile.solar.panel.TileEntitySolarPanelAdvanced;
import aeternal.ecoenergistics.integration.avaritia.common.tile.solar.AvaritiaSolarConfig;
import aeternal.ecoenergistics.integration.avaritia.common.tile.solar.panel.TileEntitySolarPanelCrystal;
import aeternal.ecoenergistics.integration.avaritia.common.tile.solar.panel.TileEntitySolarPanelInfinity;
import mekanism.api.Coord4D;
import mekanism.api.IEvaporationSolar;
import mekanism.common.base.IBoundingBlock;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.util.MekanismUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntitySolarStationInfinity extends TileEntitySolarPanelInfinity implements IBoundingBlock, IEvaporationSolar {

    public TileEntitySolarStationInfinity() {
        super("SolarStationInfinity", Integer.MAX_VALUE, AvaritiaSolarConfig.InfinityGen_SS.getValue());
    }

    @Override
    public boolean sideIsOutput(EnumFacing side) {
        return side == facing;
    }

    @Override
    protected float getConfiguredMax() {
        return (float) AvaritiaSolarConfig.InfinityGen_SS.getValue();
    }

    @Override
    public void onPlace() {
        Coord4D current = Coord4D.get(this);
        MekanismUtils.makeBoundingBlock(world, getPos().add(0, 1, 0), current);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                MekanismUtils.makeBoundingBlock(world, getPos().add(x, 2, z), current);
            }
        }
    }

    @Override
    public void onBreak() {
        world.setBlockToAir(getPos().add(0, 1, 0));
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.setBlockToAir(getPos().add(x, 2, z));
            }
        }
        invalidate();
        world.setBlockToAir(getPos());
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing side) {
        return capability == Capabilities.EVAPORATION_SOLAR_CAPABILITY || super.hasCapability(capability, side);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == Capabilities.EVAPORATION_SOLAR_CAPABILITY) {
            return Capabilities.EVAPORATION_SOLAR_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected boolean canSeeSky() {
        return world.canSeeSky(getPos().up(3));
    }
}