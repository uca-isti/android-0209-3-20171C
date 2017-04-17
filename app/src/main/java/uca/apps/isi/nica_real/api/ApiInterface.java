package uca.apps.isi.nica_real.api;

import retrofit2.http.Body;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import uca.apps.isi.nica_real.Models.Categoria;


/**
 * Created by isi3 on 17/4/2017.
 */

public interface ApiInterface {
    @GET("categorias")
    Call<List<Categoria>> getCategorias();

    @POST("categorias")
    Call<Categoria> createCategoria(@Body Categoria categoria);

}

