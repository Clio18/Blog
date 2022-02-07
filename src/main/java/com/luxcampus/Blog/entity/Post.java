package com.luxcampus.Blog.entity;

import lombok.*;
import javax.persistence.*;
import java.util.*;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
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
        return star == post.star && Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(comments, post.comments) && Objects.equals(tags, post.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, star, comments, tags);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", comments=" + comments.size() +
                ", tags=" + tags.size() +
                '}';
    }
}
