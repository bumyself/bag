package ssm.bean;

public class IpAndData {
    private String srcIp;
    private String dtcIp;
    private long Data;

    public String ips(){
        return  srcIp.replace("/", "") + "," + dtcIp.replace("/", "");
    }


    public IpAndData(String srcIp, String dtcIp, long data) {
        this.srcIp = srcIp;
        this.dtcIp = dtcIp;
        Data = data;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDtcIp() {
        return dtcIp;
    }

    public void setDtcIp(String dtcIp) {
        this.dtcIp = dtcIp;
    }

    public long getData() {
        return Data;
    }

    public void setData(long data) {
        Data = data;
    }
}
