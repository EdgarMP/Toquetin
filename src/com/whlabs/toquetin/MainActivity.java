package com.whlabs.toquetin;

import com.google.android.gms.ads.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	int contador;
	int defaultValue = 0;
	int highScore;
	int lastSaved;
	String myHighScore = "com.whlabs.toquetin.myHigScore";
	String myLastSaved = "com.whlabs.toquetin.lastSaved";
	TextView displayContador;
	SharedPreferences sharedPref;
	ImageButton tocame;
	boolean flag = true;
	
	/* Ads */
	private AdView adView;
	private AdView adViewBottom;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* Ads */
	    // Crear adView.
	    adView = new AdView(this);
	    adView.setAdUnitId("ca-app-pub-1164935914024662/4313268633");
	    adView.setAdSize(AdSize.BANNER);

	    // Buscar LinearLayout suponiendo que se le ha asignado
	    // el atributo android:id="@+id/mainLayout".
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adView);

	    // Añadirle adView.
	    layout.addView(adView);

	    // Iniciar una solicitud genérica.
	    //AdRequest adRequest = new AdRequest.Builder().build();    
	    
	    //Testing Mode
	    AdRequest adRequest = new AdRequest.Builder().addTestDevice("15C710F4A0DB145C84EBA633E5D4FD75")
	    .build();

	    // Cargar adView con la solicitud de anuncio.
	    adView.loadAd(adRequest);
	    
	    
	    // Crear adView.
	    adViewBottom = new AdView(this);
	    adViewBottom.setAdUnitId("ca-app-pub-1164935914024662/4099615830");
	    adViewBottom.setAdSize(AdSize.BANNER);

	    // Buscar LinearLayout suponiendo que se le ha asignado
	    // el atributo android:id="@+id/mainLayout".
	    LinearLayout layoutBottom = (LinearLayout)findViewById(R.id.adView2);

	    // Añadirle adView.
	    layoutBottom.addView(adViewBottom);

	    // Iniciar una solicitud genérica.
	    //AdRequest adRequest = new AdRequest.Builder().build();    
	    
	    //Testing Mode
	    AdRequest adRequestBottom = new AdRequest.Builder().addTestDevice("15C710F4A0DB145C84EBA633E5D4FD75")
	    .build();

	    // Cargar adView con la solicitud de anuncio.
	    adViewBottom.loadAd(adRequestBottom);
	    
		displayContador = (TextView)findViewById(R.id.contador);
		tocame = (ImageButton)findViewById(R.id.tocame);
		
		Context context = this;
		sharedPref = context.getSharedPreferences("com.whlabs.toquetin", Context.MODE_PRIVATE);
		
		
		highScore = sharedPref.getInt(myHighScore, defaultValue);
		lastSaved = sharedPref.getInt(myLastSaved, defaultValue);
		
		System.out.println("My High Score: "+highScore);
		System.out.println("My Last Saved: "+lastSaved);
		
		displayContador.setText(""+highScore);
		 
		//Validar la conexion a internet.
		
		//Validar SharedPreferences vs HighScore en el Backend.
		
	}
	
	public void tocame(View v){
		
		displayContador.setText(""+highScore++);
		
		ImageButton tocame = (ImageButton)v;
		if(flag){
			tocame.setImageResource(R.drawable.boton_normal);
			flag = false;
		}else{
			tocame.setImageResource(R.drawable.boton_aplanado);
			flag = true;
		}
		
		
		
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(myHighScore, highScore);
		editor.commit();
	}
	
	@Override
	public void onPause(){
		 super.onPause();
		 
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(myLastSaved, highScore);
	
		editor.commit();
		
		int contadorToSave = highScore - lastSaved;
		 SaveResult get = new SaveResult();
		 get.execute("http://23.239.30.54:8080/toquetin/guardaToque.do?contador="+contadorToSave);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
