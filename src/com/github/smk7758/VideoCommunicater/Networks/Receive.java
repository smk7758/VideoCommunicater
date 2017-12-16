package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Receive extends Thread implements Closeable {
	private DataInputStream dis = null;
	InputStream is = null;
	MainController mctr = null;

	public Receive(InputStream is, MainController mctr) {
		this.setDaemon(true);
		this.is= is;
		this.dis = new DataInputStream(is);
		this.mctr = mctr;
	}

	public void run() {
		// TODO: 規約を。
		byte data = 0;
		do {
			try {
				data = dis.readByte();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (data != 0);
		//TODO: どうする。
//		mctr.image_view_main.setImage(new Image(data));
	}

	@Override
	public void close() throws IOException {
		dis.close();
	}
}
