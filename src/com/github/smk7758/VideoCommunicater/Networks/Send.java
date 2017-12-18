package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.smk7758.VideoCommunicater.ImageSettable;
import com.github.smk7758.VideoCommunicater.Main;

public class Send extends Thread implements Closeable, ImageSettable {
	OutputStream os = null;
	DataOutputStream dos = null;
	byte[] byte_array = null;
	boolean is_alive = true;
	// boolean is_send = false;

	public Send(OutputStream os) {
		this.setDaemon(true);
		this.os = os;
		this.dos = new DataOutputStream(os);
	}

	@Override
	public void run() {
		while (is_alive) {
			// if (is_send) {
			sendCameraData(this.byte_array);
			// is_send = false;
			// System.out.print("is_send");
			// }
		}
	}

	@Override
	public void close() throws IOException {
		this.interrupt();
		is_alive = false;
		dos.close();
		os.close();
	}

	@Override
	public void setImage(byte[] byte_array) {
		// is_send = true;
		this.byte_array = byte_array;
		Main.mctr.setImage(byte_array);
	}

	private void sendCameraData(byte[] byte_array) {
		try {
			if (byte_array == null) System.out.println("byte is null in send.");
			if (dos == null || byte_array == null) return;
			System.out.println("seCD@Send.");
			// dos.writeInt(byte_array.length);
			dos.writeBytes(byte_array.toString() + "\n");
			dos.flush();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
