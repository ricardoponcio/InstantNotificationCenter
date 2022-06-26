package dev.poncio.SystemApps.InstantNotificationCenter.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.SecretsCreateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.SecretUse;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.SecretRepository;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.SecretUseRepository;

@Service
public class SecretsService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecretRepository repository;

	@Autowired
	private SecretUseRepository secretUseRepository;

	@Autowired
	private UserService userService;

	public List<Secrets> listSecretsUser() {
		return this.repository.findAllByUserAndActiveTrue(this.userService.currentContextUser());
	}

	public Secrets generateNewSecret(SecretsCreateDTO createDTO) {
		Secrets secret = new Secrets();
		secret.setActive(Boolean.TRUE);
		secret.setName(createDTO.getName());
		secret.setDescription(createDTO.getDescription());
		secret.setUser(this.userService.currentContextUser());

		String token = null;
		while (token == null || this.repository.findOneBySecretAndActiveTrue(token) != null) {
			token = UUID.randomUUID().toString().replace("-", "");
		}
		secret.setSecret(token);

		return this.repository.saveAndFlush(secret);
	}

	public boolean deleteSecret(Long secretId) {
		try {
			Secrets secret = this.repository.findOneByIdAndUserAndActiveTrue(secretId,
					this.userService.currentContextUser());
			if (secret == null)
				throw new Exception("Secret ID não encontrado!");
			secret.setActive(Boolean.FALSE);
			secret.setDateEnd(new Date());
			this.repository.saveAndFlush(secret);
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete Secret " + secretId, e);
			return false;
		}
	}

	public Secrets findValidToken(String token) {
		return this.repository.findOneBySecretAndActiveTrue(token);
	}

	public SecretUse persistSecretUse(SecretUse use) {
		return this.secretUseRepository.save(use);
	}

	public SecretUse findUseById(Long id) {
		return this.secretUseRepository.findById(id).get();
	}

}
