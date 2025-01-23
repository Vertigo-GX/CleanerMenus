package vertigo.cleanermenus;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Config(name = "cleaner-menus", wrapperName = "ModConfig")
@Modmenu(modId = "cleaner-menus")
public class ConfigModel {

	public boolean addInGameMenuBlur = true;
	public boolean disableMainMenuDarkening = false;

}