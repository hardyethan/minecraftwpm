package git.hardyethan.wpm.render;

import git.hardyethan.wpm.mixin.ChatScreenMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class WPMRenderer {

    public static int wpm = -1;

    public static void render(MatrixStack matrices) {
        if (wpm == -1) {
            return;
        }
        
        if (MinecraftClient.getInstance().currentScreen != null) {
            DrawableHelper.drawStringWithShadow(matrices, MinecraftClient.getInstance().textRenderer, Integer.toString(wpm), 5, MinecraftClient.getInstance().currentScreen.height - 24, 0xFFFFFF);
        }
    }

}
