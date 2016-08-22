package br.com.teste.contato;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.teste.adapter.ContatoAdapter;
import br.com.teste.bean.Contato;

public class CadastroActivity extends AppCompatActivity {

    private ArrayList<Contato> contatos;
    private Activity activity;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        activity = this;

        listView = (ListView) findViewById(R.id.lstContatos);

        contatos = new ArrayList<Contato>();

        //Map<Integer, String> mpsTel = new HashMap<Integer, String>();

        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Long lookup_key = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                String name_raw_contact_id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                String display_name_primary = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                long photo_id = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                long photo_uri = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                long photo_thumbnail_uri = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                int has_phone_number = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                String nome = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                String foto = "https://lh4.googleusercontent.com/-GUNzVFlr1GQ/V2AtRyebfsI/AAAAAAAABQo/i2gAv2Nn8_QRJuab-1WH8QoElqVFxxn8ACL0B/s577-no/12718009_976294825759640_7068422327048946650_n.jpg";


                //ContatoNeon ct = new ContatoNeon(Integer.valueOf(id).intValue(), nome, foto, "", photoUri, has_phone_number);

                Contato contato = new Contato(nome);

                Cursor phoneCursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = " + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                        new String[]{id},
                        null
                );

                while (phoneCursor.moveToNext()) {
                    contato.setTelefone(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                }

                phoneCursor.close();

                contatos.add(contato);
            }
        }

        cur.close();

        ContatoAdapter contatosAdapter = new ContatoAdapter(activity, contatos);

        listView.setAdapter(contatosAdapter);

        listView.setCacheColorHint(Color.TRANSPARENT);
    }
}


