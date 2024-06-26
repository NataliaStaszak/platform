package com.example.platform.resource;

import com.example.platform.Task.GroupTask.GroupTask;
import com.example.platform.Task.GroupTask.Team;
import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
public class GroupTaskResource {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private GroupTask groupTask;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Team author;

    private Date date;
    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    public GroupTaskResource() {
    }

    public GroupTaskResource(String id, GroupTask groupTask, Team author, Date date, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.groupTask = groupTask;
        this.author = author;
        this.date = date;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public GroupTaskResource(GroupTask groupTask, Team author, Date date, String fileName, String fileType, byte[] data) {
        this.groupTask = groupTask;
        this.author = author;
        this.date = date;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GroupTask getGroupTask() {
        return groupTask;
    }

    public void setGroupTask(GroupTask groupTask) {
        this.groupTask = groupTask;
    }

    public Team getAuthor() {
        return author;
    }

    public void setAuthor(Team author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
