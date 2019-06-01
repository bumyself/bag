package ssm.bean;

import jpcap.packet.TCPPacket;

import java.util.ArrayList;
import java.util.List;

public class TCPConversation {
    private List<TCPPacket> tcpPacketList;

    public TCPConversation( ) {
        this.tcpPacketList = new ArrayList<>(16);
    }

    public List<TCPPacket> getTcpPacketList() {
        return tcpPacketList;
    }

    private void setTcpPacketList(List<TCPPacket> tcpPacketList) {
        this.tcpPacketList = tcpPacketList;
    }
}
