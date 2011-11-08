package models;

import java.util.*;
import java.math.*;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name="analistas")
public class Analista extends Model{


    public String especialidade;

    @Required(message="O campo Nome é obrigatório")
    public String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cargo_id")
    public Cargo cargo;


    @Override
    public String toString(){
        return nome;
    }
}
