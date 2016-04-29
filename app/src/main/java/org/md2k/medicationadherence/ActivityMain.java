package org.md2k.medicationadherence;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.md2k.datakitapi.DataKitAPI;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.medicationadherence.model.Medication;
import org.md2k.medicationadherence.model.MedicationList;
import org.md2k.utilities.Apps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Nazir Saleheen <nazir.saleheen@gmail.com>
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = ActivityMain.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        final Button buttonService = (Button) findViewById(R.id.button_app_status);
        buttonService.setText("Add Medication");*/
        try {
            MedicationList medicationList = getMedications(new BufferedReader(new InputStreamReader(getAssets().open("medication.json"), "UTF-8")));
            prepareTable(medicationList.medications);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMain.this, "abdadf", Toast.LENGTH_SHORT).show();
            }
        });
*/
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    TableRow createDefaultRow() {
        TableRow row = new TableRow(this);
        TextView tvSensor = new TextView(this);
        tvSensor.setText("Name");
        tvSensor.setTypeface(null, Typeface.BOLD);
        tvSensor.setTextColor(getResources().getColor(R.color.teal_A700));
        TextView tvCount = new TextView(this);
        tvCount.setText("Dosage");
        tvCount.setTypeface(null, Typeface.BOLD);
        tvCount.setTextColor(getResources().getColor(R.color.teal_A700));
        TextView tvFreq = new TextView(this);
        tvFreq.setText("Time");
        tvFreq.setTypeface(null, Typeface.BOLD);
        tvFreq.setTextColor(getResources().getColor(R.color.teal_A700));
        row.addView(tvSensor);
        row.addView(tvCount);
        row.addView(tvFreq);
        return row;
    }

    public static boolean isExist(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public static MedicationList getMedications(BufferedReader br) {
        MedicationList medicationList = null;
        Gson gson = new Gson();
        medicationList = gson.fromJson(br, MedicationList.class);

        return medicationList;
    }

    void prepareTable(Medication[] medicationList) {
        TableLayout ll = (TableLayout) findViewById(R.id.tableLayout);
        ll.removeAllViews();
        ll.addView(createDefaultRow());

        for (Medication m : medicationList) {
            TableRow row = new TableRow(this);
            TextView tvSensor = new TextView(this);
            tvSensor.setText(m.generic_name + " "+m.trade_name);
            TextView tvCount = new TextView(this);
            tvCount.setText(m.strength[0].value + " " + m.strength[0].unit);
            TextView tvFreq = new TextView(this);
            tvFreq.setText("5:00 pm");
            row.addView(tvSensor);
            row.addView(tvCount);
            row.addView(tvFreq);
            ll.addView(row);
        }


    }

    public void addMedication(View view) {
        Intent intent = new Intent(this, AddMedicationActivity.class);
        startActivity(intent);


    }
}
