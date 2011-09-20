package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class TIC extends Activity implements RadioGroup.OnCheckedChangeListener {

	EditText EdtCedProd;
	EditText EdtCel2010;
	EditText EdtCelActual;
	RadioGroup RdgPlanDatos;
	RadioGroup RdgAdqPlanDatos;
	RadioGroup RdgInternetC;
	RadioGroup RdgFrecUso;
	RadioGroup RdgIntPublico;
	RadioGroup RdgFrecUsoP;
	RadioGroup RdgTelecentros;
	RadioGroup RdgFrecUsoT;
	RadioGroup RdgPortatil;
	RadioGroup RdgCamara;
	RadioGroup RdgInfoMetereo;

	int TIC_PROID;
	String TIC_CEL2010;
	String TIC_CELACTUAL;
	int TIC_PLANDATOS=1;
	int TIC_ADQPLANDATOS=1;
	int TIC_INTERNETC=1;
	int TIC_FRECUSO=1;
	int TIC_INTPUBLICO=1;
	int TIC_FRECUSOP=1;
	int TIC_TELECENTROS=1;
	int TIC_FRECUSOT=1;
	int TIC_PORTATIL=1;
	int TIC_CAMARA=1;
	int TIC_INFOMETEREO=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tic);

		// -capturar los Bundles-//
		Bundle bundle = getIntent().getExtras();
		TIC_PROID = Integer.parseInt(bundle.getString("PRO_ID"));

		// -Crear los campos-//
		EdtCedProd = (EditText) findViewById(R.id.IdTicEdtCedProd);
		EdtCel2010 = (EditText) findViewById(R.id.IdTicEdtCel2010);
		EdtCelActual = (EditText) findViewById(R.id.IdTicEdtCelActual);
		RdgPlanDatos = (RadioGroup) findViewById(R.id.IdTicRdgPlanDatos);
		RdgAdqPlanDatos = (RadioGroup) findViewById(R.id.IdTicRdgAdqPlanDatos);
		RdgInternetC = (RadioGroup) findViewById(R.id.IdTicRdgInternetC);
		RdgFrecUso = (RadioGroup) findViewById(R.id.IdTicRdgFrecUso);
		RdgIntPublico = (RadioGroup) findViewById(R.id.IdTicRdgIntPublico);
		RdgFrecUsoP = (RadioGroup) findViewById(R.id.IdTicRdgFrecUsoP);
		RdgTelecentros = (RadioGroup) findViewById(R.id.IdTicRdgTelecentros);
		RdgFrecUsoT = (RadioGroup) findViewById(R.id.IdTicRdgFrecUsoT);
		RdgPortatil = (RadioGroup) findViewById(R.id.IdTicRdgPortatil);
		RdgCamara = (RadioGroup) findViewById(R.id.IdTicRdgCamara);
		RdgInfoMetereo = (RadioGroup) findViewById(R.id.IdTicRdgInfoMetereo);

		// - Asignar los checkedListener a cada RadioGroup -//
		RdgPlanDatos.setOnCheckedChangeListener(this);
		RdgAdqPlanDatos.setOnCheckedChangeListener(this);
		RdgInternetC.setOnCheckedChangeListener(this);
		RdgFrecUso.setOnCheckedChangeListener(this);
		RdgIntPublico.setOnCheckedChangeListener(this);
		RdgFrecUsoP.setOnCheckedChangeListener(this);
		RdgTelecentros.setOnCheckedChangeListener(this);
		RdgFrecUsoT.setOnCheckedChangeListener(this);
		RdgPortatil.setOnCheckedChangeListener(this);
		RdgCamara.setOnCheckedChangeListener(this);
		RdgInfoMetereo.setOnCheckedChangeListener(this);

		// -Imprimir la cedula-//
		EdtCedProd.setText("" + TIC_PROID);

	}

	/****************************************************************************
	 ************************** METODO DE LOS BOTONES*****************************
	 ****************************************************************************/
	// -- Metodo para el boton insertar --//
	public void OnTicBtnInsertar_Click(View button) {
		registrarTIC();
	}

	// -- Metodo para el boton regresar --//
	public void OnTicBtnRegresar_Click(View button) {
		finish();
	}

	// -- Metodo para los RadioButtons --//
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if (group == RdgPlanDatos) {
			if (checkedId == R.id.IdTicRdbPlanDatosSI) {
				TIC_PLANDATOS = 1;
			} else if (checkedId == R.id.IdTicRdbPlanDatosNO) {
				TIC_PLANDATOS = 2;
			}
		}

		 if (group == RdgAdqPlanDatos) {
			if (checkedId == R.id.IdTicRdbAdqPlanDatosSI) {
				TIC_ADQPLANDATOS = 1;
			} else if (checkedId == R.id.IdTicRdbAdqPlanDatosNO) {
				TIC_ADQPLANDATOS = 2;
			}
		}

		if (group == RdgInternetC) {
			if (checkedId == R.id.IdTicRdbInternetCSI) {
				TIC_INTERNETC = 1;
			} else if (checkedId == R.id.IdTicRdbInternetCNO) {
				TIC_INTERNETC = 2;
			}
		}
		if (group == RdgFrecUso) {
			if (checkedId == R.id.IdTicRdbFrecUso1vezMes) {
				TIC_FRECUSO = 1;
			} else if (checkedId == R.id.IdTicRdbFrecUsoRegularmente) {
				TIC_FRECUSO = 2;
			} else if (checkedId == R.id.IdTicRdbFrecUsoDiario) {
				TIC_FRECUSO = 3;
			}
		}

		if (group == RdgIntPublico) {
			if (checkedId == R.id.IdTicRdbIntPublicoSI) {
				TIC_INTPUBLICO = 1;
			} else if (checkedId == R.id.IdTicRdbIntPublicoNO) {
				TIC_INTPUBLICO = 2;
			}
		}
		if (group == RdgFrecUsoP) {
			if (checkedId == R.id.IdTicRdbFrecUsoPUso1vezMes) {
				TIC_FRECUSOP = 1;
			} else if (checkedId == R.id.IdTicRdbFrecUsoPRegularmente) {
				TIC_FRECUSOP = 2;
			} else if (checkedId == R.id.IdTicRdbFrecUsoPDiario) {
				TIC_FRECUSOP = 3;
			}
		}

		if (group == RdgTelecentros) {
			if (checkedId == R.id.IdTicRdbTelecentrosSI) {
				TIC_TELECENTROS = 1;
			} else if (checkedId == R.id.IdTicRdbTelecentrosNO) {
				TIC_TELECENTROS = 2;
			}
		}
		if (group == RdgFrecUsoT) {
			if (checkedId == R.id.IdTicRdbFrecUsoT1vezMes) {
				TIC_FRECUSOT = 1;
			} else if (checkedId == R.id.IdTicRdbFrecUsoTRegularmente) {
				TIC_FRECUSOT = 2;
			} else if (checkedId == R.id.IdTicRdbFrecUsoTDiario) {
				TIC_FRECUSOT = 3;
			}
		}

		if (group == RdgPortatil) {
			if (checkedId == R.id.IdTicRdbPortatilSI) {
				TIC_PORTATIL = 1;
			} else if (checkedId == R.id.IdTicRdbPortatilNO) {
				TIC_PORTATIL = 2;
			}
		}

		if (group == RdgCamara) {
			if (checkedId == R.id.IdTicRdbCamaraSI) {
				TIC_CAMARA = 1;
			} else if (checkedId == R.id.IdTicRdbCamaraNO) {
				TIC_CAMARA = 2;
			}
		}

		if (group == RdgInfoMetereo) {
			if (checkedId == R.id.IdTicRdbInfoMetereoSI) {
				TIC_INFOMETEREO = 1;
			} else if (checkedId == R.id.IdTicRdbInfoMetereoNO) {
				TIC_INFOMETEREO = 2;
			}
		}

	}

	/******************************************************************************
	 ******************************* METODOS DE LA CLASE****************************
	 ******************************************************************************/
	// -- Metodo para imprimir mensajes --//
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

	// -- Metodo para crear la base de datos --//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
		}
	}

	// -- Metodo para registrar en el modulo TIC --//
	public void registrarTIC() {
		CrearBBDD();
		/*
		 * INSERT INTO TIC(TIC_PROID, TIC_CEL2010, TIC_CELACTUAL, TIC_PLANDATOS,
		 * TIC_ADQPLANDATOS, TIC_INTERNETC, TIC_FRECUSO, TIC_INTPUBLICO,
		 * TIC_FRECUSOP, TIC_TELECENTROS, TIC_FRECUSOT, TIC_PORTATIL,
		 * TIC_CAMARA, TIC_INFOMETEREO)
		 * VALUES(1,'Smartphone','3127151010',1,1,1,1,1,1,1,1,1,1,1)
		 */
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			TIC_CEL2010 = EdtCel2010.getText().toString();
			TIC_CELACTUAL = EdtCelActual.getText().toString();
			

			ContentValues cv = new ContentValues();
			cv.put("TIC_PROID", TIC_PROID);
			cv.put("TIC_CEL2010", TIC_CEL2010);
			cv.put("TIC_CELACTUAL", TIC_CELACTUAL);
			cv.put("TIC_PLANDATOS", TIC_PLANDATOS);
			cv.put("TIC_ADQPLANDATOS", TIC_ADQPLANDATOS);
			cv.put("TIC_INTERNETC", TIC_INTERNETC);
			cv.put("TIC_FRECUSO", TIC_FRECUSO);
			cv.put("TIC_INTPUBLICO", TIC_INTPUBLICO);
			cv.put("TIC_FRECUSOP", TIC_FRECUSOP);
			cv.put("TIC_TELECENTROS", TIC_TELECENTROS);
			cv.put("TIC_FRECUSOT", TIC_FRECUSOT);
			cv.put("TIC_PORTATIL", TIC_PORTATIL);
			cv.put("TIC_CAMARA", TIC_CAMARA);
			cv.put("TIC_INFOMETEREO", TIC_INFOMETEREO);

			 myDatabase.insert("TIC", null, cv);

			//Mensaje("Funciona", "3 " + TIC_PLANDATOS + " 4 " + TIC_ADQPLANDATOS);
			myDatabase.close();

			limpiar();

			finish();

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		}

	}

	// -- Mensaje para limpiar los campos --//
	public void limpiar() {
		EdtCedProd.setText("");
		EdtCel2010.setText("");
		EdtCelActual.setText("");
	}

}
