package ssm.service;


import jpcap.packet.TCPPacket;
import ssm.bean.KDDUP99;
import ssm.bean.TCPConversation;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一次带有完整握手和挥手链接的数据包集合整理成数据集的一条记录
 */
public class Link2DataSet {






    public  static KDDUP99 Conversation2KDDUP99(List<TCPConversation>  conversations){



        //conversation就是要处理的会话
        TCPConversation conversation = conversations.get(conversations.size()-1);


        List<TCPPacket> tcpPackets  =  conversation.getTcpPacketList();
        int size = tcpPackets.size();

        if(size == 0){
            return null;
        }

        KDDUP99 kddup99 = new KDDUP99();
        kddup99.setProtocol_type(tcpPackets.get(0).protocol + "");
        kddup99.setDuration(tcpPackets.get(size-1).sec - tcpPackets.get(0).sec);

//        Service 和Flag 没有做
        kddup99.setService("1");
        kddup99.setFlag("SF");


        String srcIp1 = tcpPackets.get(0).src_ip.toString();
        String srcIp2 = tcpPackets.get(0).dst_ip.toString();
        for(int i=size-1; i>0; i--){
            if(tcpPackets.get(i).src_ip.toString().equals(srcIp1)){
                kddup99.setSrc_bytes(tcpPackets.get(i).ack_num);
                continue;
            }
            if(tcpPackets.get(i).src_ip.toString().equals(srcIp2)){
                kddup99.setDst_bytes(tcpPackets.get(i).ack_num);
                continue;
            }

        }

        boolean land = true;
        for(int i=0; i<size-1; i++){
            if(! tcpPackets.get(i).src_ip.toString().equals(tcpPackets.get(i+1).src_ip.toString())){
                land = false;
                break;
            }

            if( tcpPackets.get(i).src_port != tcpPackets.get(i+1).src_port){
                land = false;
                break;
            }

            if(! tcpPackets.get(i).dst_ip.toString().equals(tcpPackets.get(i+1).dst_ip.toString())){
                land = false;
                break;
            }


            if( tcpPackets.get(i).dst_port != tcpPackets.get(i+1).dst_port){
                land = false;
                break;
            }


        }


        if(land){
            kddup99.setLand(1);
        }else {
            kddup99.setLand(0);
        }

        kddup99.setWrong_fragment(0);

        int urgen = 0;
        for(int i=0; i<size; i++){
            urgen += tcpPackets.get(i).urgent_pointer;
        }

        kddup99.setUrgent(urgen);




        kddup99.setCount((int)timeBasic(conversations,"count" ));
        kddup99.setSrv_count( (int)timeBasic(conversations,"srv_count" ));
        kddup99.setSerror_rate(timeBasic(conversations,"serror_rate" ));
        kddup99.setSrv_serror_rate( timeBasic(conversations,"srv_serror_rate" ));
        kddup99.setRerror_rate( timeBasic(conversations,"rerror_rate" ));
        kddup99.setSrv_rerror_rate(timeBasic(conversations,"srv_rerror_rate" ));
        kddup99.setSame_srv_rate(timeBasic(conversations,"same_srv_rate" ));
        kddup99.setDiff_srv_rate(timeBasic(conversations,"diff_srv_rate" ));
        kddup99.setSrv_diff_host_rate(timeBasic(conversations,"srv_diff_host_rate" ));




        if(conversations.size() < 100){
            kddup99.setDst_host_count(0);
            kddup99.setDst_host_srv_count(0);
            kddup99.setDst_host_same_srv_rate(0);
            kddup99.setDst_host_diff_srv_rate(0);
            kddup99.setDst_host_same_src_port_rate(0);
            kddup99.setDst_host_srv_diff_host_rate(0);
            kddup99.setDst_host_serror_rate(0);
            kddup99.setDst_host_rerror_rate(0);
            kddup99.setDst_host_srv_rerror_rate(0);
        }else {
            int index = conversations.size()-101;
            List<TCPConversation> last100Conversion  = new ArrayList<>(16);
            for(; index<size; index++){
                last100Conversion.add(conversations.get(index));
            }

            kddup99.setDst_host_count((int)hostBased(last100Conversion,"dst_host_count"));
            kddup99.setDst_host_srv_count( (int)hostBased(last100Conversion,"dst_host_same_srv_rate"));
            kddup99.setDst_host_same_srv_rate( hostBased(last100Conversion,"dst_host_diff_srv_rate"));
            kddup99.setDst_host_diff_srv_rate( hostBased(last100Conversion,"dst_host_same_src_port_rate"));
            kddup99.setDst_host_same_src_port_rate( hostBased(last100Conversion,"dst_host_srv_diff_host_rate"));
            kddup99.setDst_host_srv_diff_host_rate( hostBased(last100Conversion,"dst_host_serror_rate"));
            kddup99.setDst_host_serror_rate( hostBased(last100Conversion,"dst_host_srv_serror_rate"));
            kddup99.setDst_host_rerror_rate( hostBased(last100Conversion,"dst_host_rerror_rate"));
            kddup99.setDst_host_srv_rerror_rate( hostBased(last100Conversion,"dst_host_srv_rerror_rate"));
        }







        return kddup99;

    }


