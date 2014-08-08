package yy.traffic;


public class TrafficViolationVO {
    // submit 详细情况
    private String hphm;// 牌照号
    private String hpzl;// 小型汽车
    private String wfsj;// 违章时间 2013-03-29 14:23:00
    private String wfdd;// 违反code 50005
    private String wfdz;// 违章地点
    private String wfxw;// 违反规定
    private String qrydept; // 南开支队
    private String cjjg;// 处罚单位
    public String getHphm() {
        return hphm;
    }
    public void setHphm(String hphm) {
        this.hphm = hphm;
    }
    public String getHpzl() {
        return hpzl;
    }
    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }
    public String getWfsj() {
        return wfsj;
    }
    public void setWfsj(String wfsj) {
        this.wfsj = wfsj;
    }
    public String getWfdd() {
        return wfdd;
    }
    public void setWfdd(String wfdd) {
        this.wfdd = wfdd;
    }
    public String getWfdz() {
        return wfdz;
    }
    public void setWfdz(String wfdz) {
        this.wfdz = wfdz;
    }
    public String getWfxw() {
        return wfxw;
    }
    public void setWfxw(String wfxw) {
        this.wfxw = wfxw;
    }
    public String getQrydept() {
        return qrydept;
    }
    public void setQrydept(String qrydept) {
        this.qrydept = qrydept;
    }
    public String getCjjg() {
        return cjjg;
    }
    public void setCjjg(String cjjg) {
        this.cjjg = cjjg;
    }
    @Override
    public String toString() {
        return "TrafficViolationVO [hphm=" + hphm + ", hpzl=" + hpzl + ", wfsj=" + wfsj + ", wfdd=" + wfdd + ", wfdz="
                + wfdz + ", wfxw=" + wfxw + ", qrydept=" + qrydept + ", cjjg=" + cjjg + "]";
    }

}
