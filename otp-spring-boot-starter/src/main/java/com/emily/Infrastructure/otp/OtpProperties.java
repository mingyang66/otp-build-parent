package com.emily.Infrastructure.otp;


import com.emily.Infrastructure.otp.algorithm.OtpHashAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 验证码配置属性
 */
@ConfigurationProperties(prefix = OtpProperties.PREFIX)
public class OtpProperties {
    /**
     * 配置属性前缀
     */
    public static final String PREFIX = "spring.emily.otp";
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * OTP密码长度（默认6位）
     */
    private int codeLength = 6;

    /**
     * 时间步长（默认30秒），生成器刷新周期
     */
    private Duration timeStep = Duration.ofSeconds(30);

    /**
     * 允许的时间窗口偏移（默认:1），用于处理时钟不同步
     */
    private int windowSize = 1;

    /**
     * 密钥长度（字节），默认32字节（256位）
     * <p>
     * RFC 6238 推荐值：SHA1=20, SHA256=32, SHA512=64
     */
    private int secretKeyLength = 20;

    /**
     * 哈希算法（默认HMAC_SHA256）
     * <p>
     * 可选值：HMAC_SHA1, HMAC_SHA256, HMAC_SHA512
     */
    private OtpHashAlgorithm algorithm = OtpHashAlgorithm.HMAC_SHA1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public Duration getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Duration timeStep) {
        this.timeStep = timeStep;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getSecretKeyLength() {
        return secretKeyLength;
    }

    public void setSecretKeyLength(int secretKeyLength) {
        this.secretKeyLength = secretKeyLength;
    }

    public OtpHashAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(OtpHashAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
