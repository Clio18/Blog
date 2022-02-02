package com.luxcampus.Blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Table(name = "post")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "POST_SEQ", allocationSize = 1)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column(columnDefinition = "boolean default false")
    private boolean star;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,
            orphanRemoval = true)
    //@JsonManagedReference
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name="tagId"))
    private Set<Tag> tags;

}
