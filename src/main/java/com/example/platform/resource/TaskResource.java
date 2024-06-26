package com.example.platform.resource;

import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "resource")
public class TaskResource {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private IndividualTask individualTask;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    private Date date;
    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    public TaskResource() {

    }

    public TaskResource(String id, IndividualTask individualTask, User author, Date date, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.individualTask = individualTask;
        this.author = author;
        this.date = date;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public TaskResource(IndividualTask individualTask, User author, Date date, String fileName, String fileType, byte[] data) {
        this.individualTask = individualTask;
        this.author = author;
        this.date = date;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IndividualTask getIndividualTask() {
        return individualTask;
    }

    public void setIndividualTask(IndividualTask individualTask) {
        this.individualTask = individualTask;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