    /**
     * 基于时间的网络流量统计特征 （共9种，23～31）
     */
    private static double  timeBasic(List<TCPConversation> list, String kind){
        int count  = 0;
        int synCount = 0;
        int rateCount = 0;

        TCPConversation currentTCPConversion = list.get(list.size() - 1);
        List<TCPConversation> eligible = new ArrayList<>(16);



       for(TCPConversation tcpConversation : list){

           if(tcpConversation.getTcpPacketList().size() == 0){
               return 0;
           }

           long startTimeDifference  =  currentTCPConversion.getTcpPacketList().get(0).sec -  tcpConversation.getTcpPacketList().get(0).sec;
            long endTimeDifference = currentTCPConversion.getTcpPacketList().get(currentTCPConversion.getTcpPacketList().size() - 1).sec -  tcpConversation.getTcpPacketList().get(tcpConversation.getTcpPacketList().size() - 1).sec;
            if(startTimeDifference <=2 && endTimeDifference <=2){
                eligible.add(tcpConversation);
            }
        }


        switch (kind){
//            过去两秒内，与当前连接具有相同的目标主机的连接数，连续，[0, 511]。
            case "count":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip)){
                        count++;
                    }
                }
                return count;
//            过去两秒内，与当前连接具有相同服务的连接数，连续，[0, 511]。
            case "srv_count":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_port == currentTCPConversion.getTcpPacketList().get(0).dst_port){
                        count++;
                    }
                }
                return count;
//           过去两秒内，在与当前连接具有相同目标主机的连接中，出现“SYN” 错误的连接的百分比，连续，[0.00, 1.00]。
            case "serror_rate":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip)){
                        for(TCPPacket tcpPacket : t.getTcpPacketList()){
                            if(tcpPacket.syn){
                                synCount++;
                            }
                            count++;
                        }
                    }
                }
                return  synCount / count;



//           过去两秒内，在与当前连接具有相同服务的连接中，出现“SYN” 错误的连接的百分比，连续，[0.00, 1.00]
            case "srv_serror_rate":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_port == currentTCPConversion.getTcpPacketList().get(0).dst_port){
                        for(TCPPacket tcpPacket : t.getTcpPacketList()){
                            if(tcpPacket.syn){
                                synCount++;
                            }
                            count++;
                        }
                    }
                }
                return  synCount / count;



//           过去两秒内，在与当前连接具有相同目标主机的连接中，出现“REJ” 错误的连接的百分比，连续，[0.00, 1.00]。
            case "rerror_rate":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip)){
                        for(TCPPacket tcpPacket : t.getTcpPacketList()){
                            if(tcpPacket.r_flag){
                                synCount++;
                            }
                            count++;
                        }
                    }
                }
                return  synCount / count;
//           过去两秒内，在与当前连接具有相同服务的连接中，出现“REJ” 错误的连接的百分比，连续，[0.00, 1.00]。
            case "srv_rerror_rate":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_port == currentTCPConversion.getTcpPacketList().get(0).dst_port){
                        for(TCPPacket tcpPacket : t.getTcpPacketList()){
                            if(tcpPacket.r_flag){
                                synCount++;
                            }
                            count++;
                        }
                    }
                }
                return  synCount / count;
//          过去两秒内，在与当前连接具有相同目标主机的连接中，与当前连接具有相同服务的连接的百分比，连续，[0.00, 1.00]。
            case "same_srv_rate":

                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip)
                    && t.getTcpPacketList().get(0).dst_port == currentTCPConversion.getTcpPacketList().get(0).dst_port)
                    {
                        rateCount++;
                    }
                    count++;
                }
                return rateCount / count;



//          过去两秒内，在与当前连接具有相同目标主机的连接中，与当前连接具有不同服务的连接的百分比，连续，[0.00, 1.00]。
            case "diff_srv_rate":
                for(TCPConversation t : eligible){
                    if(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip)
                            && t.getTcpPacketList().get(0).dst_port != currentTCPConversion.getTcpPacketList().get(0).dst_port)
                    {
                        rateCount++;
                    }
                    count++;
                }
                return rateCount / count;
