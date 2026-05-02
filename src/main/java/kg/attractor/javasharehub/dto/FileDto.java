package kg.attractor.javasharehub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileDto {
    private Long id;
    private String name;
    private String uniqueKey;
    private String category;
    private String uploaderEmail;
    private int downloadCount;
    private boolean isPublic;
}