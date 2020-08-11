package com.cyk.file.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文件记录实体
 */
public class FileEntity {

    private int id;
    private String uuid;
    private String ownerId;
    private String fileName;
    private String filePath;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private int signedCode;
    private String signed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }

    public int getSignedCode() {
        return signedCode;
    }

    public void setSignedCode(int signedCode) {
        this.signedCode = signedCode;
    }

    public String getSigned() {
        return 0 == signedCode? "未签" : "已签";
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }
}
