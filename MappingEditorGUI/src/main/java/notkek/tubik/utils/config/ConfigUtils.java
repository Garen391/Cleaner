package notkek.tubik.utils.config;

import java.io.*;
import java.util.Properties;

import static notkek.tubik.MappingEditorGUI.*;

public class ConfigUtils {
    public static Properties loadConfigProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
        }
        return properties;
    }

    public static void saveConfigProperties() {
        configProperties.setProperty("javaDirectory", javaDirectoryField.getText());
        configProperties.setProperty("mappingsDirectory", mappingsDirectoryField.getText());

        try (OutputStream outputStream = new FileOutputStream(CONFIG_FILE_NAME)) {
            configProperties.store(outputStream, "Mapping Editor Configuration");
        } catch (IOException e) {
        }
    }
}
