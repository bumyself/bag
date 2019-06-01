package ssm.bean;

public class KDDUP99 {


    /**
     * 连接持续时间
     */
    private long duration;


    /**
     *  协议类型，离散类型，共有3种：TCP, UDP, ICMP。
     */
    private String protocol_type;

    /**
     * service
     */
    private String service;

    /**
     *  连接正常或错误的状态
     */
    private String flag;


    /**
     * 从源主机到目标主机的数据的字节数，连续类型，范围是 [0, 1379963888]。
     */
    private long src_bytes;

    /**
     *从目标主机到源主机的数据的字节数，连续类型，范围是 [0. 1309937401]。
     */
    private long  dst_bytes;

    /**
     *若连接来自/送达同一个主机/端口则为1，否则为0，离散类型，0或1。
     */
    private int land;

    /**
     * 错误分段的数量，连续类型，范围是 [0, 3]。
     */
    private int wrong_fragment;


    /**
     * 加急包的个数，连续类型，范围是[0, 14]。
     */
    private int urgent;


    /**
     * 前100个连接中，与当前连接具有相同目标主机的连接数，连续，[0, 255]。
     */
    private int dst_host_count;



    private int dst_host_srv_count;


    /**
     * 前100个连接中，与当前连接具有相同目标主机相同服务的连接所占的百分比，连续，[0.00, 1.00]。
     */
    private double dst_host_same_srv_rate;

    /**
     * 前100个连接中，与当前连接具有相同目标主机不同服务的连接所占的百分比，连续，[0.00, 1.00]。
     */

    private double dst_host_diff_srv_rate;


    /**
     * 前100个连接中，与当前连接具有相同目标主机相同源端口的连接所占的百分比，连续，[0.00, 1.00]。
     */
    private double dst_host_same_src_port_rate;


    /**
     * . 前100个连接中，与当前连接具有相同目标主机的连接中，出现SYN错误的连接所占的百分比，连续，[0.00, 1.00]。
     */
    private double dst_host_serror_rate;


    /**
     *  前100个连接中，与当前连接具有相同目标主机相同服务的连接中，出现SYN错误的连接所占的百分比，连续，[0.00, 1.00]。
     */

    private double dst_host_srv_serror_rate;


    /**
     * 前100个连接中，与当前连接具有相同目标主机的连接中，出现REJ错误的连接所占的百分比，连续，[0.00, 1.00]。
     */
    private double dst_host_rerror_rate;


    /**
     * dst_host_srv_rerror_rate
     */

    private double dst_host_srv_rerror_rate;


    /**
     * 前100个连接中，与当前连接具有相同目标主机相同服务的连接中，与当前连接具有不同源主机的连接所占的百分比，连续，[0.00, 1.00]。
     */

    private double dst_host_srv_diff_host_rate;


    /**
     * 过去两秒内，与当前连接具有相同的目标主机的连接数，连续，[0, 511]。
     */
    private int count;


    /**
     * 过去两秒内，与当前连接具有相同服务的连接数，连续，[0, 511]。
     */
    private int srv_count;

    /**
     * . 过去两秒内，在与当前连接具有相同目标主机的连接中，出现“SYN” 错误的连接的百分比，连续，[0.00, 1.00]。
     */
    private double serror_rate;


    /**
     * 过去两秒内，在与当前连接具有相同服务的连接中，出现“SYN” 错误的连接的百分比，连续，[0.00, 1.00]。
     */
    private double srv_serror_rate;


    /**
     * 过去两秒内，在与当前连接具有相同目标主机的连接中，出现“REJ” 错误的连接的百分比，连续，[0.00, 1.00]。
     */
    private double rerror_rate;

    /**
     * 过去两秒内，在与当前连接具有相同服务的连接中，出现“REJ” 错误的连接的百分比，连续，[0.00, 1.00]。
     */

    private double srv_rerror_rate;

    /**
     *  过去两秒内，在与当前连接具有相同目标主机的连接中，与当前连接具有相同服务的连接的百分比，连续，[0.00, 1.00]。
     */
    private double same_srv_rate;

    /**
     * 过去两秒内，在与当前连接具有相同目标主机的连接中，与当前连接具有不同服务的连接的百分比，连续，[0.00, 1.00]。
     */
    private double diff_srv_rate;


    /**
     *  过去两秒内，在与当前连接具有相同服务的连接中，与当前连接具有不同目标主机的连接的百分比，连续，[0.00, 1.00]。
     */
    private double srv_diff_host_rate;




