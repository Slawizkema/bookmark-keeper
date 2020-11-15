package ru.ssharaev.bookmarkkeeper.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Data
@JsonAutoDetect
public class CallBackData {
    private final String messageId;
    private final CommandType commandType;
    private final String category;
}
