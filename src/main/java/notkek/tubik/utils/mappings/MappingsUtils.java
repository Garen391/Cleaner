package notkek.tubik.utils.mappings;

import notkek.tubik.utils.log.LoggerUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MappingsUtils {

    public static Map<String, String> loadMappings(File methodsCsv, File fieldsCsv) {
        Map<String, String> mappings = new HashMap<>();

        try (BufferedReader methodsReader = new BufferedReader(new FileReader(methodsCsv));
             BufferedReader fieldsReader = new BufferedReader(new FileReader(fieldsCsv))) {

            String line;
            while ((line = methodsReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    mappings.put(parts[0], parts[1]);
                }
            }

            while ((line = fieldsReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    mappings.put(parts[0], parts[1]);
                }
            }

        } catch (IOException e) {
            LoggerUtils.log("Ошибка при чтении маппингов из файлов.");

        }

        return mappings;
    }
}
