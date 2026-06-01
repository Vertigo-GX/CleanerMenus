package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
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
	protected abstract void extractBlurredBackground(GuiGraphicsExtractor extractor);

	@Inject(method = "extractMenuBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIII)V", at = @At("HEAD"), cancellable = true)
	public void extractMenuBackgroundInject(GuiGraphicsExtractor extractor, int x, int y, int width, int height, CallbackInfo info) {
		if(CleanerMenusClient.CONFIG.disableMainMenuDarkening) {
			info.cancel();
		}
	}

	@Inject(method = "extractTransparentBackground", at = @At("HEAD"), cancellable = true)
	public void extractTransparentBackgroundInject(GuiGraphicsExtractor extractor, CallbackInfo info) {
		if(CleanerMenusClient.CONFIG.addInGameMenuBlur) {
			extractBlurredBackground(extractor);
		}
		if(CleanerMenusClient.CONFIG.disableInGameMenuDarkening) {
			info.cancel();
		}
	}

}