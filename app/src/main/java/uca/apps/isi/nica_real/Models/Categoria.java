package uca.apps.isi.nica_real.Models;

import io.realm.RealmObject;

/**
 * Created by isi3 on 17/4/2017.
 */

public class Categoria extends RealmObject {

    private String name;
    private int id_Categoria;

    public int getId_Categoria() {
        return id_Categoria;
    }

    public void setId_Categoria(int id) {
        this.id_Categoria = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombreCategoria2) {
        this.name = nombreCategoria2;
    }

}
