package chat.util;

public class Log {
    public static final boolean isDebug = true;

    public static void Debug(String txt) {
        if (isDebug) {
            System.out.println("\t\t\t\t\t\t[Debug] " + txt);
        }
    }
}
