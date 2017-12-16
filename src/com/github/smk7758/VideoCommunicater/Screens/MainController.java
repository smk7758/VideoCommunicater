package com.github.smk7758.VideoCommunicater.Screens;

import java.net.InetSocketAddress;

import com.github.smk7758.VideoCommunicater.Main;
import com.github.smk7758.VideoCommunicater.Networks.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainController {
	private boolean is_client = false;
	@FXML
	Button button_start, button_stop;
	@FXML
	TextField textfield_address, textfield_port; // TODO
	@FXML
	public ImageView image_view_main, image_view_sub;

	@FXML
	private void onButtonStart() {
		if (textfield_address == null || textfield_address.getText().isEmpty()
				|| textfield_port == null || textfield_address.getText().isEmpty()) return;
		Main.server.close();
		String address = textfield_address.getText();
		short port = 0;
		try {
			port = Short.parseShort(textfield_port.getText());
		} catch (NumberFormatException ex) {
			System.err.println("Bad port number.");
		}
		InetSocketAddress host = new InetSocketAddress(address, port);
		Main.client = new Client(host, this);
		Main.client.start();
		is_client = true;
	}

	@FXML
	private void onButtonStop() {
		if (is_client) Main.client.close();
		else Main.server.close();
		System.exit(0);
	}
}
