package com.fran.spring_boot_neo4j.objects;
import com.fran.spring_boot_neo4j.models.Clan;
import java.util.Map;
import lombok.Data;

/**
 * Data Transfer Object for {@link Clan}.
 */
@Data
public class ClanDTO {

    private Long id;
    private String identifier;
    private Map<String, String> clanName;

    public ClanDTO() {}

    public ClanDTO(Long id, String identifier, Map<String, String> clanName) {
        this.id = id;
        this.identifier = identifier;
        this.clanName = clanName;
    }
}
