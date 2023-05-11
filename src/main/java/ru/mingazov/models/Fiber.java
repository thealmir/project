package ru.mingazov.models;

import lombok.*;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Fiber {

    private Long id;
    private String section;
    private LocalDateTime creationDate;
    private Long commentTo;
    private List<File> files;

    public Fiber(String comment_to, String comment) {
        this.section = comment;
        this.commentTo = Long.parseLong(comment_to);
    }

    public String creationDateToString() {
        String dayOfMonth = this.creationDate.getDayOfMonth() < 10 ?
                "0" + this.creationDate.getDayOfMonth() : Integer.toString(this.creationDate.getDayOfMonth());
        String month = this.creationDate.getMonthValue() < 10 ?
                "0" + this.creationDate.getMonthValue() : Integer.toString(this.creationDate.getMonthValue());
        String hour = this.creationDate.getHour() < 10 ?
                "0" + this.creationDate.getHour() : Integer.toString(this.creationDate.getHour());
        String minutes = this.creationDate.getMinute() < 10 ?
                "0" + this.creationDate.getMinute() : Integer.toString(this.creationDate.getMinute());
        String seconds = this.creationDate.getSecond() < 10 ?
                "0" + this.creationDate.getSecond() : Integer.toString(this.creationDate.getSecond());

        return dayOfMonth + "-" + month + "-" +
                this.creationDate.getYear() + " " + hour + ":" + minutes + ":" + seconds;
    }

}
