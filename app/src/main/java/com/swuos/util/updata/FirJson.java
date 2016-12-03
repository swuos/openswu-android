package com.swuos.util.updata;

/**
 * Created by 张孟尧 on 2016/11/28.
 */

public class FirJson {
    /**
     * name : 西大助手
     * version : 6
     * changelog : 1.修复校园网wifi认证
     * 2.图书馆借阅信息ui美化
     * 3.修复图书馆搜索
     * 4.感谢使用西大助手的每一个人.
     * updated_at : 1479788844
     * versionShort : 1.5.0
     * build : 6
     * installUrl : http://download.fir.im/v2/app/install/5780f2d4e75e2d1bb000000e?download_token=aa7f6fefaeae41ba05a42d5a1c646d3e
     * install_url : http://download.fir.im/v2/app/install/5780f2d4e75e2d1bb000000e?download_token=aa7f6fefaeae41ba05a42d5a1c646d3e
     * direct_install_url : http://download.fir.im/v2/app/install/5780f2d4e75e2d1bb000000e?download_token=aa7f6fefaeae41ba05a42d5a1c646d3e
     * update_url : http://fir.im/h1ka
     * binary : {"fsize":7380399}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 7380399
     */

    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
