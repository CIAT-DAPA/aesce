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
import android.util.Log;
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
		 * String result = ""; ArrayList<NameValuePair> nameValuePairs = new
		 * ArrayList<NameValuePair>(); nameValuePairs.add(new
		 * BasicNameValuePair("n1", "bienvenido")); InputStream is = null;
		 * 
		 * try { HttpClient httpclient = new DefaultHttpClient(); HttpPost
		 * httppost = new HttpPost( "http://pruebaaesce.freetzi.com/index.php");
		 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 * HttpResponse response = httpclient.execute(httppost); HttpEntity
		 * entity = response.getEntity(); is = entity.getContent(); } catch
		 * (Exception e) { Mensaje("Error http", e.getMessage()); }
		 * 
		 * // convert response to string try { BufferedReader reader = new
		 * BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
		 * StringBuilder sb = new StringBuilder(); String line = null; while
		 * ((line = reader.readLine()) != null) { sb.append(line + "\n"); }
		 * is.close();
		 * 
		 * result = sb.toString(); } catch (Exception e) {
		 * Mensaje("Error convercion", e.getMessage()); }
		 * 
		 * // parse json data try {
		 * 
		 * JSONObject jArray = new JSONObject(result); Mensaje("aacca", "aca" +
		 * jArray.get("n1"));
		 * 
		 * } catch (JSONException e) { Mensaje("error", e.getMessage()); }
		 */
		sincronizacion("USUARIO");

	}

	//--Boton unidades--//
	public void OnSincronizacionMenuBtnUnidades_Click(View button) {
		sincronizacion("UNIDADES");
	}
	
	//--Boton unidades de manejo--//
	public void OnSincronizacionMenuBtnUmanejo_Click(View button){
		sincronizacion("UMANEJO");
	}
	
	//--Boton tipo calidad--//
	public void OnSincronizacionMenuBtnTipoCalidad_Click(View button){
		sincronizacion("TIPOCALIDAD");
	}
	
	//--Boton TIC--//
	public void OnSincronizacionMenuBtnTic_Click(View button){
		sincronizacion("TIC");
	}
	
	//--Boton Texturas--//
	public void OnSincronizacionMenuBtnTexturas_Click(View button){
		sincronizacion("TEXTURAS");
		sincronizacion("TEXTURA");
	}
	
	//--Boton Terreno--//
	public void OnSincronizacionMenuBtnTerreno_Click(View button){
		sincronizacion("TERRENO");
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
		} else if (miTabla.equals("UNIDADES")) {
			String sql = "SELECT UNI_COD, UNI_TIPO, UNI_DESC FROM UNIDADES";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("UMANEJO")) {
			String sql = "SELECT * FROM UMANEJO";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("TIC")){
			String sql="SELECT *FROM TIC";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("TEXTURAS")){
			String sql="SELECT TEX_ID, TEX_DESC FROM TEXTURAS";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("TEXTURA")){
			String sql="SELECT TEX_UMAID, TEX_RASID, TEX_RASCAT FROM TEXTURA";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("TERRENO")){
			String sql="SELECT TER_ID, TER_DESC FROM TERRENO";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("RESROMPIMIENTO")){
			String sql="SELECT RES_ID, RES_DESC FROM RESROMPIMIENTO";
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

			} else if (tabla.equals("UNIDADES")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "UNIDADES"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("UNI_COD", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("UNI_TIPO", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("UNI_DESC", c
							.getString(2)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}

			else if (tabla.equals("UMANEJO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "UMANEJO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("UMA_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("UMA_LOTENRO", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("UMA_FINID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PRUID", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("UMA_DIA", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("UMA_MES", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("UMA_ANIO", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("UMA_LATITUD", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("UMA_LONGITUD", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("UMA_ALTITUD", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("UMA_NARBOLES", c
							.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("UMA_AREA", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("UMA_TRAZADO", c
							.getString(12)));
					nameValuePairs.add(new BasicNameValuePair("UMA_TASUELO", c
							.getString(13)));
					nameValuePairs.add(new BasicNameValuePair("UMA_UINJERTO", c
							.getString(14)));
					nameValuePairs.add(new BasicNameValuePair("UMA_NPATRON", c
							.getString(15)));
					nameValuePairs.add(new BasicNameValuePair("UMA_VARIEDAD", c
							.getString(16)));
					nameValuePairs.add(new BasicNameValuePair("UMA_EDAD", c
							.getString(17)));
					nameValuePairs.add(new BasicNameValuePair("UMA_RESIEMBRA", c
							.getString(18)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PRODANIO", c
							.getString(19)));
					nameValuePairs.add(new BasicNameValuePair("UMA_UNICOD", c
							.getString(20)));
					nameValuePairs.add(new BasicNameValuePair("UMA_UNITIPO", c
							.getString(21)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PERINT", c
							.getString(22)));
					nameValuePairs.add(new BasicNameValuePair("UMA_INVITRO", c
							.getString(23)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PENDIENTE", c
							.getString(24)));
					nameValuePairs.add(new BasicNameValuePair("UMA_CAPAS", c
							.getString(25)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PROEFEC", c
							.getString(26)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PH", c
							.getString(27)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("TIPOCALIDAD")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TIPOCALIDAD"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TPC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TPC_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("TIC")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TIC"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TIC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PROID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CEL2010", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CELACTUAL", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PLANDATOS", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("TIC_ADQPLANDATOS", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("TIC_INTERNETC", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSO", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("TIC_INTPUBLICO", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSOP", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("TIC_TELECENTROS", c
							.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSOT", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PORTATIL", c
							.getString(12)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CAMARA", c
							.getString(13)));
					nameValuePairs.add(new BasicNameValuePair("TIC_INFOMETEREO", c
							.getString(14)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSO", c
							.getString(15)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("TEXTURAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TEXTURAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TEX_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TEX_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("TEXTURA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TEXTURA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TEX_UMAID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TEX_RASID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("TEX_RASCAT", c
							.getString(2)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("TERRENO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TERRENO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TER_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TER_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("RESROMPIMIENTO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "RESROMPIMIENTO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RES_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RES_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			c.close();

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
			Log.i("pRUEBA",jArray.get("TABLA").toString());
			Mensaje("TABLA", "" + jArray.get("TABLA"));

		} catch (JSONException e) {
			Mensaje("error", e.getMessage());
		}
	}

}
