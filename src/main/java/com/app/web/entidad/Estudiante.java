package com.app.web.entidad;

import lombok.*;

import javax.persistence.*;
@ToString
@NoArgsConstructor //Constructor sin argumentos
@AllArgsConstructor//Constructor con todos los argumentos

@Entity
@Table(name="estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter@Setter private Long id;
    @Column(name="nombre",nullable = false,length = 50)
    @Getter@Setter private String nombre;

    @Column(name="apellido",nullable = false,length = 50)
    @Getter@Setter private String apellido;

    @Column(name="email",nullable = false,length = 50,unique=true)
    @Getter@Setter private String email;


}


