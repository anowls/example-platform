package org.zongzi.platform.common.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haopeisong
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"modifierId", "modifyTime", "deleted"})
public class BaseDomain {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CREATOR_ID")
    private String creatorId;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "MODIFIER_ID")
    private String modifierId;

    @Column(name = "MODIFY_TIME")
    private LocalDateTime modifyTime;

    @Column(name = "DELETED")
    private Boolean deleted = false;

    @Getter(onMethod_ = {@JsonAnyGetter})
    private transient Map<String, Object> extraMap = new HashMap<>();

    @JsonAnySetter
    public void setExtraMap(String key, Object value) {
        extraMap.put(key, value);
    }

    public Object get(String key) {
        return extraMap.get(key);
    }
}
