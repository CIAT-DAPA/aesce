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
		else if(miTabla.equals("RASTA")){
			String sql="SELECT *FROM RASTA";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("RASPREGUNTAS")){
			String sql="SELECT *FROM RASPREGUNTAS";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("RASMATORGANICA")){
			String sql="SELECT *FROM RASMATORGANICA";
			sincronizarTablas(miTabla, sql);
		}
		else if(miTabla.equals("PRODUCTOS")){
			String sql="SELECT PRU_COD, PRU_DESC FROM PRODUCTOS";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PRODUCTOR")){
			String sql="SELECT *FROM PRODUCTOR";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PRODUCCION")){
			String sql="SELECT PRC_ID, PRC_LOTE, PRC_UMAID, PRC_ANIO, PRC_PRODUCCION FRO PRODUCCION";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PROCEDIMIENTOSPROF")){
			String sql="SELECT PRF_RASID, PRF_RASUMAID, PRF_ID, PRF_IDPREG, PRF_PROF FROM PROCEDIMIENTOSPROF";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PROCEDIMIENTOS")){
			String sql="SELECT PRC_RASID, PRC_RASUMAID, PRC_ID, PRC_IDPREG, PRC_RES FROM PROCEDIMIENTOS";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PREGUNTAS")){
			String sql="SELECT PRE_ID, PRE_DESC FROM PREGUNTAS";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("POSPERFIL")){
			String sql="SELECT POS_ID, POS_DESC FROM POSPERFIL";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PERFIL")){
			String sql="SELECT *FROM PERFIL";
			sincronizarTablas(miTabla,sql);
		}
		else if(miTabla.equals("PEDREGOSIDAD")){
			String sql="SELECT PED_ID, PED_DESC FROM PEDREGOSIDAD";
			sincronizarTablas(miTabla,sql);
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
			else if (tabla.equals("RASTA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "RASTA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RAS_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RAS_UMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("RAS_TERID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("RAS_POSID", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PENDIENTE", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PH", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("RAS_CARID", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PROFCARBONATOS", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PEDSUPID", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PEDPERID", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("RAS_HORPEDROC", c
							.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("RAS_HORPEDROCPROF", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("RAS_HORPEDROCESP", c
							.getString(12)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PROFPRIROCPIED", c
							.getString(13)));
					nameValuePairs.add(new BasicNameValuePair("RAS_MOTEADOSSINO", c
							.getString(14)));
					nameValuePairs.add(new BasicNameValuePair("RAS_MOTEADOSPROF", c
							.getString(15)));
					nameValuePairs.add(new BasicNameValuePair("RAS_MOTEADOS70CM", c
							.getString(16)));
					nameValuePairs.add(new BasicNameValuePair("RAS_ESTID", c
							.getString(17)));
					nameValuePairs.add(new BasicNameValuePair("RAS_ANOTACIONESPECIALES", c
							.getString(18)));
					nameValuePairs.add(new BasicNameValuePair("RAS_CAJUELANRO", c
							.getString(19)));
					nameValuePairs.add(new BasicNameValuePair("RAS_USUID", c
							.getString(20)));
					nameValuePairs.add(new BasicNameValuePair("RAS_USOACTLOTE", c
							.getString(21)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PROEFECTIVA", c
							.getString(22)));
					nameValuePairs.add(new BasicNameValuePair("RAS_SLOORGANICO", c
							.getString(23)));
					nameValuePairs.add(new BasicNameValuePair("RAS_DRENINTERNO", c
							.getString(24)));
					nameValuePairs.add(new BasicNameValuePair("RAS_DRENEXTERNO", c
							.getString(25)));
					nameValuePairs.add(new BasicNameValuePair("RAS_SALINIDAD", c
							.getString(26)));
					nameValuePairs.add(new BasicNameValuePair("RAS_SODICIDAD", c
							.getString(27)));
					
					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("RASPREGUNTAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "RASPREGUNTAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RSP_RASID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RSP_RASUMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("RSP_PREID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("RSP_PRERES", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("RSP_PROF", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("RASMATORGANICA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "RASMATORGANICA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RMO_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RMO_RASID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("RMO_RASUMAID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("RMO_MATORGANICA", c
							.getString(3)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PRODUCTOS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PRODUCTOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRU_COD", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRU_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PRODUCTOR")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PRODUCTOR"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRO_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRO_DIA", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("PRO_MES", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("PRO_ANIO", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("PRO_NOMBRE", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("PRO_APELLIDO1", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("PRO_APELLIDO2", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("PRO_CELULAR", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("PRO_FIJO", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("PRO_EMAIL", c
							.getString(9)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PRODUCCION")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PRODUCCION"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRC_LOTE", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("PRC_UMAID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("PRC_ANIO", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("PRC_PRODUCCION", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PROCEDIMIENTOSPROF")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PROCEDIMIENTOSPROF"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRF_RASID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRF_RASUMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("PRF_ID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("PRF_IDPREG", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("PRF_PROF", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PROCEDIMIENTOS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PROCEDIMIENTOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRC_RASID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRC_RASUMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("PRC_ID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("PRC_IDPREG", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("PRC_RES", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PREGUNTAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PREGUNTAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRE_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRE_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("POSPERFIL")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "POSPERFIL"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("POS_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("POS_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PERFIL")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PERFIL"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PER_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PER_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			}
			else if (tabla.equals("PEDREGOSIDAD")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PEDREGOSIDAD"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PED_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PED_DESC", c
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
