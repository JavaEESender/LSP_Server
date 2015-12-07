/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import lsp.dao.FilialsJPA;
import lsp.dao.LogJPA;
import lsp.dao.TempJPA;
import lsp.dao.UsersJPA;
import lsp.entity.Filials;
import lsp.entity.Temp;

/**
 *
 * @author Alexandr
 */
public class Client implements Runnable {

    private final Socket clientSocket;

    public Client(Socket s) {
        this.clientSocket = s;
    }

    @Override
    public void run() {

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream()); DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            FilialsJPA filialsJPA = new FilialsJPA();
            LogJPA logJPA = new LogJPA();
            String sw = in.readUTF();
            switch (sw) {
                case "PING": {
                    for (Filials f : filialsJPA.getAddressNoPing()) {
                        out.writeUTF(f.getAddress());
                        out.flush();
                    }
                    out.writeUTF("END");
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "ping");
                    break;
                }
                case "AZS": {
                    Filials filial = filialsJPA.getFilialInfo(in.readUTF());
                    out.writeUTF(filial.getIsp().getName());
                    out.writeUTF(filial.getIsp().getPhone());
                    out.writeUTF(filial.getSubnet());
                    out.writeUTF(filial.getAdmin().getName());
                    out.writeUTF(filial.getPhone());
                    out.writeUTF(filialsJPA.getLastPing(filial.getId()));
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "AZSinfo " + filial.getAddress());
                    break;
                }
                case "VPN": {
                    Filials filial = filialsJPA.getFilialInfo(in.readUTF());
                    if (filial.getAuth().equals("IPSEC")) {
                        if (filial.getCheckStat() == 1) {
                            Console cmd = new Console();
                            cmd.restartIPSEC();
                            out.writeUTF("service IPSEC restart");
                            logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "service IPSEC restart");
                        } else {
                            out.writeUTF("IPSEC service is not restarted");
                            logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "IPSEC service is not restarted");
                        }
                    } else {
                        if (filial.getAuth().equals("OpenVPN")) {
                            if (filial.getCheckStat() == 1) {
                                Console cmd = new Console();
                                cmd.restartOpenVPN(filial.getWanIP());
                                out.writeUTF("reboot " + filial.getHostName());
                                logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "reboot " + filial.getHostName());
                            } else {
                                out.writeUTF("not reboot " + filial.getHostName());
                                logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "not reboot " + filial.getHostName());
                            }
                        } else {
                            logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "no VPN");
                        }
                    }
                    break;
                }
                case "setInet": {
                    String ip = in.readUTF();
                    String dur = in.readUTF();
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.add(GregorianCalendar.HOUR, Integer.parseInt(dur));
                    if (new TempJPA().setTemp(ip, gc.getTime(), 1)) {
                        Console cmd = new Console();                                //run console add to table
                        cmd.setInet(ip, 1);
                        out.writeUTF("add inet for " + ip);
                        logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "add inet for " + ip);
                    } else {
                        out.writeUTF("update inet for " + ip);
                        logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "update inet for " + ip);
                    }
                    break;
                }
                case "getInet": {
                    for (Temp t : new TempJPA().getTemp(1)) {
                        out.writeUTF(t.getIp());
                        out.flush();
                    }
                    out.writeUTF("END");
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "get inet");
                    break;
                }
                case "remInet": {
                    String ip = in.readUTF();
                    new TempJPA().delTemp(ip);
                    Console cmd = new Console();                                //run console add to table
                    cmd.delInet(ip, 1);
                    out.writeUTF("delete inet for " + ip);
                    out.flush();
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "rem inet " + ip);
                    break;
                }
                case "setReject": {
                    String ip = in.readUTF();
                    String dur = in.readUTF();
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.add(GregorianCalendar.HOUR, Integer.parseInt(dur));
                    if (new TempJPA().setTemp(ip, gc.getTime(), 2)) {
                        Console cmd = new Console();                                //run console add to table
                        cmd.setInet(ip, 2);
                        out.writeUTF("add rej for " + ip);
                        logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "add rej for " + ip);
                    } else {
                        out.writeUTF("update rej for " + ip);
                        logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "update rej for " + ip);
                    }
                    break;
                }
                case "getReject": {
                    for (Temp t : new TempJPA().getTemp(2)) {
                        out.writeUTF(t.getIp());
                        out.flush();
                    }
                    out.writeUTF("END");
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "get rej");
                    break;
                }
                case "remReject": {
                    String ip = in.readUTF();
                    new TempJPA().delTemp(ip);
                    Console cmd = new Console();                                //run console add to table
                    cmd.delInet(ip, 2);
                    out.writeUTF("delete rej for " + ip);
                    out.flush();
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "rem rej " + ip);
                    break;
                }
                case "Sync": {
                    for (Filials f : filialsJPA.getAllFilials()) {
                        out.writeUTF("BEGIN");
                        out.writeUTF(f.getAddress());
                        out.writeUTF(f.getIsp().getName());
                        out.writeUTF(f.getIsp().getPhone());
                        out.writeUTF(f.getSubnet());
                        out.writeUTF(f.getAdmin().getName());
                        out.writeUTF(f.getPhone());
                    }
                    out.writeUTF("END");
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "Sync");
                    break;
                }
                case "getLogin": {
                    String login = in.readUTF();
                    String pass = in.readUTF();
                    String salt = new UsersJPA().getLogin(login, pass);
                    out.writeUTF(salt);
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "Login");
                    break;
                }
                case "getExpireTime": {
                    String ip = in.readUTF();
                    out.writeUTF(new TempJPA().getExpireTime(ip));
                    break;
                }
                case "checkUpdate": {
                    File file = new File("/etc/firewall/Socket/update/version.txt");
                    FileInputStream fis = new FileInputStream(file);
                    Scanner scanner = new Scanner(fis);
                    out.writeUTF(scanner.nextLine());
                    break;
                }
                case "updateApk": {

                    try {
                        byte[] buffer = new byte[1024];
                        File file = new File("/etc/firewall/Socket/update/lp.apk");
                        FileInputStream fis = new FileInputStream(file);
                        int count;
                        while ((count = fis.read(buffer, 0, buffer.length)) > 0) {
                            out.write(buffer, 0, count);
                        }
                        fis.close();
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found!");
                    } catch (IOException e) {
                        System.err.println("IOException");
                    } catch (Exception e) {
                    }

                    break;
                }
                default: {
                    logJPA.addLog(clientSocket.getInetAddress().getHostAddress(), "connecting ip");
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
