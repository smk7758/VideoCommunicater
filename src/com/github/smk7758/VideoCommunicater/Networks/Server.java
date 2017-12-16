package com.github.smk7758.VideoCommunicater.Networks;

import java.io.Closeable;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.github.smk7758.VideoCommunicater.Screens.MainController;

public class Server extends Thread implements Closeable {
	ServerSocket server_socket = null;
	Socket socket = null;
	short port = 0;
	boolean accepted = false;
	MainController mctr = null;
	Send send = null;
	Receive receive = null;

	public Server(short port, MainController mctr) {
		this.setDaemon(true);
		this.port = port;
		this.mctr = mctr;
		try {
			server_socket = new ServerSocket(port);
		} catch (BindException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			if (server_socket == null || server_socket.isClosed() || server_socket.isBound()) return;
			socket = server_socket.accept();
			accepted = true;
			System.out.println("Adress from: " + socket.getInetAddress());
			send = new Send(socket.getOutputStream(), mctr);
			send.start();
			receive = new Receive(socket.getInputStream(), mctr);
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
