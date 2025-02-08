package com.getourguide.interview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getourguide.interview.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Page<Activity> findByTitleContaining(String title, Pageable pageable);
}
