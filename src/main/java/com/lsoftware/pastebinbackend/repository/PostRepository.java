package com.lsoftware.pastebinbackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lsoftware.pastebinbackend.entities.PostEntity;



@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long>  {
    
	
	List<PostEntity> getByUserIdOrderByCreatedAtDesc(long userId);


    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure and p.expires_at > :now ORDER BY created_at DESC LIMIT 20", nativeQuery = true)
    List<PostEntity> getLastPublicPosts(@Param("exposure") long exposureId, @Param("now") Date now);

    PostEntity findByPostId(String postId);
    
}
