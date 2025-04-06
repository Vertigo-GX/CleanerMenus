package vertigo.cleanermenus;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

	private static final int BUTTON_WIDTH = 310;
	private static final int BUTTON_HEIGHT = 20;

	private final Screen PARENT;

	private boolean modified = false;

	protected ConfigScreen(Screen parent) {
		super(Text.literal("cleaner-menus.options"));
		this.PARENT = parent;
	}

	@Override
	protected void init() {
		ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
		layout.addHeader(new TextWidget(Text.translatable("cleaner-menus.text.optionsTitle"), this.textRenderer));
		GridWidget grid = new GridWidget();
		grid.setRowSpacing(5);
		GridWidget.Adder adder = grid.createAdder(1);
		adder.add(createToggleButton("addInGameMenuBlur", CleanerMenusClient.CONFIG.addInGameMenuBlur, b -> {
			setToggleButtonMessage(b, "addInGameMenuBlur", CleanerMenusClient.CONFIG.addInGameMenuBlur ^= true);
		}));
		adder.add(createToggleButton("disableInGameMenuDarkening", CleanerMenusClient.CONFIG.disableInGameMenuDarkening, b -> {
			setToggleButtonMessage(b, "disableInGameMenuDarkening", CleanerMenusClient.CONFIG.disableInGameMenuDarkening ^= true);
		}));
		adder.add(createToggleButton("disableMainMenuDarkening", CleanerMenusClient.CONFIG.disableMainMenuDarkening, b -> {
			setToggleButtonMessage(b, "disableMainMenuDarkening", CleanerMenusClient.CONFIG.disableMainMenuDarkening ^= true);
		}));
		adder.add(createToggleButton("disableThirdPersonFrontView", CleanerMenusClient.CONFIG.disableThirdPersonFrontView, b -> {
			setToggleButtonMessage(b, "disableThirdPersonFrontView", CleanerMenusClient.CONFIG.disableThirdPersonFrontView ^= true);
		}));
		layout.addBody(grid);
		layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, b -> {
			close();
		}).build());
		layout.forEachChild(this::addDrawableChild);
		layout.refreshPositions();
	}

	@Override
	public void close() {
		if (modified) {
			CleanerMenusClient.CONFIG.write();
		}
		this.client.setScreen(this.PARENT);
	}

	private ButtonWidget createToggleButton(String key, boolean value, ButtonWidget.PressAction action) {
		return ButtonWidget.builder(ScreenTexts.composeToggleText(Text.translatable("cleaner-menus.option." + key), value), action).tooltip(Tooltip.of(Text.translatable("cleaner-menus.tooltip." + key))).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
	}

	private void setToggleButtonMessage(ButtonWidget button, String key, boolean value) {
		button.setMessage(ScreenTexts.composeToggleText(Text.translatable("cleaner-menus.option." + key), value));
		modified = true;
	}

}