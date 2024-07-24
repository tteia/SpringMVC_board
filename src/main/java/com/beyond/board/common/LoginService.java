package com.beyond.board.common;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LoginService implements UserDetailsService { // spring 에서 login 하면 매핑 안 해도 찾으러 온다!

    @Autowired
    private AuthorService authorService;

    @Override                               // 우리는 username 이 email 이다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Author author = authorService.authorFindByEmail(username);
        // UserDetails 라는 인터페이스를 return 할 거라서 User 객체 활용. (Spring 제공)
        // User 로 타고 들어가서 요구 사항에 맞춰서 작성해줌.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole().toString()));
        User user = new User(author.getEmail(), author.getPassword(), authorities);
        return user;
    }
}
