package wpcg.base;

/**
 * Generic logger for all platforms
 */
public class Logger {

    /**
     * Logging level
     */
    public enum Level {
        // All messages logged (debug, msg, error, exception)
        ALL,
        // Only msg, error, exception
        MEDIUM,
        // Only error, exception
        ONLY_ERRORS
    }

    /**
     * Singleton instance.
     */
    private static Logger instance = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Logg message (only printed in level = ALL || level = MEDIUM)
     */
    public void msg(String msg) {
        System.out.println(msg);
    }

    /**
     * Logg message (always printed)
     */
    public void error(String msg) {
        System.err.println(msg);
    }


    public void exception(String msg, Exception e) {
        System.out.println("Exception: " + msg + ", " + e.getMessage());
    }

    public void debug(String s) {
    }
}
