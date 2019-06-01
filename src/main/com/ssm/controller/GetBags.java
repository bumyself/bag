package ssm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.bean.IpAndData;
import ssm.bean.Msg;
import ssm.bean.TCPConversation;
import ssm.service.AnalyseFlow;
import ssm.service.TrafficMonitoring;
import ssm.util.JsonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GetBags {

    @Autowired
    TrafficMonitoring trafficMonitoring;

    @Autowired
    AnalyseFlow analyseFlow;


    @RequestMapping("/getBags")
    @ResponseBody
    public String getBags() throws Exception{
        TrafficMonitoring trafficMonitoring = new TrafficMonitoring();

        List<TCPConversation> conversationList =  trafficMonitoring.oneByOneReceiver(0,100);
        List<IpAndData> ipAndDatas = new ArrayList<>(16);
        Map<String, Object> resultMap = new HashMap<>(16);

        if(conversationList.size() == 0){
            return null;
        }
        Msg msg  = Msg.success();
        for(TCPConversation t :conversationList){
            long data =  analyseFlow.getFlow(t);
            if(data > 0){
               ipAndDatas.add(new IpAndData(t.getTcpPacketList().get(0).src_ip.toString(), t.getTcpPacketList().get(0).dst_ip.toString(), data));
            }
        }


        for(int i=0;i<ipAndDatas.size();i++){
            for(int j=i; j<ipAndDatas.size(); j++){
                if(ipAndDatas.get(i).getSrcIp().equals(ipAndDatas.get(j).getSrcIp()) && ipAndDatas.get(i).getDtcIp().equals(ipAndDatas.get(j).getDtcIp())){
                    long totalData = ipAndDatas.get(i).getData() + ipAndDatas.get(j).getData();
                    ipAndDatas.get(i).setData(totalData);
                    ipAndDatas.remove(j);
                }
            }
        }

        for(int i=0;i<ipAndDatas.size();i++){
            resultMap.put( ipAndDatas.get(i).ips(), ipAndDatas.get(i).getData());
        }

        String resultJson = JsonUtils.toJson(resultMap);

        msg.setExtend(resultMap);
        resultJson = resultJson.replace("/", "");
        if(resultJson.length() == 0){
            throw  new Exception("长度为0！！！！！！！！！！！！！");
        }

        return  resultJson;
    }






    @RequestMapping(value = "/getD", produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getBags2( )  {
        return trafficMonitoring.getDevices();
    }





    @RequestMapping("tblJ")
    @ResponseBody
    public Msg get(@RequestParam("index") String index, @RequestParam("time") String time) throws Exception{
        List<TCPConversation> conversationList =  trafficMonitoring.oneByOneReceiver(Integer.parseInt(index),Integer.parseInt(time));
        List<IpAndData> ipAndDatas = new ArrayList<>(16);

        if(conversationList.size() == 0){
            return Msg.fail();
        }
        Msg msg  = Msg.success();
        for(TCPConversation t :conversationList){
            long data =  analyseFlow.getFlow(t);
            if(data > 0){
                ipAndDatas.add(new IpAndData(t.getTcpPacketList().get(0).src_ip.toString(), t.getTcpPacketList().get(0).dst_ip.toString(), data));
            }
        }


        msg.add("datas", ipAndDatas);
        return msg;

    }



}
