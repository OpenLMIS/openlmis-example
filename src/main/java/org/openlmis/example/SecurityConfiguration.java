package org.openlmis.example;

import org.openlmis.example.domain.Role;
import org.openlmis.example.domain.User;
import org.openlmis.example.repository.UserRepository;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PGPoolingDataSource dataSource;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .anyRequest().permitAll()
        .and()
      .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(encoder);
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .authoritiesByUsernameQuery("SELECT username,role from users where username = ?");

    if (!userRepository.findOneByUsername("user").isPresent()) {
      User user = new User();
      user.setUsername("user");
      user.setPassword(encoder.encode("password"));
      user.setRole(Role.USER);
      user.setEnabled(true);
      userRepository.save(user);
    }

    if (!userRepository.findOneByUsername("admin").isPresent()) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setPassword(encoder.encode("password"));
      admin.setRole(Role.ADMIN);
      admin.setEnabled(true);
      userRepository.save(admin);
    }
  }
}
