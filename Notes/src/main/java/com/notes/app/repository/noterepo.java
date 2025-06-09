package com.notes.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.notes.app.entity.notes;


@Repository
public interface noterepo extends JpaRepository<notes,Long> {
	
}
