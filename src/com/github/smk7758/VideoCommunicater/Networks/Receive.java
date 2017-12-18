package com.github.smk7758.VideoCommunicater.Networks;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.smk7758.VideoCommunicater.Main;

public class Receive extends Thread implements Closeable {
	private DataInputStream dis = null;
	InputStream is = null;
	BufferedReader br = null;
	byte[] byte_array = null;
	boolean is_alive = true;

	public Receive(InputStream is) {
		this.setDaemon(true);
		this.is = is;
		this.dis = new DataInputStream(is);
		this.br = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void run() {
		// TODO: 規約を。
		while (is_alive) {
			receiveCameraData();
			// if (byte_array != null) Main.mctr.setMainImage(byte_array);
		}
	}

	@Override
	public void close() throws IOException {
		this.interrupt();
		is_alive = false;
		dis.close();
	}

	private void receiveCameraData() {
		try {
			if (dis == null) return;
			System.out.println("receive");
			// int data_length = dis.readInt();
			// System.out.println("R");
			// dis.readFully(byte_array, 0, 0); // TODO
			// dis.read
			String a = br.readLine();
			System.out.print(a);
			Main.mctr.addText(a);
		} catch (EOFException ex) {
			System.err.println("Closed receive.");
			this.interrupt();
			is_alive = false;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
