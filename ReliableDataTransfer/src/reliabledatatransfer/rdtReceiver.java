package reliabledatatransfer;

import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 *
 * @author haktan
 */
public class rdtReceiver {

    public int seq2 = 0;

    public void rdt_rcv(String packet, long check) { //for rdt 2.0
        System.out.println("Receiver has received the packet");
        //extract(packet, check);

        while (true) {
            if (extract(packet, check) == 1) {
                break;
            }
        }
    }

    public int rdt_rcv(String packet, long check, int seq1) { //for rdt 2.1
        System.out.println("Receiver has received the packet");
        while (true) {
            System.out.println("seq1=" + seq1 + "seq2=" + seq2);
            if ((seq1 == seq2) && (extract(packet, check) == 1)) {

                if (seq2 == 0) {
                    seq2 = 1;
                    return seq2;
                } else {
                    seq2 = 0;
                    return seq2;
                }
            } else if (seq1 != seq2) {
                return 2;
            }
        }
    }

    public int rdt_rcvWithoutACK(String packet, long check, int seq1) { //for rdt 2.1
        System.out.println("Receiver has received the packet");
        while (true) {
            System.out.println("seq1=" + seq1 + "seq2=" + seq2);
            if ((seq1 == seq2) && (extractWithoutACK(packet, check) == 1)) {
                if (seq2 == 0) {
                    seq2 = 1;
                    return seq2;
                } else {
                    seq2 = 0;
                    return seq2;
                }
            } else if (seq1 != seq2) {
                return 2;
            }
        }
    }

    public void rdt_rcv(String packet) { //for rdt 1.0
        System.out.println("Receiver has received the packet");
        extract(packet);
    }

    public int extract(String data, long check) { //for rdt 2.0 and 2.1
        String flag = "";
        int dt = Integer.parseInt(data);
        Checksum cm = new CRC32();
        cm.update(dt);
        long chck = cm.getValue();
        if (chck == check) {
            flag = "ACK";
        } else {
            flag = "NACK";
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(2);
        //we controlled if sent data's and received data's are Checksum are equal. 
        //but i added random corrupt possibility for simulation.
        if (rand_int1 == 1) {
            flag = "NACK";
            System.out.println("We got NACK, so data sending again...");
        }
        System.out.println(flag);
        if (flag == "ACK") {
            int[] packet = {0, 0, 0, 0};
            for (int i = 0; i < data.length(); i++) {
                packet[i] = Character.getNumericValue(data.charAt(i));
            }
            System.out.println("Data extracted");
            for (int i = 0; i < data.length(); i++) {
                System.out.print(packet[i] + ",");
            }
            System.out.println("\n");

            deliver_data(packet);
            return 1;
        } else {
            return 0;
        }

    }

    public int extractWithoutACK(String data, long check) { //for rdt 2.2
        int dt = Integer.parseInt(data);
        Checksum cm = new CRC32();
        cm.update(dt);
        long chck = cm.getValue();
        if (chck == check) {
            int[] packet = {0, 0, 0, 0};
            for (int i = 0; i < data.length(); i++) {
                packet[i] = Character.getNumericValue(data.charAt(i));
            }
            System.out.println("Data extracted");
            for (int i = 0; i < data.length(); i++) {
                System.out.print(packet[i] + ",");
            }
            System.out.println("\n");
            deliver_data(packet);
            return 1;
        } else {
            return 0;
        }
    }

    public void extract(String data) { //for rdt 1.0
        int[] packet = {0, 0, 0, 0};
        for (int i = 0; i < data.length(); i++) {
            packet[i] = Character.getNumericValue(data.charAt(i));
        }
        System.out.println("Data extracted");
        for (int i = 0; i < data.length(); i++) {
            System.out.print(packet[i] + ",");
        }
        System.out.println("\n");
        deliver_data(packet);
    }

    public void deliver_data(int[] data) {
        System.out.println("Data is delivered");
    }
}
