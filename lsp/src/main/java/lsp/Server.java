/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import lsp.dao.FilialsJPA;
import lsp.dao.LogJPA;
import lsp.entity.Filials;

/**
 *
 * @author Alexandr
 */
public final class Server implements Runnable {

    private final String serverIP;
    private final int port;
    private ServerSocket serverSocket = null;
    private boolean isRun = true;

    public Server(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
        startPING();
        startCheck();
    }

    @Override
    public void run() {
        new LogJPA().addLog("localhost", "Started server");
        try {
            this.serverSocket = new ServerSocket(this.port, 0, InetAddress.getByName(this.serverIP));
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.port, e);
        }
        while (this.isRun) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (!this.isRun) {
                    new LogJPA().addLog("localhost", "Server stopped");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(
                    new Client(clientSocket)
            ).start();
        }
        new LogJPA().addLog("localhost", "Server stopped");
    }

    public void startPING() {
        FilialsJPA dao = new FilialsJPA();
        try {
            for (Filials f : dao.getAllFilials()) {
                new Thread(
                        new Ping(f)
                ).start();
            }
        } catch (Exception e) {

        }
    }

    public void startCheck() {
        new Thread(
                new CheckTable()
        ).start();
    }

    public void stopServer() {
        this.isRun = false;
    }

}
