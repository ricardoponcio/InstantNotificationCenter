package dev.poncio.SystemApps.InstantNotificationCenter.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "JOB_UPDATE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobUpdate {
    
    @Id
    @JsonIgnore
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_update_id_seq")
	@SequenceGenerator(name = "job_update_id_seq", sequenceName = "job_update_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", nullable = false)
    @Builder.Default
	private Date date = new Date();

    @Column(name = "PERCENT", nullable = true)
    private Integer percent;

    @Column(name = "LOG", nullable = true)
    private String log;

    @JsonIgnore
    @JoinColumn(name = "job", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Job job;

    @JoinColumn(name = "secrets", referencedColumnName = "id")
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Secrets secrets;
    
}
