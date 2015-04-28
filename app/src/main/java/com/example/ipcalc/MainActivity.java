package com.example.ipcalc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText ipStr;
	private Spinner mascara;
	private String mascara_sel;
	private TextView network;
	private TextView broadcast;
	private EditText resultado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ipStr = (EditText) findViewById(R.id.ipStr);
		resultado = (EditText) findViewById(R.id.resultado);
		mascara = (Spinner) findViewById(R.id.mascara);
		network = (TextView) findViewById(R.id.network);
		broadcast = (TextView) findViewById(R.id.broadcast);

		ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
				this, R.array.mascaras, android.R.layout.simple_spinner_item);

		adaptador
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mascara.setAdapter(adaptador);
		mascara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long id) {
				mascara_sel = getResources().getStringArray(R.array.mascaras)[position];
				network.setText("");
				broadcast.setText("");
				resultado.setText("");
			}

			public void onNothingSelected(AdapterView<?> parent) {
				mascara_sel = "255.255.255.255";
			}
		});

		network.setText("");
		broadcast.setText("");
		resultado.setText("");
		
		TextView txtCambiado = (TextView)findViewById(R.id.textView4);
		Editable str = Editable.Factory.getInstance().newEditable(txtCambiado.getText().toString());
		str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, txtCambiado.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		txtCambiado.setText(str);
		
		ipStr.requestFocus();
		
	}

	public void alerta(String cadena) {
		/*
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage(cadena);
		dialogBuilder.setCancelable(true).setTitle("Advertencia");
		dialogBuilder.create().show();
		*/
		
		new AlertDialog.Builder(this)
			.setMessage(cadena)
			.setCancelable(true).setTitle("Advertencia")
			.create()
			.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void execCalc(View view) {
		String txt;

		ipStr.setText(ipStr.getText().toString().trim());
		
		if (ipStr.getText().toString().equals("")) {
			alerta("Debe entrar una IP");
			return;
		}
		if (IpLib.ipClase(ipStr.getText().toString()).equals("N")) {
			alerta("IP incorrecta");
			return;
		}

		network.setText(IpLib.cNetwork(ipStr.getText().toString(), mascara_sel));
		broadcast.setText(IpLib.cBroadcast(ipStr.getText().toString(), network
				.getText().toString(), mascara_sel));
		txt = "Clase: " + IpLib.ipClase(ipStr.getText().toString()) + "\n";
		txt += "IP Num: " + IpLib.ip2Num(ipStr.getText().toString()) + "\n";
		txt += "Mask Bit: " + IpLib.maskToInt(mascara_sel) + "\n";
		txt += "Cant.Ips: " + IpLib.countIps(mascara_sel)+ "\n";
		txt += "Tipo IP: " + ( (IpLib.ipIntranet(ipStr.getText().toString()) ? "Privada" : "Publica"));

		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ipStr.getWindowToken(), 0);

		
		resultado.setText(txt);
	}

	public void execClean(View view) {
		mascara.setSelection(0);
		ipStr.setText("");
		network.setText("");
		broadcast.setText("");
		resultado.setText("");

	}

}
