package com.example.platform.resource;

import java.util.Date;

public class TaskResourceDTO {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private Date uploadDate;
    private Long AuthorId;

    public TaskResourceDTO() {
    }

    public TaskResourceDTO(String fileName, String downloadURL, String fileType, Date uploadDate, Long authorId) {
        this.fileName = fileName;
        this.downloadURL = downloadURL;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
        AuthorId = authorId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(Long authorId) {
        AuthorId = authorId;
    }
}
