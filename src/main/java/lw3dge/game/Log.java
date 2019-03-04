package lw3dge.game;

public class Log {
	public static final int LOG_OFF = 0;
	public static final int LOG_VERBOSE = 1;
	public static final int LOG_SIMPLE = 2;

	public static enum LogLevel {
		DEBUG, WARNING, FATAL
	}

	private static String[] urgency_headers = { "[ Debug ]", "[Warning]", "[ FATAL ]" };

	private static void println(LogLevel urgency, String caller, String msg) {
		if (Config.DEBUG_MODE == LOG_VERBOSE)
			System.out.println(urgency_headers[urgency.ordinal()] + "[" + caller + "]\t" + msg);
		else if (Config.DEBUG_MODE == LOG_SIMPLE) {
			String prio = "";
			if (urgency != LogLevel.DEBUG) prio = urgency_headers[urgency.ordinal()];
			System.out.println(prio + "[" + caller + "]\t" + msg);
		}
	}

	public static void println(LogLevel urgency, String msg) {
		if (Config.DEBUG_MODE == LOG_OFF)
			return;
		String caller = DebugNamer.getCallerCallerClassName();
		if (caller == null)
			println(urgency, "ANONYMOUS", msg);
		else
			println(urgency, caller, msg);
	}

	private static void println(String caller, String msg) {
		println(LogLevel.DEBUG, caller, msg);
	}

	public static void println(String msg) {
		if (Config.DEBUG_MODE == LOG_OFF)
			return;
		String caller = DebugNamer.getCallerCallerClassName();
		if (caller == null) {
			println("ANONYMOUS", msg);
		} else {
			println(caller, msg);
		}
	}
}

class DebugNamer {
	static String getCallerCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		String callerClassName = null;
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(DebugNamer.class.getName())
					&& ste.getClassName().indexOf("java.lang.Thread") != 0) {
				if (callerClassName == null) {
					callerClassName = ste.getClassName();
				} else if (!callerClassName.equals(ste.getClassName())) {
					String full_name = ste.getClassName();
					String pckg = full_name.substring(0, full_name.indexOf("."));
					full_name = reverse(full_name);
					String class_title = full_name.substring(0, full_name.indexOf("."));
					class_title = reverse(class_title);
					return (Config.DEBUG_MODE == Log.LOG_VERBOSE)
							? (pckg.equals("lw3dge") ? "Engine" : pckg) + ":" + class_title
							: (pckg.equals("lw3dge") ? "E" : "*");
				}
			}
		}
		return null;
	}

	static String reverse(String str) {
		String res = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			res += str.charAt(i);
		}
		return res;
	}
}