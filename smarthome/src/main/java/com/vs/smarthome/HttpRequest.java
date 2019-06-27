package com.vs.smarthome;

public class HttpRequest {

    private String reqType;
    private String reqFile;
    private String reqHttpVersion;
    private String reqHost;
    private String reqPort;
    private String reqConnection;
    private String reqCacheControl;
    private String reqUpgradeInsecureRequests;
    private String reqUserAgent;
    private String reqAccept;
    private String reqDNT;
    private String reqAcceptEncoding;
    private String reqAcceptLanguage;

    public HttpRequest(String reqString) {
        String lines[] = reqString.split("\n");
        reqType = lines[0].split(" ")[0];
        //reqFile = lines[0].split(" ")[1];
        reqHttpVersion = lines[0].split(" ")[2];
        reqHost = lines[1].split(" ")[1].split(":")[0];
        reqPort = lines[1].split(" ")[1].split(":")[1];
        reqConnection = lines[2].split(" ")[1];
        reqCacheControl = lines[3].split(" ")[1];
        reqUpgradeInsecureRequests = lines[4].split(" ")[1];
        reqUserAgent = lines[5].split(":")[1].trim();
        reqAccept = lines[6].split(":")[1].trim();
        //reqDNT = lines[7].split(" ")[1];
        //reqAcceptEncoding = lines[8].split(":")[1].trim();
        //reqAcceptLanguage = lines[9].split(" ")[1];
    }

    public void printRequest() {
        System.out.println();
        System.out.println("Self parsed HTTP Request");
        System.out.println(reqType + " " + reqFile + " " + reqHttpVersion);
        System.out.println("Host: " + reqHost + ":" + reqPort);
        System.out.println("Connection: " + reqConnection);
        System.out.println("Cache-Control: " + reqCacheControl);
        System.out.println("Upgrade-Insecure-Requests: " + reqUpgradeInsecureRequests);
        System.out.println("User-Agent: " + reqUserAgent);
        System.out.println("Accept: " + reqAccept);
        System.out.println("DNT: " + reqDNT);
        System.out.println("Accept-Encoding: " + reqAcceptEncoding);
        System.out.println("Accept-Language: " + reqAcceptLanguage);
        System.out.println();
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqFile() {
        return reqFile;
    }

    public void setReqFile(String reqFile) {
        this.reqFile = reqFile;
    }

    public String getReqHttpVersion() {
        return reqHttpVersion;
    }

    public void setReqHttpVersion(String reqHttpVersion) {
        this.reqHttpVersion = reqHttpVersion;
    }

    public String getReqHost() {
        return reqHost;
    }

    public void setReqHost(String reqHost) {
        this.reqHost = reqHost;
    }

    public String getReqPort() {
        return reqPort;
    }

    public void setReqPort(String reqPort) {
        this.reqPort = reqPort;
    }

    public String getReqConnection() {
        return reqConnection;
    }

    public void setReqConnection(String reqConnection) {
        this.reqConnection = reqConnection;
    }

    public String getReqCacheControl() {
        return reqCacheControl;
    }

    public void setReqCacheControl(String reqCacheControl) {
        this.reqCacheControl = reqCacheControl;
    }

    public String getReqUpgradeInsecureRequests() {
        return reqUpgradeInsecureRequests;
    }

    public void setReqUpgradeInsecureRequests(String reqUpgradeInsecureRequests) {
        this.reqUpgradeInsecureRequests = reqUpgradeInsecureRequests;
    }

    public String getReqUserAgent() {
        return reqUserAgent;
    }

    public void setReqUserAgent(String reqUserAgent) {
        this.reqUserAgent = reqUserAgent;
    }

    public String getReqAccept() {
        return reqAccept;
    }

    public void setReqAccept(String reqAccept) {
        this.reqAccept = reqAccept;
    }

    public String getReqDNT() {
        return reqDNT;
    }

    public void setReqDNT(String reqDNT) {
        this.reqDNT = reqDNT;
    }

    public String getReqAcceptEncoding() {
        return reqAcceptEncoding;
    }

    public void setReqAcceptEncoding(String reqAcceptEncoding) {
        this.reqAcceptEncoding = reqAcceptEncoding;
    }

    public String getReqAcceptLanguage() {
        return reqAcceptLanguage;
    }

    public void setReqAcceptLanguage(String reqAcceptLanguage) {
        this.reqAcceptLanguage = reqAcceptLanguage;
    }
}
