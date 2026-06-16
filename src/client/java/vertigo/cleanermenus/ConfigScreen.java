package vertigo.cleanermenus;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {

	private static final int BUTTON_WIDTH = 310;

	private static final int BUTTON_HEIGHT = 20;

	private final Screen PARENT;

	private boolean modified = false;

	protected ConfigScreen(Screen parent) {
		super(Component.literal("cleaner-menus.options"));
		this.PARENT = parent;
	}

	@Override
	protected void init() {
		HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
		layout.addToHeader(new StringWidget(Component.translatable("cleaner-menus.text.optionsTitle"), this.font));
		GridLayout grid = new GridLayout();
		grid.rowSpacing(5);
		GridLayout.RowHelper adder = grid.createRowHelper(1);
		adder.addChild(createToggleButton("addInGameMenuBlur", CleanerMenusClient.CONFIG.addInGameMenuBlur, b -> setToggleButtonMessage(b, "addInGameMenuBlur", CleanerMenusClient.CONFIG.addInGameMenuBlur ^= true)));
		adder.addChild(createToggleButton("disableInGameMenuDarkening", CleanerMenusClient.CONFIG.disableInGameMenuDarkening, b -> setToggleButtonMessage(b, "disableInGameMenuDarkening", CleanerMenusClient.CONFIG.disableInGameMenuDarkening ^= true)));
		adder.addChild(createToggleButton("disableMainMenuDarkening", CleanerMenusClient.CONFIG.disableMainMenuDarkening, b -> setToggleButtonMessage(b, "disableMainMenuDarkening", CleanerMenusClient.CONFIG.disableMainMenuDarkening ^= true)));
		adder.addChild(createToggleButton("disableThirdPersonFrontView", CleanerMenusClient.CONFIG.disableThirdPersonFrontView, b -> setToggleButtonMessage(b, "disableThirdPersonFrontView", CleanerMenusClient.CONFIG.disableThirdPersonFrontView ^= true)));
		layout.addToContents(grid);
		layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).build());
		layout.visitWidgets(this::addRenderableWidget);
		layout.arrangeElements();
	}

	@Override
	public void onClose() {
		if(modified) {
			CleanerMenusClient.CONFIG.write();
		}
		this.minecraft.gui.setScreen(this.PARENT);
	}

	private Button createToggleButton(String key, boolean value, Button.OnPress action) {
		return Button.builder(CommonComponents.optionStatus(Component.translatable("cleaner-menus.option." + key), value), action).tooltip(Tooltip.create(Component.translatable("cleaner-menus.tooltip." + key))).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
	}

	private void setToggleButtonMessage(Button button, String key, boolean value) {
		button.setMessage(CommonComponents.optionStatus(Component.translatable("cleaner-menus.option." + key), value));
		modified = true;
	}

}