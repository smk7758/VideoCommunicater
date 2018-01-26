package com.github.smk7758.VideoCommunicater.Screens;

import java.io.ByteArrayInputStream;
import java.net.InetSocketAddress;

import com.github.smk7758.VideoCommunicater.ImageSettable;
import com.github.smk7758.VideoCommunicater.Main;
import com.github.smk7758.VideoCommunicater.Networks.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController implements ImageSettable {
	private boolean is_client = false;
	@FXML
	private Button button_start, button_stop;
	@FXML
	private TextField textfield_address, textfield_port, textfield_cam; // TODO
	@FXML
	private ImageView image_view_main, image_view_sub;
	@FXML
	private TextArea textarea;

	public void initialize() {
		Main.mctr = this;
	}

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
		Main.client = new Client(host);
		Main.client.start();
		is_client = true;
	}

	@FXML
	private void onButtonStop() {
		if (is_client) Main.client.close();
		else Main.server.close();
		System.exit(0);
	}

	@Override
	public void setImage(byte[] image) {
		image_view_sub.setImage(new Image(new ByteArrayInputStream(image)));
	}

	public void setMainImage(byte[] image) {
		image_view_main.setImage(new Image(new ByteArrayInputStream(image)));
	}

	public void addText(String text) {
		textarea.appendText(text);
	}

	public int getCam() {
		return Integer.parseInt(textfield_cam.getText());
	}

	public boolean isCamEmpty() {
		if (textfield_cam == null || textfield_cam.getText() == null || textfield_cam.getText().isEmpty()) return false;
		else return true;
	}
}
