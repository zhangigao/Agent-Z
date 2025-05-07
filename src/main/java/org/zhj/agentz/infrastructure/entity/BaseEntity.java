package org.zhj.agentz.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.apache.ibatis.jdbc.Null;

import java.time.LocalDateTime;

public class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedAt;

    @TableLogic(
            value = "NULL",             // 未删除时数据库中的值（NULL）
            delval = "NOW()"            // 删除时自动填充当前时间戳
    )
    protected LocalDateTime deletedAt;

    @TableField(exist = false)
    private Operator operatedBy = Operator.USER;

    public void setAdmin() {
        this.operatedBy = Operator.ADMIN;
    }

    public boolean needCheckUserId() {
        return this.operatedBy == Operator.USER;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Operator getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(Operator operatedBy) {
        this.operatedBy = operatedBy;
    }
}
