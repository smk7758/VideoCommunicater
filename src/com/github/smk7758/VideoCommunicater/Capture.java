package com.github.smk7758.VideoCommunicater;

import java.io.Closeable;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Capture extends Thread implements Closeable {
	VideoCapture vc = null;
	int camera_number = 0;
	Mat mat = new Mat();
	ImageSettable imageset = null;

	public Capture(int camera_number, ImageSettable imageset) {
		this.setDaemon(true);
		this.camera_number = camera_number;
		this.imageset = imageset;
		vc = new VideoCapture(camera_number);
		vc.open(camera_number);
	}

	@Override
	public void run() {
		MatOfByte byte_mat = null;
		// TODO: リファクタリング
		while (vc.isOpened()) {
			vc.read(mat);
			if (!mat.empty()) {
				// mat → image
				byte_mat = new MatOfByte();
				Imgcodecs.imencode(".bmp", mat, byte_mat);
				if (byte_mat != null) imageset.setImage(byte_mat.toArray());
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
