package com.mydotey.blog.config;

import com.mydotey.blog.entity.Admin;
import com.mydotey.blog.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 检查是否已有管理员账号
            if (adminRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                adminRepository.save(admin);
                System.out.println("✓ 默认管理员账号已创建");
                System.out.println("  用户名: admin");
                System.out.println("  密码: admin123");
                System.out.println("  请登录后立即修改密码！");
            }
        };
    }
}
