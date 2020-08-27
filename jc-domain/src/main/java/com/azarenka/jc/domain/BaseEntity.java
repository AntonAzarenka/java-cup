package com.azarenka.jc.domain;

import java.time.LocalDateTime;

/**
 * Base class for Entity.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
public abstract class BaseEntity {

    private String id;
    private String createdUser;
    private String updatedUser;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String updatedDate;
    private int recordVersion = 0;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public int getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        this.recordVersion = recordVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
            .append(recordVersion, that.recordVersion)
            .append(id, that.id)
            .append(createdUser, that.createdUser)
            .append(updatedUser, that.updatedUser)
            .append(createdDate, that.createdDate)
            .append(updatedDate, that.updatedDate)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder()
            .append(id)
            .append(createdUser)
            .append(updatedUser)
            .append(createdDate)
            .append(updatedDate)
            .append(recordVersion)
            .toHashCode();
    }
}
