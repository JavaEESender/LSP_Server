/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;
import lsp.dao.TempJPA;
import lsp.entity.Temp;

/**
 *
 * @author Alexandr
 */
public class CheckTable implements Runnable {

    @Override
    public void run() {
        Timer t = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TempJPA tempJPA = new TempJPA();
                try {
                    for (Temp t : tempJPA.getAllTemp()) {
                        Date current = new Date();
                        if (current.compareTo(t.getLiveTo()) == 1) {
                            Console console = new Console();
                            console.delInet(t.getIp(), t.getForTable());
                            tempJPA.delTemp(t.getIp());
                        }
                    }
                } catch (Exception ex) {

                }
            }
        });
        t.start();
    }

}
