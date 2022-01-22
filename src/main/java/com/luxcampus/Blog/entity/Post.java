package com.luxcampus.Blog.entity;
import lombok.*;
import javax.persistence.*;

@Data
@Builder
@Table(name = "post")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
}
