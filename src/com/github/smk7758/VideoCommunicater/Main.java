package com.github.smk7758.VideoCommunicater;

import java.io.IOException;

import org.opencv.core.Core;

import com.github.smk7758.VideoCommunicater.Networks.Client;
import com.github.smk7758.VideoCommunicater.Networks.Server;
import com.github.smk7758.VideoCommunicater.Screens.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static final String program_name = "VC";
	public static final String fxml_url = "Screens/Main.fxml";
	public static Stage primary_stage = null; // why?
	public static boolean debug_mode = false; // for Debug.
	private static MainController mctr = null;
	public static short port = 25565; // TODO: 設定ファイル
	public static int camera_number = 1;
	public static Server server = null;
	public static Client client = null;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_url));
			mctr = (MainController) loader.getController();
			Scene scene = new Scene(loader.load());
			// Set Title
			primaryStage.setTitle(program_name);
			// Set Window
			primaryStage.setResizable(false);
			// Set Scene
			primaryStage.setScene(scene);
			primaryStage.show();
			server = new Server(port, mctr);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
