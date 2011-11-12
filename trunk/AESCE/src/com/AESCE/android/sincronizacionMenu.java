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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class sincronizacionMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sincronizacionmenu);
		
		CrearBBDD();
	}

	/*****************************************************
	 ****************** METODOS DE LOS BOTONES*************
	 *****************************************************/
	// --Metodo para el boton usuarios--//
	public void OnSincronizacionMenuBtnUsuarios_Click(View button) {
		/*
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("n1", "bienvenido"));
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://pruebaaesce.freetzi.com/index.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Mensaje("Error http", e.getMessage());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Mensaje("Error convercion", e.getMessage());
		}

		// parse json data
		try {

			JSONObject jArray = new JSONObject(result);
			Mensaje("aacca", "aca" + jArray.get("n1"));

		} catch (JSONException e) {
			Mensaje("error", e.getMessage());
		}
		*/
		sincronizacion("USUARIO");

	}

	/*****************************************************
	 ******************* METODOS DE LA CLASE***************
	 ****************************************************/

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

	// --Metodo para sincronizar--//
	public void sincronizacion(String miTabla) {
		if (miTabla.equals("USUARIO")) {
			String sql = "SELECT USU_ID, USU_PASS, USU_NOMBRE, USU_PERID, USU_EMAIL FROM USUARIO";
			sincronizarTablas(miTabla, sql);
		}
	}

	public void sincronizarTablas(String tabla, String sql) {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// bdH.onCreate(myDataBase);

			Cursor c = myDatabase.rawQuery(sql, null);
			// Iteramos atraves de los registros del cursor
			c.moveToFirst();

			if (tabla.equals("USUARIO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "USUARIOS"));
				
				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("USU_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("USU_PASS", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("USU_NOMBRE", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("USU_PERID", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("USU_EMAIL", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);
					
					c.moveToNext();
				}
				c.close();
			
			}

		} catch (Exception e) {
			Mensaje("Error", "Error: " + e.getMessage());
		} finally {
			bdH.close();
		}

	}

	public void sincronizacionPHP(ArrayList<NameValuePair> nameValuePairs) {
		String result = "";
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://aesce.efuturest.com/index.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Mensaje("Error http", e.getMessage());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Mensaje("Error convercion", e.getMessage());
		}

		// parse json data
		try {

			JSONObject jArray = new JSONObject(result);
			 Mensaje("TABLA", ""+jArray.get("TABLA"));

		} catch (JSONException e) {
			Mensaje("error", e.getMessage());
		}
	}

}
