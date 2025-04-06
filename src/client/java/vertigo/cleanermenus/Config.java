package vertigo.cleanermenus;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {

	private static final String SEPARATOR = " = ";
	private static final String ADD_IN_GAME_MENU_BLUR = "addInGameMenuBlur";
	private static final String REMOVE_MAIN_MENU_DARKENING = "removeMainMenuDarkening";

	public boolean addInGameMenuBlur = true;
	public boolean removeMainMenuDarkening = false;

	public Config() {
		if (!read()) {
			write();
		}
	}

	public void write() {
		File file = getFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(ADD_IN_GAME_MENU_BLUR + SEPARATOR + addInGameMenuBlur + System.lineSeparator());
			writer.write(REMOVE_MAIN_MENU_DARKENING + SEPARATOR + removeMainMenuDarkening);
		} catch (IOException e) {
			CleanerMenusClient.LOGGER.error("Failed to write config ({})", file.getPath());
		}
	}

	public boolean read() {
		File file = getFile();
		if (!file.exists()) {
			return false;
		}
		try (BufferedReader reader = new BufferedReader((new FileReader(file)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] segments = line.split(SEPARATOR);
				if (segments.length != 2 || segments[0].isEmpty() || segments[1].isEmpty()) {
					continue;
				}
				switch (segments[0]) {
					case ADD_IN_GAME_MENU_BLUR -> addInGameMenuBlur = segments[1].equals("true");
					case REMOVE_MAIN_MENU_DARKENING -> removeMainMenuDarkening = segments[1].equals("true");
				}
			}
		} catch (IOException e) {
			CleanerMenusClient.LOGGER.error("Failed to read config ({})", file.getPath());
		}
		return true;
	}

	private File getFile() {
		return FabricLoader.getInstance().getGameDir().resolve("config").resolve(CleanerMenusClient.MOD_ID + ".ini").toFile();
	}

}