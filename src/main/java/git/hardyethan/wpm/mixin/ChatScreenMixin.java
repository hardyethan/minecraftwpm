package git.hardyethan.wpm.mixin;

import git.hardyethan.wpm.render.WPMRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    private long lastUpdated = -1;
    private long firstUpdate = -1;
    private String startingText;


    @Inject(at = @At("HEAD"), method = "onChatFieldUpdate")
    private void onChatFieldUpdate(String newText, CallbackInfo ci) {
        if (newText.length() == 0) {
            firstUpdate = -1;
            lastUpdated = -1;
            startingText = "";
            WPMRenderer.wpm = -1;
            return;
        }
        long newUpdate = System.currentTimeMillis();
        if (lastUpdated == -1) {
            firstUpdate = newUpdate;
            lastUpdated = newUpdate;
            startingText = newText;
            return;
        }
        if (newUpdate - lastUpdated < 1000) {
            return;
        }

        lastUpdated = System.currentTimeMillis();
        double charTextLength = (double) (newText.length() - startingText.length()) / 5;
        WPMRenderer.wpm = (int) (charTextLength / ((int) (lastUpdated / 1000 - firstUpdate / 1000)) * 60);

    }

    @Inject(at = @At(value = "RETURN"), method = "keyPressed")
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable ci) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            firstUpdate = -1;
            lastUpdated = -1;
            startingText = "";
            WPMRenderer.wpm = -1;
        }
    }
}