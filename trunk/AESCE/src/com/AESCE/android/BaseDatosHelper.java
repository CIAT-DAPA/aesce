package com.AESCE.android;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosHelper extends SQLiteOpenHelper {

	//La carpeta por defecto donde Android espera encontrar la Base de Datos de tu aplicacion
	private static String DB_PATH="/data/data/com.AESCE.android/databases/";
	private static String DB_NAME= "frutisitio.db3";
	
	private SQLiteDatabase myDataBase;
	
	 private final Context myContext;
	
	/*
	* Constructor
	* 
	* Guarda una referencia al contexto para acceder a la carpeta assets de la aplicaci—n y a los recursos
	* @param contexto
	 */
	public BaseDatosHelper(Context context) {
		
		super(context, DB_NAME, null, 1);
		this.myContext=context;
		// TODO Auto-generated constructor stub
	}

	
	/* 
	 * Crea una base de datos vacia en el sistema y la sobreescribe con la que hemos puesto en Assets
	 */
	public void crearDataBase()throws IOException{
		boolean dbExist= comprobarBaseDatos();
		
		if(dbExist==true){
			//No hacemos nada
		}
		else{
		
			//Si no existe, creamos una nueva Base de datos en la carpeta por defecto de nuestra aplicacion, 
    		//de esta forma el Sistema nos permitira sobreescribirla con la que tenemos en la carpeta Assets
			this.getReadableDatabase();
			try{
				copiarBaseDatos();
			}
			catch(IOException e){
				throw new Error("Error al copiar la base de datos "+e.getMessage());
			}
		}
	}
	
	/*
     * Comprobamos si la base de datos existe
     * @return true si existe, false en otro caso
     */
	private boolean comprobarBaseDatos(){
		SQLiteDatabase checkDB=null;
		try{
			String myPath= DB_PATH+DB_NAME;
			checkDB=SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}
		catch(SQLException e){
			//No existe
		}
		
		if(checkDB!=null){
			checkDB.close();
		}
		return checkDB !=null ? true: false;
	}
	
	/*
     * Copia la base de datos desde la carpeta Assets sobre la base de datos vacia recien creada en la carpeta del sistema,
     * desde donde es accesible
     */
	private void copiarBaseDatos()throws IOException{
		//Abrimos la BBDD de la carpeta Assets como un InputStream
		InputStream myInput= myContext.getAssets().open(DB_NAME);
		
		//Carpeta de destino (donde hemos creado la BBDD vacia)
		String outFileName= DB_PATH+DB_NAME;
		
		//Abrimos la BBDD vacia como OutputStream
		OutputStream myOutput=new FileOutputStream(outFileName);
		
		//Transfiere los Bytes entre el Stream de entrada y el de Salida
		byte[]buffer=new byte[1024];
		int length;
		
		while((length=myInput.read(buffer))>0){
			myOutput.write(buffer,0,length);
		}
		
		//Cerramos los ficheros abiertos
		myOutput.flush();
		myOutput.close();
		myInput.close();
		
	}
	
	/*
     * Abre la base de datos
     */
	public String abrirBaseDatos()throws SQLException{
		String myPath=DB_PATH+DB_NAME;
		myDataBase=SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		return "Conexion exitosa";
	}
	
    /*
     * Cierra la base de datos
     */
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		/*
		arg0.execSQL("INSERT INTO USUARIO VALUES('nise14','123456',1,'nise14@hotmail.com','Nicolas')");
		arg0.execSQL("INSERT INTO USUARIO VALUES('jaq','123456',1,'jaq@umanizales.edu.co','Jorge')");
		arg0.execSQL("INSERT INTO USUARIO VALUES('guipa','123456',1,'guillermopatino@gmail.com','Guillermo')");
		*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
