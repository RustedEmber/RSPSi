package rspsi.io;

public class SaveManager {
    private static boolean saveEnabled = false;

    public static boolean isSaveEnabled() {
        return saveEnabled;
    }

    public static void setSaveEnabled(boolean enabled) {
        saveEnabled = enabled;
    }
}