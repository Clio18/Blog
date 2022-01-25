package com.luxcampus.Blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@Setter
//@Getter
@Builder
@Table(name = "comment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    //TODO: sequence generator

    private Long id;
    @Column
    private String text;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime created_on;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    //@JsonBackReference
    private Post post;

//    @Override
//    public String toString() {
//        return "Comment{" +
//                "id=" + id +
//                ", text='" + text + '\'' +
//                ", created_on=" + created_on +
//                ", post id=" + post.getId() +
//                '}';
//    }
}
