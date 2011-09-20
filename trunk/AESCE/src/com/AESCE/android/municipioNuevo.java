package com.AESCE.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class municipioNuevo extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.municipionuevo);
	}
	
	/********************************************************
	 ******************* METODO DE LOS BOTONES****************
	 *********************************************************/
	// --Metodo para el boton regresar--//
	public void OnMunicipioNuevoBtnRegresar_Click(View button) {
		finish();
	}

}
