//package com.project.forum.mapper;
//
//import com.project.forum.util.CustomTimestamp;
//import org.mapstruct.Qualifier;
//
//import java.time.LocalDateTime;
//
//@Qualifier
//@interface CustomTimestampTranslator { }
//@Qualifier @interface MapCreatedTime { }
//
//@CustomTimestampTranslator
//public class CustomTimestampMapper {
//    @MapCreatedTime
//    public String mapCreatedTime(LocalDateTime createdTime) {
//        CustomTimestamp timestamp = new CustomTimestamp();
//        timestamp.setTimestamp(createdTime);
//
//        return String.valueOf(timestamp);
//    }
//}
