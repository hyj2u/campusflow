package com.cnco.campusflow.timetable;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final AppUserRepository appUserRepository;
    private final CodeRepository codeRepository;
    private final CourseRepository courseRepository;

    public TimetableEntity registerTimetable(TimeTableDto timetableDTO, AppUserEntity appUser) {
        TimetableEntity timetable = new TimetableEntity();
        timetable.setTmTableName(timetableDTO.getTmTableName());
        timetable.setCategory(timetableDTO.getCategory());

        if (appUser.getTimetables() == null) {
            appUser.setTimetables(new ArrayList<>());
        }
        // 사용자에 새 시간표 추가
        appUser.getTimetables().add(timetable);
        // Cascade 옵션으로 인해, 사용자 저장 시 시간표도 저장됨
        TimetableEntity timetableEntity =timeTableRepository.save(timetable);
        appUserRepository.save(appUser);

        return timetableEntity;
    }

    public CourseEntity registerCourse(CourseDto courseDto) {
        TimetableEntity timetable = timeTableRepository.findById(courseDto.getTimeTableId()).orElseThrow(() ->
                new IllegalArgumentException("유효하지 않은 Table Id입니다."));
        CourseEntity lecture;
        if(courseDto.getCourseId()!=null) {
            lecture = courseRepository.findById(courseDto.getCourseId()).orElseThrow(() ->
                    new IllegalArgumentException("유효하지 않은 Course Id입니다."));
        }else{
            lecture = new CourseEntity();
        }
        lecture.setCourseName(courseDto.getCourseName());
        lecture.setColor(courseDto.getColor());
        lecture.setStartTime(courseDto.getStartTime());
        lecture.setEndTime(courseDto.getEndTime());
        lecture.setLocation(courseDto.getLocation());
        lecture.setProfessor(courseDto.getProfessor());
        // 요일 세팅
        List<CodeEntity> days = courseDto.getDays().stream()
                .map(codeCd -> {
                    CodeEntity code = codeRepository.findByCodeCd(codeCd)
                            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 코드입니다: " + codeCd));
                    return code;
                })
                .collect(Collectors.toList());

        lecture.setDays(days); // 이 시점에서 연결 완료
        CourseEntity savedLecture = courseRepository.save(lecture); //  save 이후 조인 테이블 insert 발생
        timetable.getCourses().add(savedLecture);
        timeTableRepository.save(timetable);
        return savedLecture;

    }

    public void changeTimeTableName(Long timeTableId, String name) {
        TimetableEntity timetable = timeTableRepository.findById(timeTableId).orElseThrow(() ->
                new IllegalArgumentException("유효하지 않은 Table Id입니다."));
        timetable.setTmTableName(name);
        timeTableRepository.save(timetable);
    }
    public CourseEntity getCourse(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalArgumentException("유효하지 않은 강의 Id입니다."));
        return course;
    }
    public List<TimeTableListDto> getTimeTableList(AppUserEntity appUser) {
        if (appUser == null || appUser.getTimetables() == null) {
            return Collections.emptyList();
        }
        return appUser.getTimetables().stream()
                .sorted(Comparator.comparing(TimetableEntity::getInsertTimestamp).reversed())
                .map(timetable -> {
                    TimeTableListDto dto = new TimeTableListDto();
                    dto.setTmTableId(timetable.getTmTableId());
                    dto.setTmTableName(timetable.getTmTableName());
                    dto.setCategory(timetable.getCategory());
                    // 필요한 다른 필드들도 추가
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public TimeTableDtlDto getTimeTableDtl(Long timeTableId, String day) {
        TimetableEntity timetable = timeTableRepository.findById(timeTableId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Table Id입니다."));
        // DTO 변환
        TimeTableDtlDto dto = new TimeTableDtlDto();
        dto.setTmTableId(timetable.getTmTableId());
        dto.setTmTableName(timetable.getTmTableName());

        if (timetable.getCourses() != null) {
            var filteredCourses = timetable.getCourses().stream()
                    .filter(course -> {
                        // day가 null이면 모든 course를 포함, day가 있으면 조건에 맞는 course만 포함
                        if (day == null) return true;
                        return course.getDays() != null &&
                                course.getDays().stream().anyMatch(code -> Objects.equals(code.getCodeCd(), day));
                    })
                    .map(course -> {
                        CourseDto courseDto = new CourseDto();
                        courseDto.setCourseId(course.getCourseId());
                        courseDto.setCourseName(course.getCourseName());
                        courseDto.setStartTime(course.getStartTime());
                        courseDto.setEndTime(course.getEndTime());
                        courseDto.setLocation(course.getLocation());
                        courseDto.setProfessor(course.getProfessor());
                        courseDto.setColor(course.getColor());
                        // days 필드 설정: course.getDays()가 null이 아닌 경우 codeId 값 추출
                        if (course.getDays() != null) {
                            courseDto.setDays(course.getDays().stream()
                                    .map(code -> code.getCodeCd())
                                    .collect(Collectors.toList()));
                        }
                        return courseDto;
                    })
                    .collect(Collectors.toList());
            dto.setCourses(filteredCourses);
        }

        return dto;
    }
    public void deleteTmTable(Long timeTableId) {
        timeTableRepository.deleteById(timeTableId);
    }
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
