package aeternal.ecoenergistics.common.tile;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nonnull;
import mekanism.api.Coord4D;
import mekanism.api.TileNetworkList;
import mekanism.common.Mekanism;
import mekanism.common.base.IRedstoneControl;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.IComputerIntegration;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.tile.prefab.TileEntityEffectsBlock;
import mekanism.common.util.CableUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator.EcoGeneratorType;

public abstract class   TileEntityEcoGenerator extends TileEntityEcoEffectsBlock implements IComputerIntegration, IRedstoneControl, ISecurityTile {

    /**
     * Output per tick this generator can transfer.
     */
    public double output;

    /**
     * This machine's current RedstoneControl type.
     */
    public RedstoneControl controlType;

    /*public TileComponentSecurity securityComponent = new TileComponentSecurity(this);*/

    /**
     * Generator -- a block that produces energy. It has a certain amount of fuel it can store as well as an output rate.
     *
     * @param name      - full name of this generator
     * @param maxEnergy - how much energy this generator can store
     */
    public TileEntityEcoGenerator(String soundPath, String name, double maxEnergy, double out) {
        super("gen." + soundPath, name, maxEnergy);
        output = out;
        controlType = RedstoneControl.DISABLED;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (MekanismConfig.current().general.destroyDisabledBlocks.val()) {
                EcoGeneratorType type = EcoGeneratorType.get(getBlockType(), getBlockMetadata());
                if (type != null && !type.isEnabled()) {
                    Mekanism.logger.info("Destroying generator of type '" + type.getBlockName() + "' at coords " + Coord4D.get(this) + " as according to config.");
                    world.setBlockToAir(getPos());
                    return;
                }
            }
            if (MekanismUtils.canFunction(this)) {
                CableUtils.emit(this);
            }
        }
    }

    @Override
    public double getMaxOutput() {
        return output;
    }

    @Override
    public boolean sideIsConsumer(EnumFacing side) {
        return false;
    }

    @Override
    public boolean sideIsOutput(EnumFacing side) {
        return side == facing;
    }

    /**
     * Whether or not this generator can operate.
     *
     * @return if the generator can operate
     */
    public abstract boolean canOperate();
    public abstract boolean canOperateNightTime();

    @Override
    public boolean canSetFacing(@Nonnull EnumFacing facing) {
        return facing != EnumFacing.DOWN && facing != EnumFacing.UP;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            controlType = RedstoneControl.values()[dataStream.readInt()];
        }
    }

    @Override
    public TileNetworkList getNetworkedData(TileNetworkList data) {
        super.getNetworkedData(data);
        data.add(controlType.ordinal());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);
        controlType = RedstoneControl.values()[nbtTags.getInteger("controlType")];
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);
        nbtTags.setInteger("controlType", controlType.ordinal());
        return nbtTags;
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean renderUpdate() {
        return true;
    }

    @Override
    public boolean lightUpdate() {
        return true;
    }

    @Override
    public RedstoneControl getControlType() {
        return controlType;
    }

    @Override
    public void setControlType(RedstoneControl type) {
        controlType = type;
        MekanismUtils.saveChunk(this);
    }

    @Override
    public boolean canPulse() {
        return false;
    }

    @Override
    public TileComponentSecurity getSecurity() {
       return null;
    }
}