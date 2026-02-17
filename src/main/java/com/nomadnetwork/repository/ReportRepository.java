package com.nomadnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.Report;
import com.nomadnetwork.entity.User;

public interface ReportRepository extends JpaRepository<Report, Long>{
	boolean existsByPostAndReportedBy(Post post, User reportedBy);
	@Query("""
		    SELECT r.post, COUNT(r)
		    FROM Report r
		    GROUP BY r.post
		    ORDER BY COUNT(r) DESC
		""")
		List<Object[]> findReportedPostsWithCount();
		
		List<Report> findByPost(Post post);
		
		List<Report> findAllByOrderByReportedAtDesc();
}
