package notkek.tubik;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import notkek.tubik.utils.config.ConfigUtils;
import notkek.tubik.utils.misc.MiscUtils;

import java.util.Properties;

public class MappingEditorGUI extends Application {
    public static final String CONFIG_FILE_NAME = "config.properties";
    public static Properties configProperties;

    public static TextField javaDirectoryField;
    public static TabPane tabPane;
    public static TextArea logArea;
    public static TextField mappingsDirectoryField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        configProperties = ConfigUtils.loadConfigProperties();

        primaryStage.setTitle("Mapping Cleaner");
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabCustom = new Tab("Custom");

        tabPane.getTabs().addAll(tabCustom);

        tabCustom.setContent(MiscUtils.createCustomTabContent());

        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        javaDirectoryField.setText(configProperties.getProperty("javaDirectory", ""));
        mappingsDirectoryField.setText(configProperties.getProperty("mappingsDirectory", ""));
    }

}
