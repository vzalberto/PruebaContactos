package com.example.pruebacontactos;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.widget.TextView;
import android.app.Activity;
import android.database.Cursor;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Consulta();
	}

	
	
	private void Consulta()
	{
		//Creamos un objeto de tipo Uri con el content provider que vamos a utilizar en este caso para obtener información a cerca de los contactos
        Uri uriContactos = ContactsContract.Contacts.CONTENT_URI;//Para obtener el nombre del contacto
        Uri uriTelefonos = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//Para obtener el teléfono del contacto
 
        //Array especificando las columnas que queremos obtener de Contactos (Uno para cada uri definida)
        String[] idNombre = new String[] {
                Contacts._ID,//Id contacto
                Contacts.DISPLAY_NAME//Nombre del contacto
        };
 
        String[] numeroTelefono = new String[]{
                CommonDataKinds.Phone.NUMBER//Teléfono del contacto
        };
 
        //Consulta para este registro
        //managedQuery es un método de la clase Activity
        Cursor cursorIdNombre = getContentResolver().query(uriContactos,
                idNombre, //Nombre de la columna que va a devolver, en este caso hemos creado anteriormente el array de nombre de columnas.
                null, // Cláusula where
                null, // Argumentos de selección
                null); // Orden del conenido.
 
        Cursor cursorNumeroTelefono = getContentResolver().query(uriTelefonos,
                    numeroTelefono,
                    null,
                    null,
                    null
                );
        
        
      //Si el cursor no contiene no contiene nigún registro, no entra en el if
        if ( cursorIdNombre.getCount() != 0)
        {
            //Colocamos a ambos cursores en el primer registro
            cursorIdNombre.moveToFirst();
            cursorNumeroTelefono.moveToFirst();
         
            //Vamos a obtener el numero de la columna con nombre DISPLAY_NAME(contiene el nombre de los contactos) para después poder acceder a ella
            int numeroColumnaNombre = cursorIdNombre.getColumnIndex(Contacts.DISPLAY_NAME);
            //Ahora obtenemos el primer registro de la columna que será el nombre del primer contacto de nuestra lista de contactos
            String nombreContacto = cursorIdNombre.getString(numeroColumnaNombre);
         
            //Hacemos lo mismo con el segundo cursor
            int numeroColumnaTelefono = cursorNumeroTelefono.getColumnIndex(Phone.NUMBER);
            String numeroContacto = cursorNumeroTelefono.getString(numeroColumnaTelefono);
         
            TextView tvContactos = (TextView)findViewById(R.id.textView1); 
            
            		tvContactos.append(nombreContacto +"-----"+numeroContacto+ "\n");
         
            //Ahora recorremos el cursor
            while(cursorIdNombre.moveToNext())
            {
                cursorNumeroTelefono.moveToNext();    
         
                nombreContacto = cursorIdNombre.getString(numeroColumnaNombre);
         
                numeroContacto = cursorNumeroTelefono.getString(numeroColumnaTelefono);
         
                tvContactos.append(nombreContacto +"-----"+numeroContacto+ "\n");
            }
        }
	}
	
	
}
