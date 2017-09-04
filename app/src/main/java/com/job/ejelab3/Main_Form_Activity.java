package com.job.ejelab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static com.job.ejelab3.R.id.action_menu_presenter;
import static com.job.ejelab3.R.id.chb_1;
import static com.job.ejelab3.R.id.spi_1;

public class Main_Form_Activity extends AppCompatActivity {

    private Spinner nom_pizza;
    private Button pedido;
    private CheckBox ch1, ch2;
    private RadioGroup masas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        nom_pizza = (Spinner)findViewById(R.id.spi_1);
        pedido = (Button)findViewById(R.id.bt_ordenar);
        ch1 = (CheckBox)findViewById(R.id.chb_1);
        ch2 = (CheckBox)findViewById(R.id.chb_2);
        masas = (RadioGroup)findViewById(R.id.rg_1);



        String[] pizzas = {" ", "Americana", "Meet Lover", "Suprema", "Súper Suprema"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pizzas);
        nom_pizza.setAdapter(adapter);

        nom_pizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String pizzas = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(),
                        "Escogio la pizza " + pizzas,
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public void call_pizza(View view) {

        String tmasa = ((RadioButton)findViewById(masas.getCheckedRadioButtonId())).getText().toString().toLowerCase();

        String tipo = (String)nom_pizza.getSelectedItem();
        int costo = 0;
        if(tipo == "Americana"){
            costo = 40;
        }else if(tipo == "Meet Lover"){
            costo = 60;
        }else if(tipo == "Suprema"){
            costo = 45;
        }else if(tipo == "Súper Suprema"){
            costo = 65;
        }

        int comple = 0;
        if(ch1.isChecked() && ch2.isChecked()){
            comple = 20;
        }else if(ch2.isChecked()){
            comple = 12;
        }else if(ch1.isChecked()){
            comple = 8;
        }

        int total = 0;
        total = costo + comple;

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Confirmación de pedido");
        alertDialog.setMessage("Su pedido de "+tipo+" con "+tmasa+" a S/."+total+".00 +IGV está en proceso de envío.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ACEPTAR", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int a){
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();


                Intent notifi = new Intent(this, MainActivity.class);
                notifi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 10000, notifi, PendingIntent.FLAG_ONE_SHOT);

                final Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("Confirmación de Pizza")
                        .setContentText("Su pizza esta en camino")
                        .setSmallIcon(R.drawable.ic_pizza)
                        .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();


             final   NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.notify(10000, notification);
            }
        }, 10000);


    }
}
