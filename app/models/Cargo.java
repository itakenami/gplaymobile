package models;

import java.util.*;
import java.math.*;
import javax.persistence.*;
import play.db.jpa.Model;

@Entity(name="cargos")
public class Cargo extends Model{


    public String nome;


    @Override
    public String toString(){
        return nome;
    }
}
