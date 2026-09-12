package course.musicschool.service;

import java.math.BigDecimal;

public record MusicCourseCommand(String courseCode, String title, BigDecimal fee) {}
