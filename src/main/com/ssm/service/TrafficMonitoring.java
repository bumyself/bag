package ssm.service;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;

import jpcap.packet.UDPPacket;
import jpcap.packet.TCPPacket;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.bean.TCPConversation;
import ssm.bean.TCPLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TrafficMonitoring {


    static List<TCPPacket>tcpPackets = new ArrayList<>(16);
    static List<UDPPacket>udpPackets = new ArrayList<>(16);
    static List<ICMPPacket>icmpPackets = new ArrayList<>(16);
    static  List<TCPConversation>  TCPConversations = new ArrayList<>(160);
    static List<TCPLink> TCPLinks = new LinkedList<>();






    /**
     * 获取网络接口列表
     */
    public String getDevices() {

        String resultStr = "";


        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (int i = 0; i < devices.length; i++) {
             resultStr += ("网卡编号：" + i + ": <br>详细信息：" + devices[i].name + "("
                    + devices[i].description + ")<br>");
            resultStr +=(" datalink: " + devices[i].datalink_name + "("
                    + devices[i].datalink_description + ")<br>");
            resultStr +=(" MAC地址:");
            for (byte b : devices[i].mac_address){
                resultStr +=(Integer.toHexString(b & 0xff) + ":");
            }

            System.out.println();
            for (NetworkInterfaceAddress a : devices[i].addresses){
                resultStr +=("ip address:" + a.address + " " + a.subnet
                        + " " + a.broadcast + "<br>");
            }

        }
        return resultStr;
    }



    /**
     *使用逐个捕获方法, 从网络接口捕获数据包
     */

    public   List<TCPConversation> oneByOneReceiver(int index, int time) throws IOException {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535,
                false, 10);
        Packet packet;
        long startTime = System.currentTimeMillis();
        while (startTime + time * 60 * 10 >= System.currentTimeMillis()) {
            packet = captor.getPacket();

            try{
                tcpPackets.add((TCPPacket)packet);
            }catch (Exception e){
                try{
                    udpPackets.add(  (UDPPacket)packet);
                }catch (ClassCastException e1){
                    try{
                        icmpPackets.add((ICMPPacket) packet);
                    }catch (ClassCastException e2){
                        //who cares?
                    }
                }
            }
    }

         packets2Link(tcpPackets);


        for(TCPLink tcpLink : TCPLinks){
            List<TCPConversation>list = soutOut(tcpLink);
            for(TCPConversation conversation : list){
                TCPConversations.add(conversation);
            }


        }


        List<TCPConversation> temporaryList = new ArrayList<>(16);

        for(int i=0; i<TCPConversations.size(); i++){
            if(TCPConversations.get(i).getTcpPacketList().size() != 0){
                temporaryList.add(TCPConversations.get(i));
            }
        }




        TCPConversations = temporaryList;
        TCPConversations  = packetSrting(TCPConversations);

        return TCPConversations;

    }


    /**
     *使用逐个捕获方法, 从网络接口捕获数据包
     *  包A（192.168.2.2：80 到  192.168.2.4：1024）  和 包B（192.168.2.2：80 到 192.168.2.4：1024  ）  返回true
     *  或者
     *  包A（192.168.2.2：80 到  192.168.2.4：1024）  和 包B（ 192.168.2.4：1024 到 192.168.2.2：80）  返回true
     */
    private static boolean comparePacket(TCPPacket p1, TCPPacket p2){
        if(p1.src_ip.equals(p2.src_ip)  && p1.src_port == p2.src_port
                && p1.dst_ip.equals(p2.dst_ip) && p1.dst_port == p2.dst_port  ){
            return true;
        }else if(p1.src_ip.equals(p2.dst_ip)   &&  p1.src_port == p2.dst_port
                && p1.dst_ip.equals(p2.src_ip) && p1.dst_port == p2.src_port){
            return true;
        }
        return false;
    }





    /**
     *将包整理成链接
     */
      static  List<TCPLink>  packets2Link(List<TCPPacket> packetBeans){
        for(TCPPacket currentPacketBean : packetBeans){
            if(currentPacketBean == null){
                continue;
            }


//            遍历所有的包

            boolean isAdded = false;
//            这个变量标识当前遍历的包是否已经加入某个链接，默认为false， 如果加入了设置为true
            for(TCPLink TCPLink : TCPLinks){
//                遍历所有的link，将当前遍历的包和所有链接里的包进行比较，看能不能加入
                TCPPacket packetInLink = TCPLink.getFirstBean();
                if(comparePacket(currentPacketBean, packetInLink)){
                    TCPLink.addPacket(currentPacketBean);
                    isAdded = true;
                    break;
                }

            }
            if(!isAdded){
                TCPLink TCPLink = new TCPLink();
                TCPLink.addPacket(currentPacketBean);
                TCPLinks.add(TCPLink);
            }

        }
        return TCPLinks;
    }



    private static List<TCPConversation> soutOut(TCPLink TCPLink){
          List<TCPConversation> conversationList = new ArrayList<>(16);
          TCPConversation tcpConversation = new TCPConversation();
          int length = TCPLink.getTcpPacketList().size();
          boolean isAdded = false;
          int startx = 0;
          for(; startx<length; startx++){
              if(TCPLink.getTcpPacketList().get(startx).syn){
                  break;
              }
          }

          int finAckTimes= 0;
          for(; startx<length; startx++){

              if(TCPLink.getTcpPacketList().get(startx).ack  &&
                      TCPLink.getTcpPacketList().get(startx-1).fin &&
                      TCPLink.getTcpPacketList().get(startx-1).ack){
                  finAckTimes += 1;
                  if(finAckTimes == 2){
                      tcpConversation.getTcpPacketList().add(TCPLink.getTcpPacketList().get(startx));
                      conversationList.add(tcpConversation);
                      tcpConversation = new TCPConversation();
                      isAdded = true;
                      finAckTimes = 0;
                      continue;
                  }

              }

              tcpConversation.getTcpPacketList().add(TCPLink.getTcpPacketList().get(startx));
          }
          if(!isAdded){
              conversationList.add(tcpConversation);
          }




          return conversationList;
    }

    /**
     * 根据会话中的第一个数据包时间将会话们进行排序
     */
    private  static List<TCPConversation>packetSrting(List<TCPConversation>   tcpConversation){

          if(tcpConversation.size() == 0){
              return null;
          }

          for(int i=0; i<tcpConversation.size (); i++){
              for(int j=i+1; j<tcpConversation.size(); j++){
                  if(tcpConversation.get(i).getTcpPacketList().size() == 0 || tcpConversation.get(j).getTcpPacketList().size() == 0){
                      continue;
                  }
                  TCPPacket TCPI = tcpConversation.get(i).getTcpPacketList().get( 0 );
                  TCPPacket TCPJ = tcpConversation.get(j).getTcpPacketList().get( 0 );
                if(TCPI.sec > TCPJ.sec){
                    TCPConversation temporary = tcpConversation.get(i);
                    tcpConversation.set(i,tcpConversation.get(j));
                    tcpConversation.set(j,temporary);
                }
              }
          }
          return tcpConversation;
    }

}