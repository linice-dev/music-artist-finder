package com.linicedev.music_artist_finder.lock.domain;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "music_artist_finder", name = "api_lock")
public class ApiLockEntity implements Serializable {

    private static final long serialVersionUID = 5130684794353203758L;
    @Id
    private Long id;
    private String lockName;
    private Long count;
    private Long currentHour;

    public ApiLockEntity() {
    }

    public ApiLockEntity(Long id, String lockName) {
        this.id = requireNonNull(id);
        this.lockName = requireNonNull(lockName);
        this.count = 0L;
        this.currentHour = System.currentTimeMillis() / 1000 / 3600;
    }

    public Long getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }
}
