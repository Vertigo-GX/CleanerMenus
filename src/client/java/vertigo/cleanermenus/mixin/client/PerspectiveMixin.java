package vertigo.cleanermenus.mixin.client;

import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vertigo.cleanermenus.CleanerMenusClient;

@Mixin(Perspective.class)
public abstract class PerspectiveMixin {

	@Shadow
	public abstract boolean isFirstPerson();

	@Inject(method = "next", at = @At("HEAD"), cancellable = true)
	public void next(CallbackInfoReturnable<Perspective> info) {
		if (CleanerMenusClient.CONFIG.disableThirdPersonFrontView) {
			info.setReturnValue(Perspective.values()[this.isFirstPerson() ? 1 : 0]);
		}
	}

}