package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.github.smk7758.VideoCommunicater.Capture;
import com.github.smk7758.VideoCommunicater.Main;

public class Client extends Thread implements Closeable {
	SocketChannel socket = null;
	Send send = null;
	Receive receive = null;
	Capture capture = null;

	public Client(InetSocketAddress host) {
		try {
			socket = SocketChannel.open(host);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("start run in Client");
			System.out.println("Connect: " + socket.socket().getRemoteSocketAddress().toString());
			Main.camera_number = Main.mctr.isCamEmpty() ? Main.mctr.getCam() : Main.camera_number;
			send = new Send(socket.socket().getOutputStream());
			capture = new Capture(Main.camera_number, send);
			receive = new Receive(socket.socket().getInputStream());
			capture.start();
			send.start();
			receive.start();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			if (send != null) send.close();
			if (receive != null) receive.close();
			if (socket != null) socket.close();
			Main.client = null;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.exit(1);
		}
	}

}
