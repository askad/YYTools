package yy.traffic;


public class TrafficViolationVO {
    // submit 详细情况
    private String hphm;
    private String hpzl;
    private String wfsj;
    private String wfdd;
    private String wfdz;
    private String wfxw;
    private String qrydept;
    private String cjjg;
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
