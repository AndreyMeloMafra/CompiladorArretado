package utils;

public class OsUtils {
    private String OS = null;

    public String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public boolean isWindows() {
        return getOsName().startsWith("Windows");
    }
}