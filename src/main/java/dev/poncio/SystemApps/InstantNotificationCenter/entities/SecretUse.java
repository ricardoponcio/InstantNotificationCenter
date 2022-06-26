package dev.poncio.SystemApps.InstantNotificationCenter.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SECRET_USE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretUse {
    
    @Id
    @JsonIgnore
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secret_use_id_seq")
	@SequenceGenerator(name = "secret_use_id_seq", sequenceName = "secret_use_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

    @Column(nullable = true)
    private String ip;

    @Column(nullable = true)
    private String browser;

    @Column(name = "BROWSER_VERSION", nullable = true)
    private String browserVersion;

    @Column(name = "OPERATING_SYSTEM", nullable = true)
    private String operatingSystem;

    @Column(name = "DEVICE_TYPE", nullable = true)
    private String deviceType;

    @Column(nullable = true)
    private String endpoint;

    @Column(name = "FILTER_PROCEED", nullable = true)
    private Boolean filterProceed;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SECRET", referencedColumnName = "id")
    private Secrets secrets;

}
