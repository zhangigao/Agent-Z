package org.zhj.agentz.interfaces.dto.agent;


import org.zhj.agentz.domain.agent.constant.PublishStatus;
import org.zhj.agentz.infrastructure.exception.ParamValidationException;
import org.zhj.agentz.infrastructure.utils.ValidationUtils;

/**
 * 审核/更新Agent版本状态的请求对象
 */
public class ReviewAgentVersionRequest {

    /**
     * 目标状态: PUBLISHED-发布, REJECTED-拒绝, REMOVED-下架, REVIEWING-审核中
     */
    private PublishStatus status;

    /**
     * 拒绝原因，当status为REJECTED时必填
     */
    private String rejectReason;

    // 构造方法
    public ReviewAgentVersionRequest() {
    }

    public ReviewAgentVersionRequest(PublishStatus status, String rejectReason) {
        this.status = status;
        this.rejectReason = rejectReason;
    }

    /**
     * 校验请求参数
     */
    public void validate() {
        ValidationUtils.notNull(status, "status");

        // 如果是拒绝，必须提供拒绝原因
        if (PublishStatus.REJECTED.equals(status) &&
                (rejectReason == null || rejectReason.trim().isEmpty())) {
            throw new ParamValidationException("rejectReason", "拒绝时必须提供拒绝原因");
        }

        // 取消状态限制，允许所有合法的PublishStatus值
    }

    // Getter和Setter
    public PublishStatus getStatus() {
        return status;
    }

    public void setStatus(PublishStatus status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}