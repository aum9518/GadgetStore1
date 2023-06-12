package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JWTService;
import peaksoft.dto.dtoAuthentication.AdminTokenRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationResponse;
import peaksoft.dto.dtoAuthentication.SignIn;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse adminToken(AdminTokenRequest adminTokenRequest) {
        // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.email(),authenticationRequest.password()));
//        User user = userRepository.getUserByEmail(adminTokenRequest.email()).orElseThrow(() -> new UsernameNotFoundException("user with email: " +adminTokenRequest.email()  + " is not found!"));
        User user1 = userRepository.findById(1L).orElseThrow(() -> new UsernameNotFoundException("user with email: 1 is not found!"));
        String token = jwtService.generateToken(user1);
        System.out.println(token);
        return AuthenticationResponse.builder()
                .email(user1.getEmail())
                .token(token)
                .role(user1.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signUp(AuthenticationRequest authenticationRequest) {
        if (userRepository.existsByEmail(authenticationRequest.email())){
            throw new  EntityExistsException("user with email: "+ authenticationRequest.email()+" already exist");
        }
        User user = new User();
        user.setFirstName(authenticationRequest.firstName());
        user.setLastName(authenticationRequest.lastName());
        user.setEmail(authenticationRequest.email());
        user.setRole(authenticationRequest.role());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setCreatedAt(ZonedDateTime.now());
        user.setUpdateDate(ZonedDateTime.now());
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignIn signIn) {
        User user = userRepository.getUserByEmail(signIn.email()).orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        if (signIn.email().isBlank()){
            throw new BadCredentialsException("email does not exist...");
        }
        if (!passwordEncoder.matches(signIn.password(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
    @PostConstruct
    public  void init(){
        User user = new User();
        user.setFirstName("Manas");
        user.setLastName("Abdugani uulu");
        user.setPassword(passwordEncoder.encode("manas12"));
        user.setEmail("m@gmail.com");
        user.setRole(Role.ADMIN);
        user.setCreatedAt(ZonedDateTime.now());
        user.setUpdateDate(ZonedDateTime.now());
        if (!userRepository.existsByEmail(user.getEmail())){
            userRepository.save(user);
        }
    }
}
