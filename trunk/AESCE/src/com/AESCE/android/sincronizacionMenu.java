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
		sincronizacion("PERFIL");

	}
	
	//--Boton productor--//
	public void OnSincronizacionMenuBtnProductor_Click(View button){
		sincronizacion("PRODUCTOR");
		sincronizacion("UNIDADES");
	}

	// --Boton unidades--//
	public void OnSincronizacionMenuBtnFinca_Click(View button) {
		sincronizacion("FINCA");
		sincronizacion("UNIDADES");
	}
	
	//--Boton TIC--//
	public void OnSincronizacionMenuBtnTic_Click(View button){
		sincronizacion("TIC");
	}

	// --Boton unidades de manejo--//
	public void OnSincronizacionMenuBtnUmanejo_Click(View button) {
		sincronizacion("UMANEJO");
		sincronizacion("CULASOCIADO");
		sincronizacion("LOTEDEFINIDO");
		sincronizacion("DISPERSOS");
	}
	
	//--Boton contactos--//
	public void OnSincronizacionMenuBtnContactos_Click(View button){
		sincronizacion("CONTACTOS");
	}

	//--Boton Historico produccion--//
	public void OnSincronizacionMenuBtnHistProduccion_Click(View button){
		sincronizacion("PRODUCCION");
		sincronizacion("COSECHA");
		sincronizacion("PREGUNTAS");
	}
	//--Boton Calidad--//
	public void OnSincronizacionMenuBtnCalidad_Click(View button){
		sincronizacion("CALIDAD");
		sincronizacion("TIPOCALIDAD");
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
		} else if (miTabla.equals("TIC")) {
			String sql = "SELECT *FROM TIC";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("TEXTURAS")) {
			String sql = "SELECT TEX_ID, TEX_DESC FROM TEXTURAS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("TEXTURA")) {
			String sql = "SELECT TEX_UMAID, TEX_RASID, TEX_RASCAT FROM TEXTURA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("TERRENO")) {
			String sql = "SELECT TER_ID, TER_DESC FROM TERRENO";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("RESROMPIMIENTO")) {
			String sql = "SELECT RES_ID, RES_DESC FROM RESROMPIMIENTO";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("RASTA")) {
			String sql = "SELECT *FROM RASTA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("RASPREGUNTAS")) {
			String sql = "SELECT *FROM RASPREGUNTAS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("RASMATORGANICA")) {
			String sql = "SELECT *FROM RASMATORGANICA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PRODUCTOS")) {
			String sql = "SELECT PRU_COD, PRU_DESC FROM PRODUCTOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PRODUCTOR")) {
			String sql = "SELECT *FROM PRODUCTOR";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PRODUCCION")) {
			String sql = "SELECT PRC_ID, PRC_LOTE, PRC_UMAID, PRC_ANIO, PRC_PRODUCCION FROM PRODUCCION";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PROCEDIMIENTOSPROF")) {
			String sql = "SELECT PRF_RASID, PRF_RASUMAID, PRF_ID, PRF_IDPREG, PRF_PROF FROM PROCEDIMIENTOSPROF";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PROCEDIMIENTOS")) {
			String sql = "SELECT PRC_RASID, PRC_RASUMAID, PRC_ID, PRC_IDPREG, PRC_RES FROM PROCEDIMIENTOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PREGUNTAS")) {
			String sql = "SELECT PRE_ID, PRE_DESC FROM PREGUNTAS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("POSPERFIL")) {
			String sql = "SELECT POS_ID, POS_DESC FROM POSPERFIL";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PERFIL")) {
			String sql = "SELECT *FROM PERFIL";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("PEDREGOSIDAD")) {
			String sql = "SELECT PED_ID, PED_DESC FROM PEDREGOSIDAD";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("MUNICIPIOS")) {
			String sql = "SELECT MUN_ID, MUN_DEPID, MUN_DESC FROM MUNICIPIOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("LOTEDEFINIDO")) {
			String sql = "SELECT LTD_UMAID, LTD_DISTSIEMBRA, LTD_CUAREG, LTD_TRESBOLILLO FROM LOTEDEFINIDO";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("FINCA")) {
			String sql = "SELECT *FROM FINCA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("ESTRUCTURA")) {
			String sql = "SELECT EST_ID, EST_DESC FROM ESTRUCTURA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("DISPERSOS")) {
			String sql = "SELECT *FROM DISPERSOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("DEPARTAMENTOS")) {
			String sql = "SELECT DEP_ID, DEP_DESC FROM DEPARTAMENTOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CULASOCIADO")) {
			String sql = "SELECT CUA_FINID, CUA_UMAID, CUA_PRUCOD, CUA_CULDESC FROM CULASOCIADO";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("COSECHA")) {
			String sql = "SELECT *FROM COSECHA";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CONTACTOS")) {
			String sql = "SELECT *FROM CONTACTOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CARBONATOS")) {
			String sql = "SELECT CAR_ID, CAR_DESC FROM CARBONATOS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CAPASENDURECIDAS")) {
			String sql = "SELECT *FROM CAPASENDURECIDAS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CAPAS")) {
			String sql = "SELECT *FROM CAPAS";
			sincronizarTablas(miTabla, sql);
		} else if (miTabla.equals("CALIDAD")) {
			String sql = "SELECT *FROM CALIDAD";
			sincronizarTablas(miTabla, sql);
		}else if(miTabla.equals("TIPOCALIDAD")){
			String sql="SELECT *FROM TIPOCALIDAD";
			sincronizarTablas(miTabla,sql);
		} 
		
		else if (miTabla.equals("ANAQUIMICO")) {
			String sql = "SELECT *FROM ANAQUIMICO";
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
					nameValuePairs.add(new BasicNameValuePair("UMA_RESIEMBRA",
							c.getString(18)));
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
					nameValuePairs.add(new BasicNameValuePair("UMA_PENDIENTE",
							c.getString(24)));
					nameValuePairs.add(new BasicNameValuePair("UMA_CAPAS", c
							.getString(25)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PROEFEC", c
							.getString(26)));
					nameValuePairs.add(new BasicNameValuePair("UMA_PH", c
							.getString(27)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("TIPOCALIDAD")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"TIPOCALIDAD"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TPC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TPC_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("TIC")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TIC"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TIC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PROID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CEL2010", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CELACTUAL",
							c.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PLANDATOS",
							c.getString(4)));
					nameValuePairs.add(new BasicNameValuePair(
							"TIC_ADQPLANDATOS", c.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("TIC_INTERNETC",
							c.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSO", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("TIC_INTPUBLICO",
							c.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSOP", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair(
							"TIC_TELECENTROS", c.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("TIC_FRECUSOT", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("TIC_PORTATIL", c
							.getString(12)));
					nameValuePairs.add(new BasicNameValuePair("TIC_CAMARA", c
							.getString(13)));
					nameValuePairs.add(new BasicNameValuePair(
							"TIC_INFOMETEREO", c.getString(14)));
					
					

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("TEXTURAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TEXTURAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TEX_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TEX_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("TEXTURA")) {
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
			} else if (tabla.equals("TERRENO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "TERRENO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("TER_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("TER_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("RESROMPIMIENTO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"RESROMPIMIENTO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RES_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RES_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("RASTA")) {
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
					nameValuePairs.add(new BasicNameValuePair("RAS_PENDIENTE",
							c.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PH", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("RAS_CARID", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_PROFCARBONATOS", c.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PEDSUPID", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("RAS_PEDPERID", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("RAS_HORPEDROC",
							c.getString(10)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_HORPEDROCPROF", c.getString(11)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_HORPEDROCESP", c.getString(12)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_PROFPRIROCPIED", c.getString(13)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_MOTEADOSSINO", c.getString(14)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_MOTEADOSPROF", c.getString(15)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_MOTEADOS70CM", c.getString(16)));
					nameValuePairs.add(new BasicNameValuePair("RAS_ESTID", c
							.getString(17)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_ANOTACIONESPECIALES", c.getString(18)));
					nameValuePairs.add(new BasicNameValuePair("RAS_CAJUELANRO",
							c.getString(19)));
					nameValuePairs.add(new BasicNameValuePair("RAS_USUID", c
							.getString(20)));
					nameValuePairs.add(new BasicNameValuePair("RAS_USOACTLOTE",
							c.getString(21)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_PROEFECTIVA", c.getString(22)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_SLOORGANICO", c.getString(23)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_DRENINTERNO", c.getString(24)));
					nameValuePairs.add(new BasicNameValuePair(
							"RAS_DRENEXTERNO", c.getString(25)));
					nameValuePairs.add(new BasicNameValuePair("RAS_SALINIDAD",
							c.getString(26)));
					nameValuePairs.add(new BasicNameValuePair("RAS_SODICIDAD",
							c.getString(27)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("RASPREGUNTAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"RASPREGUNTAS"));

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
			} else if (tabla.equals("RASMATORGANICA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"RASMATORGANICA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("RMO_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("RMO_RASID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("RMO_RASUMAID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair(
							"RMO_MATORGANICA", c.getString(3)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PRODUCTOS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "PRODUCTOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRU_COD", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRU_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PRODUCTOR")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "PRODUCTOR"));

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
					nameValuePairs.add(new BasicNameValuePair("PRO_APELLIDO1",
							c.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("PRO_APELLIDO2",
							c.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("PRO_CELULAR", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("PRO_FIJO", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("PRO_EMAIL", c
							.getString(9)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PRODUCCION")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "PRODUCCION"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRC_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRC_LOTE", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("PRC_UMAID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("PRC_ANIO", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("PRC_PRODUCCION",
							c.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PROCEDIMIENTOSPROF")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"PROCEDIMIENTOSPROF"));

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
			} else if (tabla.equals("PROCEDIMIENTOS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"PROCEDIMIENTOS"));

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
			} else if (tabla.equals("PREGUNTAS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "PREGUNTAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PRE_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PRE_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("POSPERFIL")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "POSPERFIL"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("POS_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("POS_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PERFIL")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "PERFIL"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PER_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PER_DESC", c
							.getString(1)));
					
					Mensaje(c.getString(0),c.getString(1));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("PEDREGOSIDAD")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"PEDREGOSIDAD"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("PED_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("PED_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("MUNICIPIOS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "MUNICIPIOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("MUN_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("MUN_DEPID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("MUN_DESC", c
							.getString(2)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("LOTEDEFINIDO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"LOTEDEFINIDO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("LTD_UMAID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair(
							"LTD_DISTSIEMBRA", c.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("LTD_CUAREG", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair(
							"LTD_TRESBOLILLO", c.getString(3)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("FINCA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "FINCA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("FIN_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("FIN_NOMBRE", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("FIN_PROID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair(
							"FIN_GEOREFERENCIA", c.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("FIN_ASO", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("FIN_NOMASO", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("FIN_AREA", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("FIN_UNICOD", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("FIN_UNITIPO", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("FIN_REGPROD", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("FIN_REGMANEJO",
							c.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("FIN_REGCLIMA", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("FIN_DEPID", c
							.getString(12)));
					nameValuePairs.add(new BasicNameValuePair("FIN_MUNID", c
							.getString(13)));
					nameValuePairs.add(new BasicNameValuePair(
							"FIN_CORREGIMIENTO", c.getString(14)));
					nameValuePairs.add(new BasicNameValuePair("FIN_VEREDA", c
							.getString(15)));
					nameValuePairs.add(new BasicNameValuePair("FIN_USUID", c
							.getString(16)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("ESTRUCTURA")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "ESTRUCTURA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("EST_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("EST_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("DISPERSOS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "DISPERSOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("DIS_UMAID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("DIS_CERCA", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("DIS_VIA", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("DIS_RIO", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("DIS_VIVIENDA", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("DIS_POTRERO", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("DIS_OTRA", c
							.getString(6)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}
			} else if (tabla.equals("DEPARTAMENTOS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"DEPARTAMENTOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("DEP_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("DEP_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CULASOCIADO")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"CULASOCIADO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("CUA_FINID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("CUA_UMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("CUA_PRUCOD", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("CUA_CULDESC", c
							.getString(3)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("COSECHA")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "COSECHA"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("COS_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("COS_UMAID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("COS_ANIO", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("COS_MES", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("COS_PREGID", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("COS_RESID", c
							.getString(5)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CONTACTOS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "CONTACTOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("CON_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("CON_FINID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("CON_DIA", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("CON_MES", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("CON_ANIO", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("CON_REPAESCE", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair(
							"CON_TELREPAESCE", c.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("CON_REPASO", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("CON_TELREPASO",
							c.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("CON_REPFUN", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("CON_TELREPFUN",
							c.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("CON_LIDER", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("CON_TELLIDER", c
							.getString(12)));
					
					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CARBONATOS")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "CARBONATOS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("CAR_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("CAR_DESC", c
							.getString(1)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CAPASENDURECIDAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA",
						"CAPASENDURECIDAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("CAP_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("CAP_RASID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("CAP_RASUMAID", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("CAP_ESPESOR", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("CAP_COLORSECO",
							c.getString(4)));
					nameValuePairs.add(new BasicNameValuePair(
							"CAP_COLORHUMEDO", c.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("CAP_TEXID", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("CAP_RESID", c
							.getString(7)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CAPAS")) {
				nameValuePairs.add(new BasicNameValuePair("TABLA", "CAPAS"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("CAP_ID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("CAP_RASID", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("CAP_SINO", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("CAP_PROF", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("CAP_ESP", c
							.getString(4)));

					sincronizacionPHP(nameValuePairs);

					c.moveToNext();
				}

			} else if (tabla.equals("CALIDAD")) {
				nameValuePairs
				.add(new BasicNameValuePair("TABLA", "CALIDAD"));

		while (c.isAfterLast() == false) {
			nameValuePairs.add(new BasicNameValuePair("CAL_ID", c
					.getString(0)));
			nameValuePairs.add(new BasicNameValuePair("CAL_UMAID", c
					.getString(1)));
			nameValuePairs.add(new BasicNameValuePair("CAL_TPCID", c
					.getString(2)));
			nameValuePairs.add(new BasicNameValuePair("CAL_LOTE", c
					.getString(3)));
			nameValuePairs.add(new BasicNameValuePair("CAL_ANIO", c
					.getString(4)));
			nameValuePairs.add(new BasicNameValuePair("CAL_PORCENTAJE", c
					.getString(5)));
			
			sincronizacionPHP(nameValuePairs);

			c.moveToNext();
		}

	} else if (tabla.equals("ANAQUIMICO")) {
				nameValuePairs
						.add(new BasicNameValuePair("TABLA", "ANAQUIMICO"));

				while (c.isAfterLast() == false) {
					nameValuePairs.add(new BasicNameValuePair("ANQ_UMAID", c
							.getString(0)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_MO", c
							.getString(1)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_N", c
							.getString(2)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_P", c
							.getString(3)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_K", c
							.getString(4)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_CA", c
							.getString(5)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_MG", c
							.getString(6)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_CIC", c
							.getString(7)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_MN", c
							.getString(8)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_FE", c
							.getString(9)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_ZN", c
							.getString(10)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_CU", c
							.getString(11)));
					nameValuePairs.add(new BasicNameValuePair("ANQ_B", c
							.getString(12)));

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
			Log.i("pRUEBA", jArray.get("TABLA").toString());
			Mensaje("TABLA", "" + jArray.get("TABLA"));

		} catch (JSONException e) {
			Mensaje("error", e.getMessage());
		}
	}

}
