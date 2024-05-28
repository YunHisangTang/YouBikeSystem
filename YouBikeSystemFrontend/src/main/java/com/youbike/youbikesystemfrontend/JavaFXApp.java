package com.youbike.youbikesystemfrontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 創建WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // 加載初始頁面
        webEngine.load(getClass().getResource("/com/youbike/youbikesystemfrontend/initial.html").toExternalForm());

        // 設置根佈局
        BorderPane root = new BorderPane();
        root.setCenter(webView);

        // 創建場景並設置寬高
        Scene scene = new Scene(root, 800, 600);

        // 設置主舞台的標題並顯示
        primaryStage.setTitle("YouBike System Frontend");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // 啟動JavaFX應用程序
        launch(args);
    }
}
