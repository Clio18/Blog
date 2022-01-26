package com.luxcampus.Blog.entity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Table(name = "post")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    @SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize=1)
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
}
