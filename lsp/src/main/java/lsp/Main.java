/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

/**
 *
 * @author Alexandr
 */
public class Main {

    public static String serverIP = "213.160.150.180";
    public static int port = 7855;

    public static void main(String[] args) {
        if (args[0] != null) {
            serverIP = args[0];
        }
        if (args[1] != null) {
            port = Integer.parseInt(args[1]);
        }

        Server server = new Server(serverIP, port);
        new Thread(server).start();
    }
}
