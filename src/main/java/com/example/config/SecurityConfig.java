package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    /** パスワードの暗号化 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    /** セキュリティの各種設定 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/user/signup").permitAll()
                .requestMatchers("/user/signup/rest").permitAll()
                .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")// ここまでは、ログイン不要の設定
                .anyRequest().authenticated())
                .formLogin(login -> login //フォームログイン
                        .loginProcessingUrl("/login") //ログイン処理のパス
                        .loginPage("/login") //ログインページの指定
                        .failureUrl("/login?error")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/user/list", true)
                        .permitAll())
                .logout(logout -> logout // ログアウト処理
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout"));

        // CSRF対策を無効に設定（一時的）
        //http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    /** インメモリ認証の設定 */
    /*    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        List<UserDetails> users = new ArrayList<UserDetails>();
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("GENERAL")
                .build();
        users.add(user);
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        users.add(admin);
        return new InMemoryUserDetailsManager(users);
    }*/

    /** データベース認証の設定 */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