    public KDDUP99() {
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSrv_count() {
        return srv_count;
    }

    public void setSrv_count(int srv_count) {
        this.srv_count = srv_count;
    }

    public double getSerror_rate() {
        return serror_rate;
    }

    public void setSerror_rate(double serror_rate) {
        this.serror_rate = serror_rate;
    }

    public double getSrv_serror_rate() {
        return srv_serror_rate;
    }

    public void setSrv_serror_rate(double srv_serror_rate) {
        this.srv_serror_rate = srv_serror_rate;
    }

    public double getRerror_rate() {
        return rerror_rate;
    }

    public void setRerror_rate(double rerror_rate) {
        this.rerror_rate = rerror_rate;
    }

    public double getSrv_rerror_rate() {
        return srv_rerror_rate;
    }

    public void setSrv_rerror_rate(double srv_rerror_rate) {
        this.srv_rerror_rate = srv_rerror_rate;
    }

    public double getSame_srv_rate() {
        return same_srv_rate;
    }

    public void setSame_srv_rate(double same_srv_rate) {
        this.same_srv_rate = same_srv_rate;
    }

    public double getDiff_srv_rate() {
        return diff_srv_rate;
    }

    public void setDiff_srv_rate(double diff_srv_rate) {
        this.diff_srv_rate = diff_srv_rate;
    }

    public double getSrv_diff_host_rate() {
        return srv_diff_host_rate;
    }

    public void setSrv_diff_host_rate(double srv_diff_host_rate) {
        this.srv_diff_host_rate = srv_diff_host_rate;
    }

    public double getDst_host_srv_diff_host_rate() {
        return dst_host_srv_diff_host_rate;
    }

    public void setDst_host_srv_diff_host_rate(double dst_host_srv_diff_host_rate) {
        this.dst_host_srv_diff_host_rate = dst_host_srv_diff_host_rate;
    }

    public int getDst_host_srv_count() {
        return dst_host_srv_count;
    }

    public void setDst_host_srv_count(int dst_host_srv_count) {
        this.dst_host_srv_count = dst_host_srv_count;
    }

    public int getDst_host_count() {
        return dst_host_count;
    }

    public void setDst_host_count(int dst_host_count) {
        this.dst_host_count = dst_host_count;
    }

    public double getDst_host_same_srv_rate() {
        return dst_host_same_srv_rate;
    }

    public void setDst_host_same_srv_rate(double dst_host_same_srv_rate) {
        this.dst_host_same_srv_rate = dst_host_same_srv_rate;
    }

    public double getDst_host_diff_srv_rate() {
        return dst_host_diff_srv_rate;
    }

    public void setDst_host_diff_srv_rate(double dst_host_diff_srv_rate) {
        this.dst_host_diff_srv_rate = dst_host_diff_srv_rate;
    }

    public double getDst_host_same_src_port_rate() {
        return dst_host_same_src_port_rate;
    }

    public void setDst_host_same_src_port_rate(double dst_host_same_src_port_rate) {
        this.dst_host_same_src_port_rate = dst_host_same_src_port_rate;
    }

    public double getDst_host_serror_rate() {
        return dst_host_serror_rate;
    }

    public void setDst_host_serror_rate(double dst_host_serror_rate) {
        this.dst_host_serror_rate = dst_host_serror_rate;
    }

    public double getDst_host_srv_serror_rate() {
        return dst_host_srv_serror_rate;
    }

    public void setDst_host_srv_serror_rate(double dst_host_srv_serror_rate) {
        this.dst_host_srv_serror_rate = dst_host_srv_serror_rate;
    }

    public double getDst_host_rerror_rate() {
        return dst_host_rerror_rate;
    }

    public void setDst_host_rerror_rate(double dst_host_rerror_rate) {
        this.dst_host_rerror_rate = dst_host_rerror_rate;
    }

    public double getDst_host_srv_rerror_rate() {
        return dst_host_srv_rerror_rate;
    }

    public void setDst_host_srv_rerror_rate(double dst_host_srv_rerror_rate) {
        this.dst_host_srv_rerror_rate = dst_host_srv_rerror_rate;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getProtocol_type() {
        return protocol_type;
    }

    public void setProtocol_type(String protocol_type) {
        this.protocol_type = protocol_type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public long getSrc_bytes() {
        return src_bytes;
    }

    public void setSrc_bytes(long src_bytes) {
        this.src_bytes = src_bytes;
    }

    public long getDst_bytes() {
        return dst_bytes;
    }

    public void setDst_bytes(long dst_bytes) {
        this.dst_bytes = dst_bytes;
    }

    public int getLand() {
        return land;
    }

    public void setLand(int land) {
        this.land = land;
    }

    public int getWrong_fragment() {
        return wrong_fragment;
    }

    public void setWrong_fragment(int wrong_fragment) {
        this.wrong_fragment = wrong_fragment;
    }
}