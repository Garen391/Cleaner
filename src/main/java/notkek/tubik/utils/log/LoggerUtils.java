package notkek.tubik.utils.log;

import static notkek.tubik.MappingEditorGUI.logArea;

public class LoggerUtils {
    public static void log(String message) {
        logArea.appendText(message + "\n");
    }
}
