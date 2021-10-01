package dev.poncio.SystemApps.InstantNotificationCenter.entities;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "`GROUP`")
public class Group {
    
    @Id
    @JsonIgnore
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq")
	@SequenceGenerator(name = "group_id_seq", sequenceName = "group_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @JsonIgnore
    @JoinColumn(name = "user", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

}
