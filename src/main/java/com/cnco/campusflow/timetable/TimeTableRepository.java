package com.cnco.campusflow.timetable;

import com.cnco.campusflow.jwt.RefreshTokenEntity;
import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimetableEntity, Long> {


}
