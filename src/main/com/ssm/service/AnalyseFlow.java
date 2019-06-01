package ssm.service;

import jpcap.packet.TCPPacket;
import org.springframework.stereotype.Service;
import ssm.bean.TCPConversation;

@Service
public class AnalyseFlow {

    public long getFlow(TCPConversation tcpConversation){
        long totalFlow = 0;
        int lastEle = tcpConversation.getTcpPacketList().size() - 1;
        TCPPacket lastTcpPacket  =  tcpConversation.getTcpPacketList().get(lastEle);
        totalFlow += lastTcpPacket.ack_num;
        for(; lastEle>0; lastEle--){
            if(!(tcpConversation.getTcpPacketList().get(lastEle).src_ip.equals(lastTcpPacket.src_ip))){
                totalFlow += tcpConversation.getTcpPacketList().get(lastEle).ack_num;
                break;
            }
        }
        return totalFlow;
    }
}
