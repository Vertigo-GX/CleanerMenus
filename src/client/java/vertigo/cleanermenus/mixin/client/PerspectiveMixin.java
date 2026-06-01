package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vertigo.cleanermenus.CleanerMenusClient;

@Mixin(CameraType.class)
public abstract class PerspectiveMixin {

	@Shadow
	public abstract boolean isFirstPerson();

	@Inject(method = "cycle", at = @At("HEAD"), cancellable = true)
	public void cycleInject(CallbackInfoReturnable<CameraType> info) {
		if(CleanerMenusClient.CONFIG.disableThirdPersonFrontView) {
			info.setReturnValue(CameraType.values()[isFirstPerson() ? 1 : 0]);
		}
	}

}