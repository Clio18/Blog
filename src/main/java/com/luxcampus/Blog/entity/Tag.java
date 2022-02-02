package com.luxcampus.Blog.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "TAG_SEQ", allocationSize = 1)
    private Long tagId;
    @Column
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

}
