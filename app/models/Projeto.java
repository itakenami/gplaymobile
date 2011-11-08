package models;

import java.util.*;
import java.math.*;
import javax.persistence.*;
import play.db.jpa.Model;

@Entity(name="projetos")
public class Projeto extends Model{


    public Date data_fim;

    public Date data_inicio;

    public String descricao;

    public String nome;

    @ManyToMany()
    @JoinTable(name="analistas_projetos", joinColumns={@JoinColumn(name="projeto_id")}, inverseJoinColumns={@JoinColumn(name="analista_id")})
    public Set<Analista> analistas;


    @Override
    public String toString(){
        return nome;
    }
}
