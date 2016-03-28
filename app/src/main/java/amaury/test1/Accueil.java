package amaury.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Accueil extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void onClickClose(View view){
        Toast.makeText(this,String.format("Akeukoukou"), Toast.LENGTH_LONG).show();
    }

    public void onClickOptions(View view){
        Intent da = new Intent();
        da.setClass(this, VueOptions.class);
        startActivity(da);
    }

    public void onClickCreer(View view){
        Intent da = new Intent();
        da.setClass(this, CreerSoiree.class);
        startActivity(da);
    }

    public void onClickLogin(View view){
        Intent da = new Intent();
        da.setClass(this, LoginActivity.class);
        startActivity(da);
    }

    public void onClickRecherche(View view){
        Intent da = new Intent();
        da.setClass(this, RechercherSoiree.class);
        startActivity(da);
    }

    public void onClickAdmin(View view){
        Intent da = new Intent();
        da.setClass(this, AdminActivity.class);
        startActivity(da);
    }
}
