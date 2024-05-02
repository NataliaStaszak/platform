package com.example.platform.resource;

import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.User.User;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    private IndividualTask individualTask;

    @ManyToOne
    @JoinColumn(name = "id_author", referencedColumnName = "id")
    private User author;

    @Column(name = "date")
    private Date date;

    public Resource(IndividualTask individualTask, User author, Date date) {
        this.individualTask = individualTask;
        this.author = author;
        this.date = date;
    }

    public Resource() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
