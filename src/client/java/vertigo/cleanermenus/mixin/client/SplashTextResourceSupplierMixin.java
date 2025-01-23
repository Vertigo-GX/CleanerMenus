package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vertigo.cleanermenus.CleanerMenusClient;

@Mixin(SplashTextResourceSupplier.class)
public abstract class SplashTextResourceSupplierMixin {

	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	public void getInject(CallbackInfoReturnable<SplashTextRenderer> info) {
		if (CleanerMenusClient.CONFIG.disableSplashText()) {
			info.setReturnValue(null);
		}
	}

}