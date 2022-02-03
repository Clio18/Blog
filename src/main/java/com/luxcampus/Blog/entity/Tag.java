package com.luxcampus.Blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ")
    @SequenceGenerator(name = "TAG_SEQ", sequenceName = "tag_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "TAG_POST",
            joinColumns = @JoinColumn(name = "TAG_id"),
            inverseJoinColumns = @JoinColumn(name = "POST_id"))
    private List<Post> posts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
