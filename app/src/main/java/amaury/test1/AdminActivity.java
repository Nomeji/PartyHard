package amaury.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void onClickSupprBDD(View view){
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        bdd.deleteBDD();
        bdd.close();
    }

    public void onClickCreerBDD(View view){
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        bdd.createBDD();
        bdd.close();
    }
}
