package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Client extends Thread implements Closeable {
	Socket socket = null;
	MainController mctr = null;
	Send send = null;
	Receive receive = null;

	public Client(InetSocketAddress host, MainController mctr) {
		this.mctr = mctr;
		try {
			socket = new Socket(host.getHostName(), host.getPort());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			send = new Send(socket.getOutputStream(), mctr);
			send.start();
			receive = new Receive(socket.getInputStream(), mctr);
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
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.exit(1);
		}
	}

}
