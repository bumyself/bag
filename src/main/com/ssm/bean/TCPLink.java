package ssm.bean;

import jpcap.packet.TCPPacket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TCPLink implements Serializable {
    private List<TCPPacket> tcpPacketList;


    public List<TCPPacket> getTcpPacketList() {
        return tcpPacketList;
    }

    public TCPLink(){
        tcpPacketList = new ArrayList<>(16);
    }

    public List<TCPPacket> addPacket(TCPPacket packetBean){
        tcpPacketList.add(packetBean);
        return tcpPacketList;
    }
    public TCPPacket getFirstBean(){
        return tcpPacketList.get(0);
    }
}
