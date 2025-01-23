package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.cleanermenus.CleanerMenusClient;

@Mixin(Screen.class)
public abstract class ScreenMixin {

	@Shadow
	protected abstract void applyBlur();

	@Inject(method = "renderDarkening(Lnet/minecraft/client/gui/DrawContext;IIII)V", at = @At("HEAD"), cancellable = true)
	public void renderDarkeningInject(DrawContext context, int x, int y, int width, int height, CallbackInfo info) {
		if (CleanerMenusClient.CONFIG.disableMainMenuDarkening()) {
			info.cancel();
		}
	}

	@Inject(method = "renderInGameBackground", at = @At("HEAD"), cancellable = true)
	public void renderInGameBackgroundInject(DrawContext context, CallbackInfo info) {
		if (CleanerMenusClient.CONFIG.addInGameMenuBlur()) {
			this.applyBlur();
		}
		info.cancel();
	}

}