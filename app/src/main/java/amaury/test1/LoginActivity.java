package amaury.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickSeConnecter(View view){
        EditText champLogin = (EditText) findViewById(R.id.loginTextfield);
        String login = champLogin.getText().toString();
        EditText champPassword = (EditText) findViewById(R.id.passwordTextfield);
        String password = champPassword.getText().toString();

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        int tempid=bdd.utilisateurExiste(login,password);
        if(tempid!=-1){
            String stemp = "User ID : "+tempid;
            Toast.makeText(LoginActivity.this, stemp, Toast.LENGTH_SHORT).show();
            MainApplicationVariables.setUserID(tempid);
            Intent da = new Intent();
            da.setClass(this, Accueil.class);
            startActivity(da);
        }
        else{
            Toast.makeText(LoginActivity.this, "Utilisateur inconnu", Toast.LENGTH_SHORT).show();
        }
        bdd.close();
    }

    public void onClickCreerCompte(View view){
        EditText champLogin = (EditText) findViewById(R.id.loginTextfield);
        String login = champLogin.getText().toString();
        EditText champPassword = (EditText) findViewById(R.id.passwordTextfield);
        String password = champPassword.getText().toString();
        EditText champEmail = (EditText) findViewById(R.id.mailTextfield);
        String email = champEmail.getText().toString();
        EditText champNom = (EditText) findViewById(R.id.nomTextfield);
        String nom = champNom.getText().toString();
        EditText champPrenom = (EditText) findViewById(R.id.prenomTextfield);
        String prenom = champPrenom.getText().toString();

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        Utilisateur utilisateur = new Utilisateur(0,login,password,email,nom,prenom);
        bdd.addUtilisateur(utilisateur);
        bdd.close();
    }


}
