package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.UserRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void UserDetailsService_loadUserByUsername(){
        String username = "oriol";
        User user = User.builder().username(username).build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
    }


}
