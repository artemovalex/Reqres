package by.examle.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Builder
@Accessors(chain = true)

public class User {
    public int id;
    public String name;
    public String job;
    public String createdAt;
    public String updatedAt;
}