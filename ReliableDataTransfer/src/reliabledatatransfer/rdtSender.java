package reliabledatatransfer;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/*
 * @author haktan
 */
public class rdtSender extends rdtReceiver {

    public rdtReceiver rcv = new rdtReceiver();

    public void rdt_send(int[] data, long check) { //for 2.0
        String packet = make_pkt(data);
        System.out.println("Data has been packed");
        System.out.println(packet);
        System.out.println("Data has been sent by udt_send");
        udt_send(packet, check);
    }

    public int rdt_send(int[] data, long check, int seq1) {//for rdt 2.1
        String packet = make_pkt(data);
        System.out.println("Data has been packed");
        System.out.println(packet);
        System.out.println("Data has been sent by udt_send");
        int tmp = udt_send(packet, check, seq1);
        if (tmp == 1) {
            return 1;
        } else if (tmp == 0) {
            return 0;
        } else if (tmp == 2) {
            return 2;
        }
        return 3;
    }

    public int rdt_sendWithoutACK(int[] data, long check, int seq1) {//for rdt 2.2
        String packet = make_pkt(data);
        System.out.println("Data has been packed");
        System.out.println(packet);
        System.out.println("Data has been sent by udt_send");
        int tmp = udt_sendWithoutACK(packet, check, seq1);
        if (tmp == 1) {
            return 1;
        } else if (tmp == 0) {
            return 0;
        } else if (tmp == 2) {
            return 2;
        }
        return 3;
    }

    public void rdt_send(int[] data) { //for rdt 1.0
        String packet = make_pkt(data);
        System.out.println("Data has been packed");
        System.out.println(packet);
        System.out.println("Data has been sent by udt_send");
        udt_send(packet);
    }

    public String make_pkt(int[] data) { //for rdt 1.0
        String packet = "";
        for (int i = 0; i < data.length; i++) { //from bits to bytes packeting process
            packet += data[i];
        }
        System.out.println(packet);
        return packet;
    }

    public void udt_send(String data) { //for rdt 1.0
        rcv.rdt_rcv(data);
    }

    public void udt_send(String data, long chck) { //for rdt 2.0
        rcv.rdt_rcv(data, chck);
    }

    public int udt_send(String data, long chck, int seq1) {//for rdt2.1
        int tmp = rcv.rdt_rcv(data, chck, seq1);
        if (tmp == 1) {
            return 1;
        } else if (tmp == 0) {
            return 0;
        } else if (tmp == 2) {
            return 2;
        }
        return 3;
    }

    public int udt_sendWithoutACK(String data, long chck, int seq1) {//for rdt2.1
        int tmp = rcv.rdt_rcvWithoutACK(data, chck, seq1);
        if (tmp == 1) {
            return 1;
        } else if (tmp == 0) {
            return 0;
        } else if (tmp == 2) {
            return 2;
        }
        return 3;
    }
}
