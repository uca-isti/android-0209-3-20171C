package uca.apps.isi.nica_real;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import uca.apps.isi.nica_real.Models.Categoria;
import uca.apps.isi.nica_real.api.Api;
import uca.apps.isi.nica_real.api.ApiInterface;
import uca.apps.isi.nica_real.fragment.Fragment_acercaDe;
import uca.apps.isi.nica_real.fragment.Fragment_deducciones;
import uca.apps.isi.nica_real.fragment.Fragment_feedback;
import uca.apps.isi.nica_real.fragment.Fragment_home;
import uca.apps.isi.nica_real.fragment.LocationFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText nombre;
    private EditText id;
    private Button guardar;
    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getBase())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

//        Categoria categoria = new Categoria();
//        categoria.setName("Ingresos");

//        Call<Categoria> tweetCall = apiInterface.createCategoria(categoria);
//        tweetCall.enqueue(new Callback<Categoria>() {
//            @Override
//            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
//
//                Log.i(TAG, response.body().getName());
//            }
//
//            @Override
//            public void onFailure(Call<Categoria> call, Throwable t) {
//
//            }
//        });

        //Log.i(TAG,  apiInterface.getTweets().request().url().toString());

        Call<List<Categoria>> call = apiInterface.getCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {


                if(response != null) {

                    for(Categoria categoria : response.body()) {

                        Log.i(TAG,"" +categoria.getName());
                    }
                } else {
                    Log.i(TAG, "Response es nulo");
                }

            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }
    private void initViews() {
        nombre = (EditText) findViewById(R.id.nombreCategoria);
        id = (EditText) findViewById(R.id.idCategoria);
        guardar = (Button) findViewById(R.id.btnGuardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });
    }
    private boolean validate() {
        boolean success = false;
        if(nombre.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_LONG).show();
        } else if(id.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar un id", Toast.LENGTH_LONG).show();
        } else {
            success = true;
        }

        return success;
    }

    private void save() {
        if (validate()) {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            Categoria categoria = realm.createObject(Categoria.class);
            categoria.setName(nombre.getText().toString());
            categoria.setId_Categoria(Integer.parseInt(String.valueOf(id.getText())));
            realm.commitTransaction();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass= null;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            fragmentClass = Fragment_deducciones.class;

        } else
        if (id == R.id.nav_gallery) {
            fragmentClass = Fragment_feedback.class;

        } else if (id == R.id.nav_slideshow) {
            fragmentClass = Fragment_acercaDe.class;

        } else if (id == R.id.nav_inicio) {
            fragmentClass = Fragment_home.class;

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
