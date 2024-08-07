package aeternal.ecoenergistics.common.block;

import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator.EcoGeneratorBlock;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator.EcoGeneratorType;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import mekanism.api.IMekWrench;
import mekanism.api.energy.IEnergizedItem;
import mekanism.common.base.*;
import mekanism.common.block.BlockMekanismContainer;
import mekanism.common.block.states.BlockStateFacing;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.wrenches.Wrenches;
import mekanism.common.security.ISecurityItem;
import mekanism.common.security.ISecurityTile;
import mekanism.common.tile.prefab.TileEntityBasicBlock;
import mekanism.common.tile.prefab.TileEntityContainerBlock;
import mekanism.common.tile.prefab.TileEntityElectricBlock;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.SecurityUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class BlockEcoGenerator extends BlockMekanismContainer {

    private static final AxisAlignedBB SOLAR_BOUNDS = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.7F, 1.0F);

    protected BlockEcoGenerator() {
        super(Material.IRON);
        setHardness(3.5F);
        setResistance(8F);
        setCreativeTab(EcoEnergistics.tabEcoEnergistics);
    }

    public static BlockEcoGenerator getGeneratorBlock(EcoGeneratorBlock block) {
        return new BlockEcoGenerator() {
            @Override
            public EcoGeneratorBlock getGeneratorBlock() {
                return block;
            }
        };
    }

    public abstract EcoGeneratorBlock getGeneratorBlock();

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateEcoGenerator(this, getTypeProperty());
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        EcoGeneratorType type = EcoGeneratorType.get(getGeneratorBlock(), meta & 0xF);
        return getDefaultState().withProperty(getTypeProperty(), type);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EcoGeneratorType type = state.getValue(getTypeProperty());
        return type.meta;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tile = MekanismUtils.getTileEntitySafe(worldIn, pos);
        if (tile instanceof TileEntityBasicBlock && ((TileEntityBasicBlock) tile).facing != null) {
            state = state.withProperty(BlockStateFacing.facingProperty, ((TileEntityBasicBlock) tile).facing);
        }
        if (tile instanceof IActiveState) {
            state = state.withProperty(BlockStateEcoGenerator.activeProperty, ((IActiveState) tile).getActive());
        }
        return state;
    }

    @Override
    @Deprecated
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        if (!world.isRemote) {
            final TileEntity tileEntity = MekanismUtils.getTileEntity(world, pos);
            if (tileEntity instanceof TileEntityBasicBlock) {
                ((TileEntityBasicBlock) tileEntity).onNeighborChange(neighborBlock);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack itemstack) {
        TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
        EnumFacing change = EnumFacing.SOUTH;
        if (tileEntity.canSetFacing(EnumFacing.DOWN) && tileEntity.canSetFacing(EnumFacing.UP)) {
            int height = Math.round(entityliving.rotationPitch);
            if (height >= 65) {
                change = EnumFacing.UP;
            } else if (height <= -65) {
                change = EnumFacing.DOWN;
            }
        }

        if (change != EnumFacing.DOWN && change != EnumFacing.UP) {
            int side = MathHelper.floor((double) (entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            change = switch (side) {
                case 0 -> EnumFacing.NORTH;
                case 1 -> EnumFacing.EAST;
                case 2 -> EnumFacing.SOUTH;
                case 3 -> EnumFacing.WEST;
                default -> change;
            };
        }

        tileEntity.setFacing(change);
        tileEntity.redstone = world.getRedstonePowerFromNeighbors(pos) > 0;
        if (tileEntity instanceof IBoundingBlock) {
            ((IBoundingBlock) tileEntity).onPlace();
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (MekanismConfig.current().client.enableAmbientLighting.val()) {
            TileEntity tileEntity = MekanismUtils.getTileEntitySafe(world, pos);
            if (tileEntity instanceof IActiveState && !(tileEntity instanceof TileEntityEcoSolarPanel)) {
                if (((IActiveState) tileEntity).getActive() && ((IActiveState) tileEntity).lightUpdate()) {
                    return MekanismConfig.current().client.ambientLightingLevel.val();
                }
            }
        }
        return 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getBlock().getMetaFromState(state);
    }

    @Override
    @Deprecated
    public float getPlayerRelativeBlockHardness(IBlockState state, @Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        return SecurityUtils.canAccess(player, tile) ? super.getPlayerRelativeBlockHardness(state, player, world, pos) : 0.0F;
    }

    @Override
    public void getSubBlocks(CreativeTabs creativetabs, NonNullList<ItemStack> list) {
        for (EcoGeneratorType type : EcoGeneratorType.getValidMachines()) {
            if (type.blockType == getGeneratorBlock() && type.isEnabled()) {
                switch (type) {
                    default -> list.add(new ItemStack(this, 1, type.meta));
                }
            }
        }
    }

    @Override
    public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
        if (tileEntity instanceof IBoundingBlock) {
            ((IBoundingBlock) tileEntity).onBreak();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
        int metadata = state.getBlock().getMetaFromState(state);
        ItemStack stack = entityplayer.getHeldItem(hand);

        if (!stack.isEmpty()) {
            IMekWrench wrenchHandler = Wrenches.getHandler(stack);
            if (wrenchHandler != null) {
                RayTraceResult raytrace = new RayTraceResult(new Vec3d(hitX, hitY, hitZ), side, pos);
                if (wrenchHandler.canUseWrench(entityplayer, hand, stack, raytrace)) {
                    if (SecurityUtils.canAccess(entityplayer, tileEntity)) {
                        wrenchHandler.wrenchUsed(entityplayer, hand, stack, raytrace);
                        if (entityplayer.isSneaking()) {
                            MekanismUtils.dismantleBlock(this, state, world, pos);
                            return true;
                        }
                        if (tileEntity != null) {
                            tileEntity.setFacing(tileEntity.facing.rotateY());
                            world.notifyNeighborsOfStateChange(pos, this, true);
                        }
                    } else {
                        SecurityUtils.displayNoAccess(entityplayer);
                    }
                    return true;
                }
            }
        }

        int guiId = EcoGeneratorType.get(getGeneratorBlock(), metadata).guiId;
        if (guiId != -1 && tileEntity != null) {
            if (!entityplayer.isSneaking()) {
                if (SecurityUtils.canAccess(entityplayer, tileEntity)) {
                    entityplayer.openGui(EcoEnergistics.instance, guiId, world, pos.getX(), pos.getY(), pos.getZ());
                } else {
                    SecurityUtils.displayNoAccess(entityplayer);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        int metadata = state.getBlock().getMetaFromState(state);
        if (EcoGeneratorType.get(getGeneratorBlock(), metadata) == null) {
            return null;
        }
        return EcoGeneratorType.get(getGeneratorBlock(), metadata).create();
    }

    @Nonnull
    @Override
    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        EcoGeneratorType type = EcoGeneratorType.get(state);
        if (type != null) {
            switch (type) {
                case SOLAR_PANEL_ADVANCED, SOLAR_PANEL_HYBRID, SOLAR_PANEL_PERFECTHYBRID, SOLAR_PANEL_QUANTUM,
                     SOLAR_PANEL_SPECTRAL, SOLAR_PANEL_PROTONIC, SOLAR_PANEL_SINGULAR, SOLAR_PANEL_DIFFRACTIVE,
                     SOLAR_PANEL_PHOTONIC, SOLAR_PANEL_NEUTRON,
                     AVARITIA_SOLAR_PANEL_CRYSTAL, AVARITIA_SOLAR_PANEL_NEUTRON, AVARITIA_SOLAR_PANEL_INFINITY -> {
                    return BlockFaceShape.UNDEFINED;
                }
                case SOLAR_STATION_ADVANCED, SOLAR_STATION_HYBRID, SOLAR_STATION_PERFECTHYBRID, SOLAR_STATION_QUANTUM,
                     SOLAR_STATION_SPECTRAL, SOLAR_STATION_PROTONIC, SOLAR_STATION_SINGULAR, SOLAR_STATION_DIFFRACTIVE,
                     SOLAR_STATION_PHOTONIC, SOLAR_STATION_NEUTRON,
                     AVARITIA_SOLAR_STATION_CRYSTAL, AVARITIA_SOLAR_STATION_NEUTRON,
                     AVARITIA_SOLAR_STATION_INFINITY -> {
                    return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
                }
            }
        }
        return super.getBlockFaceShape(world, state, pos, face);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return null;
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        EcoGeneratorType type = EcoGeneratorType.get(state);
        return switch (type) {
            case SOLAR_PANEL_ADVANCED, SOLAR_PANEL_HYBRID, SOLAR_PANEL_PERFECTHYBRID, SOLAR_PANEL_QUANTUM,
                 SOLAR_PANEL_SPECTRAL, SOLAR_PANEL_PROTONIC, SOLAR_PANEL_SINGULAR, SOLAR_PANEL_DIFFRACTIVE,
                 SOLAR_PANEL_PHOTONIC, SOLAR_PANEL_NEUTRON,
                 AVARITIA_SOLAR_PANEL_CRYSTAL, AVARITIA_SOLAR_PANEL_NEUTRON, AVARITIA_SOLAR_PANEL_INFINITY ->
                    SOLAR_BOUNDS;
            default -> super.getBoundingBox(state, world, pos);
        };
    }

    @Nonnull
    @Override
    protected ItemStack getDropItem(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
        ItemStack itemStack = new ItemStack(this, 1, state.getBlock().getMetaFromState(state));

        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        if (tileEntity == null) {
            return ItemStack.EMPTY;
        }

        if (tileEntity instanceof ISecurityTile) {
            ISecurityItem securityItem = (ISecurityItem) itemStack.getItem();
            if (securityItem.hasSecurity(itemStack)) {
                securityItem.setOwnerUUID(itemStack, ((ISecurityTile) tileEntity).getSecurity().getOwnerUUID());
                securityItem.setSecurity(itemStack, ((ISecurityTile) tileEntity).getSecurity().getMode());
            }
        }

        if (tileEntity instanceof TileEntityElectricBlock) {
            IEnergizedItem electricItem = (IEnergizedItem) itemStack.getItem();
            electricItem.setEnergy(itemStack, ((TileEntityElectricBlock) tileEntity).getEnergy());
        }

        if (tileEntity instanceof TileEntityContainerBlock && ((TileEntityContainerBlock) tileEntity).handleInventory()) {
            ISustainedInventory inventory = (ISustainedInventory) itemStack.getItem();
            inventory.setInventory(((TileEntityContainerBlock) tileEntity).getInventory(), itemStack);
        }

        if (tileEntity instanceof ISustainedData) {
            ((ISustainedData) tileEntity).writeSustainedData(itemStack);
        }
        return itemStack;
    }

    @Override
    @Deprecated
    public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public EnumFacing[] getValidRotations(World world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        EnumFacing[] valid = new EnumFacing[6];
        if (tile instanceof TileEntityBasicBlock) {
            TileEntityBasicBlock basicTile = (TileEntityBasicBlock) tile;
            for (EnumFacing dir : EnumFacing.VALUES) {
                if (basicTile.canSetFacing(dir)) {
                    valid[dir.ordinal()] = dir;
                }
            }
        }
        return valid;
    }

    @Override
    public boolean rotateBlock(World world, @Nonnull BlockPos pos, @Nonnull EnumFacing axis) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBasicBlock basicTile) {
            if (basicTile.canSetFacing(axis)) {
                basicTile.setFacing(axis);
                return true;
            }
        }
        return false;
    }

    public PropertyEnum<EcoGeneratorType> getTypeProperty() {
        return getGeneratorBlock().getProperty();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState blockState) {
        EcoGeneratorType type = EcoGeneratorType.get(blockState);
        return type != null && type.hasRedstoneOutput;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        EcoGeneratorType type = EcoGeneratorType.get(blockState);
        if (type != null && type.hasRedstoneOutput) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof IComparatorSupport) {
                return ((IComparatorSupport) tile).getRedstoneLevel();
            }
        }
        return 0;
    }
}
