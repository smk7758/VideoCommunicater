package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.github.smk7758.VideoCommunicater.Capture;
import com.github.smk7758.VideoCommunicater.Main;
import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Server extends Thread implements Closeable {
	ServerSocket server_socket = null;
	Socket socket = null;
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
			server_socket = new ServerSocket(port);
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
			if (server_socket == null) return;
			System.out.println("before accept");
			socket = server_socket.accept();
			System.out.println("after accept");
			accepted = true;
			System.out.println("Adress from: " + socket.getInetAddress());
			send = new Send(socket.getOutputStream());
			capture = new Capture(Main.camera_number, send);
			receive = new Receive(socket.getInputStream());
			send.start();
			capture.start();
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
			if (send != null) send.close();
			if (receive != null) receive.close();
			if (socket != null) socket.close();
			if (server_socket != null) server_socket.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.exit(1);
		}
	}
}
