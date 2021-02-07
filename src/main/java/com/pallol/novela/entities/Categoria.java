package com.pallol.novela.entities;
// Generated 25/07/2019 01:09:25 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Categoria generated by hbm2java
 */
@Entity
@Table(name="categoria"
    ,catalog="noveladb"
)
public class Categoria  implements java.io.Serializable {


     private Integer categoriaId;
     private String nombre;
     private Set<Novela> novelas = new HashSet<Novela>(0);

    public Categoria() {
    }

	
    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    public Categoria(String nombre, Set<Novela> novelas) {
       this.nombre = nombre;
       this.novelas = novelas;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="categoria_id", unique=true, nullable=false)
    public Integer getCategoriaId() {
        return this.categoriaId;
    }
    
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    
    @Column(name="nombre", nullable=false, length=65535)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="categoria")
    public Set<Novela> getNovelas() {
        return this.novelas;
    }
    
    public void setNovelas(Set<Novela> novelas) {
        this.novelas = novelas;
    }




}


