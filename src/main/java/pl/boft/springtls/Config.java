package pl.boft.springtls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
class Config extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService userDetails() {
        return new InMemoryUserDetailsManager(
            User.withUsername("user")
                .password("secret")
                .passwordEncoder(it -> passwordEncoder().encode(it))
                .roles("USER")
                .build()
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .headers().httpStrictTransportSecurity().disable()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .and()
            .authorizeRequests().anyRequest().authenticated();
    }

}
