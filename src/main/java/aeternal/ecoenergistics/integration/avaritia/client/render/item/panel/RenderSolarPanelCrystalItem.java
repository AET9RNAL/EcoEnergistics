package aeternal.ecoenergistics.integration.avaritia.client.render.item.panel;

import aeternal.ecoenergistics.Constants;
import aeternal.ecoenergistics.integration.avaritia.client.model.ModelSolarPanelAvaritia;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderSolarPanelCrystalItem {

    private static ModelSolarPanelAvaritia solarPanelCrystal = new ModelSolarPanelAvaritia();

    public static void renderStack(@Nonnull ItemStack stack, TransformType transformType) {
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.rotate(90, 0, -1, 0);
        GlStateManager.translate(0, -1.0F, 0);
        MekanismRenderer.bindTexture(Constants.getResource(Constants.ResourceType.RENDER, "CrystalSolarPanel.png"));
        solarPanelCrystal.render(0.0625F);
    }
}