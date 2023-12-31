package aeternal.ecoenergistics.common.item;

import javax.annotation.Nonnull;
import aeternal.ecoenergistics.common.tier.MEETiers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMoreSolarCells extends ItemMEE implements IMetaItem {

    public ItemMoreSolarCells() {
        super();
        setHasSubtypes(true);
    }

    @Override
    public String getTexture(int meta) {
        return MEETiers.values()[meta].getSimpleName() + "SolarCell";
    }

    @Override
    public int getVariants() {
        return MEETiers.values().length;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tabs, @Nonnull NonNullList<ItemStack> itemList) {
        if (isInCreativeTab(tabs)) {
            for (MEETiers tier : MEETiers.values()) {

                itemList.add(new ItemStack(this, 1, tier.ordinal()));

            }
        }
    }

    @Nonnull
    @Override
    public String getTranslationKey(ItemStack item) {
        return "item." + MEETiers.values()[item.getItemDamage()].getSimpleName() + "SolarCell";
    }
}