package models;

import java.util.*;
import java.math.*;
import javax.persistence.*;
import play.db.jpa.Model;

@Entity(name="requisitos")
public class Requisito extends Model{


    public String descricao;

    public String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="projeto_id")
    public Projeto projeto;


    @Override
    public String toString(){
        return nome;
    }
}
