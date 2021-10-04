package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.AuthDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.UserDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.ResponseEntity;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.UserRepository;
import dev.poncio.SystemApps.InstantNotificationCenter.utils.JwtTokenUtil;

@Controller
@RestController
@RequestMapping("/user/")
public class UserController extends AbstractController {
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @GetMapping("/list")
	public ResponseEntity<List<User>> listUsers() {
        try {
            return new ResponseEntity<List<User>>().status(true).attach(repository.findAll());
        } catch(Exception e) {
            return new ResponseEntity<List<User>>().status(false).message(e.toString());
        }
    }

    @PostMapping("/login")
	public ResponseEntity<AuthDTO> generateToken(@RequestBody UserDTO usuarioDto) {
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuarioDto.getUsername(), usuarioDto.getPassword()));
			String token = jwtTokenUtil.generateToken((org.springframework.security.core.userdetails.User) auth.getPrincipal());
			Long expiresIn = jwtTokenUtil.getExpirationDateFromToken(token).getTime();
			return new ResponseEntity<AuthDTO>().status(true)
				.attach(AuthDTO.builder()
							.token(token)
							.expiresIn(expiresIn)
							.build()
				)
				.message("Logado com sucesso!");
		} catch (BadCredentialsException | UsernameNotFoundException ex) {
			return new ResponseEntity<AuthDTO>().status(false).message(messageService.get("usuario.erro.credenciais"));
		} catch (Exception ex) {
			return new ResponseEntity<AuthDTO>().status(false).message(ex.toString());
		}
	}

}
