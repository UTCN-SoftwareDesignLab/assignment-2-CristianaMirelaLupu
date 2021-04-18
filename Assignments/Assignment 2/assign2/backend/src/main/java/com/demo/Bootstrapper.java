package com.demo;

import com.demo.book.BookService;
import com.demo.book.model.dto.BookDTO;
import com.demo.security.AuthService;
import com.demo.book.BookRepository;
import com.demo.security.dto.SignupRequest;
import com.demo.user.RoleRepository;
import com.demo.user.UserRepository;
import com.demo.user.model.ERole;
import com.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    private final BookService bookService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("cristiana.wlf@gmail.com")
                    .username("cristiana_wlf")
                    .password("Aleluia123!")
                    .roles(Set.of("ADMIN"))
                    .build());

            authService.register(SignupRequest.builder()
                    .email("cristiana.wlf@yahoo.com")
                    .username("cristiana_wolf")
                    .password("Aleluia123!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }

        bookService.create(BookDTO.builder()
                .title("Ana Karenina")
                .author("Lev Tolstoi")
                .genre("novel")
                .quantity(67L)
                .price((float) 23.45)
                .build());

        bookService.create(BookDTO.builder()
                .title("Quo Vadis")
                .author("Henryk Sienkiewicz")
                .genre("Nobel Prices")
                .quantity(6L)
                .price((float) 234.5)
                .build());

        bookService.create(BookDTO.builder()
                .title("The Brothers Karamazov")
                .author("Feodor Dostoievski")
                .genre("russian")
                .quantity(6L)
                .price((float) 234.5)
                .build());

        bookService.create(BookDTO.builder()
                .title("The Adolescent")
                .author("Feodor Dostoievski")
                .genre("russian")
                .quantity(8L)
                .price((float) 34.5)
                .build());

        bookService.create(BookDTO.builder()
                .title("Gone with the wind")
                .author("Margaret Mitchell")
                .genre("novel")
                .quantity(0L)
                .price((float) 98.5)
                .build());

    }
}
