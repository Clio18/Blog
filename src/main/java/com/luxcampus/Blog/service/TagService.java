package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class TagService implements TagServiceInterface {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public void addTagToPost(Tag tag, Long postId) {
        //check if tag from request is already in db
        Tag tagFromDB = tagRepository.findByName(tag.getName());
        Post post = postRepository.getById(postId);
        if (tagFromDB==null){
            //it is new tag, it should be saved in db
            post.getTags().add(tag);
            tag.getPosts().add(post);
            tagRepository.save(tag);
        }else {
            //check if this tag is already exist in post
            Set<Tag> tagsInPost = post.getTags();
            for (Tag tagInPost : tagsInPost) {
                if(tagInPost.getName().equals(tagFromDB.getName())){
                    return;
                }
            }
            //this post has not such tagFromDB, it should be added
            post.getTags().add(tagFromDB);
            tagFromDB.getPosts().add(post);
        }
    }

    @Override
    @Transactional
    public void deleteTagFromPost(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()) {
            List<Post> postList = postRepository.findAll();
            Tag tag = optionalTag.get();
            for (Post post : postList) {
                Set<Tag> tagSet = post.getTags();
                tagSet.remove(tag);
            }
            tagRepository.delete(tag);
        }
    }
}
