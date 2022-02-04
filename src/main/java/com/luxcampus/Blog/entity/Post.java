package com.luxcampus.Blog.entity;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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

    @ManyToMany()
    @JoinTable(name = "POST_TAG",
            joinColumns = @JoinColumn(name = "POST_id"),
            inverseJoinColumns = @JoinColumn(name = "TAG_id"))
    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return star == post.star && Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, star);
    }
}
