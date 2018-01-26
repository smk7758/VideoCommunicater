package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.github.smk7758.VideoCommunicater.Capture;
import com.github.smk7758.VideoCommunicater.Main;
import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Server extends Thread implements Closeable {
	ServerSocketChannel server_socket = null;
	SocketChannel socket = null;
	short port = 0;
	boolean accepted = false;
	MainController mctr = null;
	Send send = null;
	Receive receive = null;
	Capture capture = null;

	public Server(short port) {
		this.setDaemon(true);
		this.port = port;
		try {
			server_socket = ServerSocketChannel.open();
			server_socket.socket().bind(new InetSocketAddress(port));
		} catch (BindException e) {
			System.err.println(e.getMessage() + System.lineSeparator());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("start run in Server");
			if (server_socket == null)
				return;
			System.out.println("before accept");
			if (!server_socket.isOpen() || !server_socket.socket().isBound())
				return;
			socket = server_socket.accept();
			System.out.println("after accept");
			accepted = true;
			Main.camera_number = Main.mctr.isCamEmpty() ? Main.mctr.getCam() : Main.camera_number;
			System.out.println("Adress from: " + socket.socket().getRemoteSocketAddress());
			send = new Send(socket.socket().getOutputStream());
			capture = new Capture(Main.camera_number, send);
			receive = new Receive(socket.socket().getInputStream());
			capture.start();
			send.start();
			receive.start();
		} catch (SocketException ex) {
			System.err.println("Server socket Closed.");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			if (send != null)
				send.close();
			if (receive != null)
				receive.close();
			if (socket != null)
				socket.close();
			if (server_socket != null)
				server_socket.close();
			Main.server = null;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.exit(1);
		}
	}
}
