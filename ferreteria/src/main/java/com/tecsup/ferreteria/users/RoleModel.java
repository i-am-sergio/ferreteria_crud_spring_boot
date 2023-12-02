package com.tecsup.ferreteria.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "Role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nombre;
}
