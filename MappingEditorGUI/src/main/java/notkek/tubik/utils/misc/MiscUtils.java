package notkek.tubik.utils.misc;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import notkek.tubik.utils.file.FileUtils;
import notkek.tubik.utils.log.LoggerUtils;

import java.io.*;
import java.util.Map;

import static notkek.tubik.MappingEditorGUI.javaDirectoryField;
import static notkek.tubik.MappingEditorGUI.*;
import static notkek.tubik.utils.config.ConfigUtils.saveConfigProperties;
import static notkek.tubik.utils.mappings.MappingsUtils.*;

public class MiscUtils {
    public static VBox createCustomTabContent() {
        Label javaDirectoryLabel = new Label("Путь к .java файлам:");
        javaDirectoryField = new TextField();
        javaDirectoryField.setPrefWidth(300);
        Button browseJavaDirectoryButton = new Button("Обзор");
        browseJavaDirectoryButton.setOnAction(e -> chooseJavaDirectory());

        Label mappingsDirectoryLabel = new Label("Путь к директории с маппингами:");
        mappingsDirectoryField = new TextField();
        mappingsDirectoryField.setPrefWidth(300);
        Button browseMappingsDirectoryButton = new Button("Обзор");
        browseMappingsDirectoryButton.setOnAction(e -> chooseMappingsDirectory());

        Button processButton = new Button("Обработать");
        processButton.setOnAction(e -> processCustomMappings());

        logArea = new TextArea();
        logArea.setPrefHeight(200);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
                javaDirectoryLabel, javaDirectoryField, browseJavaDirectoryButton,
                mappingsDirectoryLabel, mappingsDirectoryField, browseMappingsDirectoryButton,
                processButton, logArea
        );

        return vbox;
    }
    public static void chooseJavaDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию с .java файлами");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            javaDirectoryField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    public static void chooseMappingsDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию с маппингами");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            mappingsDirectoryField.setText(selectedDirectory.getAbsolutePath());
        }
    }
    public static void processCustomMappings() {
        String javaDirectoryPath = javaDirectoryField.getText();
        String mappingsDirectoryPath = mappingsDirectoryField.getText();

        if (javaDirectoryPath.isEmpty() || mappingsDirectoryPath.isEmpty()) {
            LoggerUtils.log("Пожалуйста, укажите пути ко всем директориям и файлам.");
            return;
        }

        File javaDirectory = new File(javaDirectoryPath);
        File mappingsDirectory = new File(mappingsDirectoryPath);

        if (!javaDirectory.isDirectory() || !mappingsDirectory.isDirectory()) {
            LoggerUtils.log("Убедитесь, что указанные директории существуют.");
            return;
        }

        File customMethodsCsv = new File(mappingsDirectory, "methods.csv");
        File customFieldsCsv = new File(mappingsDirectory, "fields.csv");

        if (!customMethodsCsv.isFile() || !customFieldsCsv.isFile()) {
            LoggerUtils.log("Убедитесь, что файлы маппингов существуют в указанной директории.");
            return;
        }

        Map<String, String> mappingsToReplace = loadMappings(customMethodsCsv, customFieldsCsv);

        if (mappingsToReplace.isEmpty()) {
            LoggerUtils.log("Не удалось загрузить маппинги из файлов.");
            return;
        }

        FileUtils.processJavaFiles(javaDirectory, mappingsToReplace);
        LoggerUtils.log("Завершено!");
        saveConfigProperties();
    }

}
