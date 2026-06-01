package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.cleanermenus.CleanerMenusClient;

@Mixin(Screen.class)
public abstract class ScreenMixin {

	@Shadow
	protected abstract void renderBlurredBackground(GuiGraphics graphics);

	@Inject(method = "renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;IIII)V", at = @At("HEAD"), cancellable = true)
	public void renderMenuBackgroundInject(GuiGraphics graphics, int x, int y, int width, int height, CallbackInfo info) {
		if(CleanerMenusClient.CONFIG.disableMainMenuDarkening) {
			info.cancel();
		}
	}

	@Inject(method = "renderTransparentBackground", at = @At("HEAD"), cancellable = true)
	public void renderTransparentBackgroundInject(GuiGraphics graphics, CallbackInfo info) {
		if(CleanerMenusClient.CONFIG.addInGameMenuBlur) {
			renderBlurredBackground(graphics);
		}
		if(CleanerMenusClient.CONFIG.disableInGameMenuDarkening) {
			info.cancel();
		}
	}

}