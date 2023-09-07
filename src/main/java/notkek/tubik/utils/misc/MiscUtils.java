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
        Label javaDirectoryLabel = new Label("���� � .java ������:");
        javaDirectoryField = new TextField();
        javaDirectoryField.setPrefWidth(300);
        Button browseJavaDirectoryButton = new Button("�����");
        browseJavaDirectoryButton.setOnAction(e -> chooseJavaDirectory());

        Label mappingsDirectoryLabel = new Label("���� � ���������� � ����������:");
        mappingsDirectoryField = new TextField();
        mappingsDirectoryField.setPrefWidth(300);
        Button browseMappingsDirectoryButton = new Button("�����");
        browseMappingsDirectoryButton.setOnAction(e -> chooseMappingsDirectory());

        Button processButton = new Button("����������");
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
        directoryChooser.setTitle("�������� ���������� � .java �������");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            javaDirectoryField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    public static void chooseMappingsDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("�������� ���������� � ����������");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            mappingsDirectoryField.setText(selectedDirectory.getAbsolutePath());
        }
    }
    public static void processCustomMappings() {
        String javaDirectoryPath = javaDirectoryField.getText();
        String mappingsDirectoryPath = mappingsDirectoryField.getText();

        if (javaDirectoryPath.isEmpty() || mappingsDirectoryPath.isEmpty()) {
            LoggerUtils.log("����������, ������� ���� �� ���� ����������� � ������.");
            return;
        }

        File javaDirectory = new File(javaDirectoryPath);
        File mappingsDirectory = new File(mappingsDirectoryPath);

        if (!javaDirectory.isDirectory() || !mappingsDirectory.isDirectory()) {
            LoggerUtils.log("���������, ��� ��������� ���������� ����������.");
            return;
        }

        File customMethodsCsv = new File(mappingsDirectory, "methods.csv");
        File customFieldsCsv = new File(mappingsDirectory, "fields.csv");

        if (!customMethodsCsv.isFile() || !customFieldsCsv.isFile()) {
            LoggerUtils.log("���������, ��� ����� ��������� ���������� � ��������� ����������.");
            return;
        }

        Map<String, String> mappingsToReplace = loadMappings(customMethodsCsv, customFieldsCsv);

        if (mappingsToReplace.isEmpty()) {
            LoggerUtils.log("�� ������� ��������� �������� �� ������.");
            return;
        }

        FileUtils.processJavaFiles(javaDirectory, mappingsToReplace);
        LoggerUtils.log("���������!");
        saveConfigProperties();
    }

}
