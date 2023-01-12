package com.myblogger.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int catId;
	
	@Column(name = "title",length = 20,nullable = false)
	private String catTitle;
	
	@Column(name = "description",length = 100)
	private String catDiscription;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<Post>();
}
