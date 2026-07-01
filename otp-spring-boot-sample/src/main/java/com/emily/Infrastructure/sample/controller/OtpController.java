package com.emily.Infrastructure.sample.controller;

import com.emily.Infrastructure.otp.algorithm.OtpGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码 REST 接口
 */
@RestController
public class OtpController {

    private final OtpGenerator otpService;

    public OtpController(OtpGenerator otpService) {
        this.otpService = otpService;
    }

    // ==================== OTP一次性密码接口 ====================

    /**
     * 为用户生成OTP密钥
     * POST /api/otp/secret?account=user@example.com
     */
    @PostMapping("/api/otp/secret")
    public Map<String, Object> generateOtpSecret(@RequestParam String account) {
        String secret = otpService.generateSecret(account);
        String otpAuthUri = otpService.generateOtpAuthUri(account, "LIME");

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("account", account);
        data.put("secret", secret);
        data.put("otpAuthUri", otpAuthUri);
        result.put("data", data);
        return result;
    }

    /**
     * 获取用户的OTP密钥
     * GET /api/otp/getOtpSecret?account=user@example.com
     */
    @GetMapping("/api/otp/getOtpSecret")
    public Map<String, Object> getOtpSecret(@RequestParam String account) {
        String secret = otpService.getSecret(account);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("account", account);
        data.put("secret", secret);
        data.put("enabled", secret != null);
        result.put("data", data);
        return result;
    }

    /**
     * 验证OTP密码
     * POST /api/otp/verify
     * Body: { "account": "user@example.com", "otp": "123456" }
     */
    @PostMapping("/api/otp/verify")
    public Map<String, Object> verifyOtp(@RequestBody OtpVerifyRequest request) {
        boolean success = otpService.verify(request.getAccount(), request.getOtp());

        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 400);
        result.put("message", success ? "验证通过" : "验证失败");
        return result;
    }

    /**
     * 检查用户是否已启用OTP
     * GET /api/otp/enabled?account=user@example.com
     */
    @GetMapping("/api/otp/enabled")
    public Map<String, Object> checkOtpEnabled(@RequestParam String account) {
        boolean enabled = otpService.isEnabled(account);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("account", account);
        data.put("enabled", enabled);
        result.put("data", data);
        return result;
    }

    /**
     * 删除用户的OTP配置
     * DELETE /api/otp?account=user@example.com
     */
    @PostMapping("/api/otp/remove")
    public Map<String, Object> removeOtp(@RequestParam String account) {
        otpService.remove(account);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "OTP已删除");
        return result;
    }

    /**
     * OTP验证码校验请求体
     */
    public static class OtpVerifyRequest {
        private String account;
        private String otp;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }
}
