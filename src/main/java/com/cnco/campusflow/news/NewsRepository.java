package com.cnco.campusflow.news;

import com.cnco.campusflow.timetable.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    List<NewsEntity> findAllByActiveYn(String activeYn);

}
