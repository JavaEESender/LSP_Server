/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

import java.io.IOException;

/**
 *
 * @author Alexandr
 */
public class Console {

    public boolean Ping(String ip) {
        try {
            String cmd = "ping -S 10.0.25.253 -c 1 " + ip;
            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();
            return myProcess.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public void restartIPSEC() {
        try {
            String cmd = "/etc/rc.d/ipsec restart";
            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }

    public void restartOpenVPN(String wanIP) {
        try {
            String cmd = "sh /etc/firewall/Socket/resOpenVPNclient " + wanIP;
            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }

    public void setInet(String ip, int forTable) {
        try {
            switch (forTable) {
                case 1: {
                    String cmd = "ipfw table 12 add " + ip;
                    Process myProcess = Runtime.getRuntime().exec(cmd);
                    myProcess.waitFor();
                    break;
                }
                case 2: {
                    String cmd = "ipfw table 13 add " + ip;
                    Process myProcess = Runtime.getRuntime().exec(cmd);
                    myProcess.waitFor();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {

        }
    }

    public void delInet(String ip, int forTable) {
        try {
            switch (forTable) {
                case 1: {
                    String cmd = "ipfw table 12 delete " + ip;
                    Process myProcess = Runtime.getRuntime().exec(cmd);
                    myProcess.waitFor();
                    break;
                }
                case 2: {
                    String cmd = "ipfw table 13 delete " + ip;
                    Process myProcess = Runtime.getRuntime().exec(cmd);
                    myProcess.waitFor();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {

        }
    }

}
