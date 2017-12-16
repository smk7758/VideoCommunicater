package com.github.smk7758.VideoCommunicater.Networks;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.github.smk7758.VideoCommunicater.Screens.MainController;

import javafx.scene.image.Image;

public class Capture extends Thread implements Closeable {
	OutputStream os = null;
	VideoCapture vc = null;
	int camera_number = 0;
	Mat mat = new Mat();
	MainController mctr = null;

	public Capture(OutputStream os, int camera_number, MainController mctr) {
		this.setDaemon(true);
		this.os = os;
		this.camera_number = camera_number;
		this.mctr = mctr;
		vc = new VideoCapture(camera_number);
		vc.open(camera_number);
		System.out.println("0");
	}

	@Override
	public void run() {
		MatOfByte byte_mat = null;
		System.out.println("1");
		// TODO: リファクタリング
		while (vc.isOpened()) {
			vc.read(mat);
			if (!mat.empty()) {
				// mat → image
				byte_mat = new MatOfByte();
				Imgcodecs.imencode(".bmp", mat, byte_mat);
				// set ImageView
				mctr.image_view_sub.setImage(new Image(new ByteArrayInputStream(byte_mat.toArray())));
				System.out.println("A");
				// TODO: どこから取得する？受け取る？取りに行く？これでいい？
				// try {
				// os.write(byte_mat.toArray());
				// } catch (IOException e) {
				// // TODO 自動生成された catch ブロック
				// e.printStackTrace();
				// }
			} else {
				System.err.println("[Error] Can't capture the camera.");
			}
		}
		System.out.println("Stop.(Capture)");

	}

	@Override
	public void close() throws IOException {
		vc.release();
	}
}
