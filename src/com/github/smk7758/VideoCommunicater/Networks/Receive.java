package com.github.smk7758.VideoCommunicater.Networks;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousCloseException;
import java.time.LocalDateTime;

import com.github.smk7758.VideoCommunicater.Main;

public class Receive extends Thread implements Closeable {
	private DataInputStream dis = null;
	InputStream is = null;
	BufferedReader br = null;
	byte[] byte_array = new byte[4];
	boolean is_alive = true;

	public Receive(InputStream is) {
		this.setDaemon(true);
		this.is = is;
		this.dis = new DataInputStream(is);
		// this.br = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void run() {
		int status = 0;
		try {
			while (is_alive && status != -1) {
				System.out.print("%");
				status = dis.read(byte_array);
				Main.mctr.setImage(byte_array);
				System.out.print("*");
			}
		} catch (AsynchronousCloseException ex) {
			System.err.println("Socket has been closed.");
		} catch (IOException e) {
			System.out.println(LocalDateTime.now());
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// TODO: 規約を。
		// while (is_alive) {
		// receiveCameraData();
		// // if (byte_array != null) Main.mctr.setMainImage(byte_array);
		// }
	}

	@Override
	public void close() throws IOException {
		this.interrupt();
		is_alive = false;
		dis.close();
	}

	// private void receiveCameraData() {
	// try {
	// if (dis == null) return;
	// System.out.println("receive");
	// // int data_length = dis.readInt();
	// // System.out.println("R");
	// // dis.readFully(byte_array, 0, 0); // TODO
	// // dis.read
	// } catch (EOFException ex) {
	// System.err.println("Closed receive.");
	// this.interrupt();
	// is_alive = false;
	// } catch (IOException e) {
	// // TODO 自動生成された catch ブロック
	// e.printStackTrace();
	// }
	// }
}
