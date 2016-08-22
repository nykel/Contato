package br.com.teste.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.teste.bean.Contato;
import br.com.teste.contato.R;

import static br.com.teste.contato.R.layout.item_contato;

/**
 * Created by kathe_000 on 21/08/2016.
 */
public class ContatoAdapter extends BaseAdapter {

    private Activity activity;
    private List<Contato> contatos;


    public ContatoAdapter(Activity activity, List<Contato> contatos) {
        this.activity = activity;
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Contato contato = contatos.get(position);

        RecordHolder recordHolder = new RecordHolder();

        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(item_contato, parent, false);

            recordHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            recordHolder.txtTlefone = (TextView) view.findViewById(R.id.txtTelefone);

            view.setTag(recordHolder);
        } else {
            recordHolder = (RecordHolder) view.getTag();
        }


        recordHolder.txtNome.setText((CharSequence) contato.getNome());
        recordHolder.txtTlefone.setText(contato.getTelefone());

        return view;
    }

    static class RecordHolder {
        public TextView txtNome;
        public TextView txtTlefone;
    }

}
