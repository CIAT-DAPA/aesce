package com.AESCE.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class sincronizacionMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sincronizacionmenu);
	}

	/*****************************************************
	 ****************** METODOS DE LOS BOTONES*************
	 *****************************************************/
	// --Metodo para el boton usuarios--//
	public void OnSincronizacionMenuBtnUsuarios_Click(View button) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("n1", "bienvenido"));
		InputStream is=null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://pruebaaesce.freetzi.com/index.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Mensaje("Error http",e.getMessage());
		}
		
		//convert response to string
		try{
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line + "\n");
		    }
		    is.close();

		    result=sb.toString();
		}catch(Exception e){
		    Mensaje("Error convercion", e.getMessage());
		}
		
		//parse json data
		try{
			
		    JSONObject jArray = new JSONObject(result);
		    Mensaje("aacca","aca"+jArray.get("n1"));
		    
		  
		    
		
		}catch(JSONException e){
		    Mensaje("error",e.getMessage());
		}
				


	}
	
	// -- METODO PARA IMPRIMIR MENSAJES--//
		public void Mensaje(String titulo, String Mensaje) {

			String squence = "" + Mensaje;
			String title = "" + titulo;
			String PositiveButton = "OK";

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(squence)
					.setTitle(title)
					.setIcon(R.drawable.icon)
					.setCancelable(false)
					.setPositiveButton(PositiveButton,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();

								}
							});

			AlertDialog alert = builder.create();
			alert.show();

		}

		// --Metodo para crear la base de datos--//
		public void CrearBBDD() {
			BaseDatosHelper bdH = new BaseDatosHelper(this);
			try {
				bdH.crearDataBase();
			} catch (IOException ioe) {
				Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
			}
		}

}