//           过去两秒内，在与当前连接具有相同服务的连接中，与当前连接具有不同目标主机的连接的百分比，连续，[0.00, 1.00]。
            case "srv_diff_host_rate":
                for(TCPConversation t : eligible){
                    if(!(t.getTcpPacketList().get(0).dst_ip.equals(currentTCPConversion.getTcpPacketList().get(0).dst_ip))
                            && t.getTcpPacketList().get(0).dst_port == currentTCPConversion.getTcpPacketList().get(0).dst_port)
                    {
                        rateCount++;
                    }
                    count++;
                }
                return rateCount / count;
            default:
                return 0;
        }



    }




    /**
     * 基于主机的网络流量统计特征 （共10种，32～41）
     */
    public static double hostBased(List<TCPConversation> list, String kind){
        TCPConversation currentConversion  = list.get(list.size()  -1 );
        int sameCount = 0;
        boolean synTrueBreak = false;
        List<TCPConversation> conversationList = new ArrayList<>(16);
        int synError = 0;
        switch (kind){
            case "dst_host_count":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip  )){
                        sameCount++;
                    }
                }
                return  sameCount;
            case "dst_host_srv_count":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip)
                            &&(list.get(i).getTcpPacketList().get(0).dst_port  ==   currentConversion.getTcpPacketList().get(0).dst_port ) ){
                        sameCount++;
                    }
                }
                return  sameCount;

            case "dst_host_same_srv_rate":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip)
                            &&(list.get(i).getTcpPacketList().get(0).dst_port  ==   currentConversion.getTcpPacketList().get(0).dst_port ) ){
                        sameCount++;
                    }
                }
                return  sameCount / list.size() ;

            case "dst_host_diff_srv_rate":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_port  !=   currentConversion.getTcpPacketList().get(0).dst_port
                        && list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip))
                    {
                        sameCount++;
                    }
                }
                 return  sameCount / list.size();


            case "dst_host_same_src_port_rate":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).src_port  ==   currentConversion.getTcpPacketList().get(0).src_port
                            && list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip))
                    {
                        sameCount++;
                    }
                }
                return  sameCount / list.size();


            case "dst_host_srv_diff_host_rate":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_port  ==   currentConversion.getTcpPacketList().get(0).dst_port
                            &&  ! (list.get(i).getTcpPacketList().get(0).src_ip.equals( currentConversion.getTcpPacketList().get(0).src_ip))
                            &&  (list.get(i).getTcpPacketList().get(0).dst_ip.equals( currentConversion.getTcpPacketList().get(0).dst_ip))
                    )
                    {
                        sameCount++;
                    }
                }
                return  sameCount / list.size();


            case "dst_host_serror_rate":


                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).src_ip.equals(currentConversion.getTcpPacketList().get(0).src_ip)){
                        conversationList.add(list.get(i));
                    }
                }



                for(TCPConversation t :conversationList){
                    for(TCPPacket tcpPacket : t.getTcpPacketList()){
                        if(tcpPacket.syn){
                            synError++;
                            synTrueBreak = true;
                            break;
                        }
                    }
                    if(synTrueBreak){
                        synTrueBreak = false;
                        break;
                    }
                }

                return synError / conversationList.size();

            case "dst_host_srv_serror_rate":
                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals(currentConversion.getTcpPacketList().get(0).dst_ip)){
                        conversationList.add(list.get(i));
                    }
                }


                for(TCPConversation t :conversationList){
                    for(TCPPacket tcpPacket : t.getTcpPacketList()){
                        if(tcpPacket.syn){
                            synError++;
                            synTrueBreak = true;
                            break;
                        }
                    }
                    if(synTrueBreak){
                        synTrueBreak = false;
                        break;
                    }
                }

                return synError / conversationList.size();



            case "dst_host_rerror_rate":

                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals(currentConversion.getTcpPacketList().get(0).dst_ip)){
                        conversationList.add(list.get(i));
                    }
                }



                for(TCPConversation t :conversationList){
                    for(TCPPacket tcpPacket : t.getTcpPacketList()){
                        if(tcpPacket.r_flag){
                            synError++;
                            synTrueBreak = true;
                            break;
                        }
                    }
                    if(synTrueBreak){
                        synTrueBreak = false;
                        break;
                    }
                }

                return synError / conversationList.size();




            case "dst_host_srv_rerror_rate":

                for(int i = 0; i < list.size() -1 ; i++){
                    if(list.get(i).getTcpPacketList().get(0).dst_ip.equals(currentConversion.getTcpPacketList().get(0).dst_ip)
                        && list.get(i).getTcpPacketList().get(0).dst_port  ==   currentConversion.getTcpPacketList().get(0).dst_port){
                        conversationList.add(list.get(i));
                    }
                }



                for(TCPConversation t :conversationList){
                    for(TCPPacket tcpPacket : t.getTcpPacketList()){
                        if(tcpPacket.r_flag){
                            synError++;
                            synTrueBreak = true;
                            break;
                        }
                    }
                    if(synTrueBreak){
                        synTrueBreak = false;
                        break;
                    }
                }

                return synError / conversationList.size();



            default:
                return 0;
        }
    }
}
