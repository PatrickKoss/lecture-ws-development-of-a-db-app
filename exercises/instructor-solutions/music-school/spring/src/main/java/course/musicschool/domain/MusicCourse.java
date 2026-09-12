package course.musicschool.domain;

import java.math.BigDecimal;

public record MusicCourse(Long id, String courseCode, String title, BigDecimal fee) {}
