package com.notes.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.notes.app.entity.notes;
import java.util.List;

@Repository
public interface noterepo extends JpaRepository<notes,Long> {
	
	// Find all notes belonging to a specific user, ordered by update time
//	List<notes> findByUserIdOrderByUpdatedAtDesc(Long userId);
	List<notes> findByusers_IdOrderByUpdatedAtDesc(Long userId);
}