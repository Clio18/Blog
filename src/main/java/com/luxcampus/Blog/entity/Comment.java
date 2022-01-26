package com.luxcampus.Blog.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Table(name = "comment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    @SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize=1)
    private Long id;
    @Column
    private String text;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime created_on;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    //@JsonBackReference
    private Post post;
}
