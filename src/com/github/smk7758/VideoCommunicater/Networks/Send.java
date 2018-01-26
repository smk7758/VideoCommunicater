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

	public Send(OutputStream os) {
		this.setDaemon(true);
		this.os = os;
		this.dos = new DataOutputStream(os);
	}

	@Override
	public void run() {
		while (is_alive) {
			sendCameraData(this.byte_array);
			System.out.print("+");
			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
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
		// System.out.print(",");
	}

	private void sendCameraData(byte[] byte_array) {
		try {
			// if (byte_array == null) System.out.println("byte array are null in send.");
			if (dos == null || byte_array == null)
				return;
			// if (Main.client == null) return;
			// System.out.print(byte_array.length);
			// dos.writeInt(byte_array.length);
			dos.write(byte_array);
			dos.flush();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
