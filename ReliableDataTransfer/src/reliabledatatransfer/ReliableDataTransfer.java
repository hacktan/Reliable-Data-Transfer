/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reliabledatatransfer;

import java.util.Scanner;
import java.util.Timer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 *
 * @author hakta
 */
public class ReliableDataTransfer extends rdtSender {

    /**
     * @param args the command line arguments
     */
    public void chck(long sum) {
        System.out.println("fa");

    }

    public static void main(String[] args) throws InterruptedException {
        Timer tmer = new Timer();
        int[] data = {1, 0, 1, 0};
        int[][] dataPacket = {{1, 0, 1, 0}, {1, 0, 1, 0}};
        System.out.println("Choose a rdt version, options are: rdt 1.0=1, rdt 2.0=2, 2.1=2.1, 2.2=2.2 and for 3.0=3");
        Scanner sn = new Scanner(System.in);
        int seq1 = 0;
        double vrs = Double.parseDouble(sn.nextLine());
        System.out.println("The data that will be delivered has been created.");
        if (vrs == 1.0) {
            rdtSender snd = new rdtSender();
            snd.rdt_send(data);
        } else if (vrs == 2.0) {
            String packet = "";
            for (int i = 0; i < data.length; i++) { //from bits to bytes packeting process
                packet += data[i];
            }
            int cod = Integer.parseInt(packet);
            Checksum cm = new CRC32();
            cm.update(cod);

            long chck = cm.getValue();

            rdtSender snd = new rdtSender();
            snd.rdt_send(data, chck);
        } else if (vrs == 2.1) {
            String packet = "";
            for (int i = 0; i < data.length; i++) { //from bits to bytes packeting process
                packet += data[i];
            }
            int cod = Integer.parseInt(packet);
            Checksum cm = new CRC32();
            cm.update(cod);

            long chck = cm.getValue();

            rdtSender snd = new rdtSender();

            for (int i = 0; i < dataPacket.length; i++) {
                int tmp = snd.rdt_send(dataPacket[i], chck, seq1);
                if (tmp == 1 || tmp == 0) {
                    if (seq1 == 0) {
                        seq1 = 1;
                    } else {
                        seq1 = 0;
                    }
                } else {
                    i--;
                }
            }

        }
        else if (vrs == 2.2) {
            String packet = "";
            for (int i = 0; i < data.length; i++) { //from bits to bytes packeting process
                packet += data[i];
            }
            int cod = Integer.parseInt(packet);
            Checksum cm = new CRC32();
            cm.update(cod);

            long chck = cm.getValue();

            rdtSender snd = new rdtSender();

            for (int i = 0; i < dataPacket.length; i++) {
                int tmp = snd.rdt_sendWithoutACK(dataPacket[i], chck, seq1);
                if (tmp == 1 || tmp == 0) {
                    if (seq1 == 0) {
                        seq1 = 1;
                    } else {
                        seq1 = 0;
                    }
                } else {
                    i--;
                }
            }

        }
        else if (vrs == 3.0) {
            String packet = "";
            for (int i = 0; i < data.length; i++) { //from bits to bytes packeting process
                packet += data[i];
            }
            int cod = Integer.parseInt(packet);
            Checksum cm = new CRC32();
            cm.update(cod);
            long chck = cm.getValue();
            rdtSender snd = new rdtSender();
            for (int i = 0; i < dataPacket.length; i++) {
                int tmp = snd.rdt_sendWithoutACK(dataPacket[i], chck, seq1);
                long start = System.currentTimeMillis();
                Thread.sleep(2000); //Timer for rdt 3.0 feature
                if (tmp == 1 || tmp == 0) {
                    if (seq1 == 0) {
                        seq1 = 1;
                    } else {
                        seq1 = 0;
                    }
                } else {
                    i--;
                }
            }

        }

    }

}
