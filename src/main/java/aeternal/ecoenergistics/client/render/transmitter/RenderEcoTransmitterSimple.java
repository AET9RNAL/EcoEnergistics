package aeternal.ecoenergistics.client.render.transmitter;

import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoTransmitter;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;

public abstract class RenderEcoTransmitterSimple<T extends TileEntityEcoTransmitter> extends RenderEcoTransmitterBase<T>  {

    protected abstract void renderSide(BufferBuilder renderer, EnumFacing side, T transmitter);

    protected void render(T transmitter, double x, double y, double z, int glow) {
        GlStateManager.pushMatrix();
        GlStateManager.enableCull();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

        for (EnumFacing side : EnumFacing.VALUES) {
            renderSide(worldRenderer, side, transmitter);
        }

        MekanismRenderer.GlowInfo glowInfo = MekanismRenderer.enableGlow(glow);
        tessellator.draw();
        MekanismRenderer.disableGlow(glowInfo);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.popMatrix();
    }
}
