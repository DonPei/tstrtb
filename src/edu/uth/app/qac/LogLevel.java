package edu.uth.app.qac;

import lombok.Getter;

@Getter
public enum LogLevel {
    OFF(6, "OFF"), ERROR(5, "ERROR"), WARNING(4, "WARNING"), INFO(3, "INFO"), DEBUG(2, "DEBUG"), TRACE(1, "TRACE") ;

    private Integer value;
    private String description;

    LogLevel(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static LogLevel levelOf(Integer value) {
        for (LogLevel level : LogLevel.values()) {
            if (level.value.equals(value)) {
                return level;
            }
        }
        return null;
    }
}
