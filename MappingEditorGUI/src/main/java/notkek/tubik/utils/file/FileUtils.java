package notkek.tubik.utils.file;

import java.io.*;
import java.util.Map;

import static notkek.tubik.utils.log.LoggerUtils.log;

public class FileUtils {
    public static void processJavaFiles(File directory, Map<String, String> mappingsToReplace) {
        File[] javaFiles = directory.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles != null) {
            for (File javaFile : javaFiles) {
                try {
                    String content = readFile(javaFile);
                    for (Map.Entry<String, String> entry : mappingsToReplace.entrySet()) {
                        content = content.replace(entry.getKey(), entry.getValue());
                    }
                    writeFile(javaFile, content);
                } catch (IOException e) {
                    log("Ошибка при обработке .java файла: " + javaFile.getName());

                }
            }
        }
    }

    public static String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static void writeFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
}
