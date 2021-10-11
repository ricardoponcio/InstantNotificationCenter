package dev.poncio.SystemApps.InstantNotificationCenter.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.poncio.SystemApps.InstantNotificationCenter.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	public UserService() {

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		dev.poncio.SystemApps.InstantNotificationCenter.entities.User user = this.repository.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException("Usuário informado não existe!");
		return new User(user.getEmail(), user.getPassword(), getAuthorities(user));
	}

	private List<GrantedAuthority> getAuthorities(dev.poncio.SystemApps.InstantNotificationCenter.entities.User user) {
		if (user.getRoles() != null && user.getRoles().split(", ").length > 0) {
			return Arrays.asList(user.getRoles().split(", ")).stream().map(role -> "ROLE_" + role.trim().toUpperCase())
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		} else {
			return Arrays.asList("ROLE_USER").stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
		}
	}

}