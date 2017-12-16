package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import com.github.smk7758.VideoCommunicater.Main;
import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Send extends Thread implements Closeable {
	OutputStream os = null;
	MainController mctr = null;
	Capture capture = null;

	public Send(OutputStream os, MainController mctr) {
		this.setDaemon(true);
		this.os = os;
		this.mctr = mctr;
		this.capture = new Capture(os, Main.camera_number, mctr);
	}

	@Override
	public void run() {
		capture.start();
	}

	@Override
	public void close() throws IOException {
		os.close();
		capture.close();
	}
}
