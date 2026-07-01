package com.emily.Infrastructure.otp;

import com.emily.Infrastructure.otp.algorithm.OtpGenerator;
import com.emily.Infrastructure.otp.store.DefaultOtpStoreServiceImpl;
import com.emily.Infrastructure.otp.store.OtpStoreService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 验证码自动配置
 */
@AutoConfiguration
@EnableConfigurationProperties(OtpProperties.class)
@ConditionalOnProperty(prefix = OtpProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class OtpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OtpGenerator otpGenerator(OtpProperties properties, OtpStoreService otpStoreService) {
        return new OtpGenerator(properties, otpStoreService);
    }

    @Bean
    @ConditionalOnMissingBean
    public OtpStoreService otpStoreService() {
        return new DefaultOtpStoreServiceImpl();
    }

    /**
     * 定时清理过期验证码，每 60 秒执行一次
     */
    @Configuration
    @EnableScheduling
    static class OtpCleanupConfiguration {

        private final OtpStoreService otpStoreService;

        OtpCleanupConfiguration(
                OtpStoreService otpStoreService) {
            this.otpStoreService = otpStoreService;
        }

        @Scheduled(fixedDelay = 60_000)
        public void cleanExpiredOtp() {
            otpStoreService.cleanExpired();
        }
    }
}
