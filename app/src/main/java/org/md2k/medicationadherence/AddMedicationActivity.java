package org.md2k.medicationadherence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.md2k.medicationadherence.model.Medication;
import org.md2k.medicationadherence.model.MedicationList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddMedicationActivity extends Activity {

    String[] meditationNameSpinner;
    String[] dosageSpinner;
    MedicationList medicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        try {
            medicationList = ActivityMain.getMedications(new BufferedReader(new InputStreamReader(getAssets().open("medication.json"), "UTF-8")));

            setMedicationSpinner();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setMedicationSpinner() {
        this.meditationNameSpinner = new String[medicationList.medications.length];
        for (int i=0; i<medicationList.medications.length; i++)
            meditationNameSpinner[i]=medicationList.medications[i].generic_name;

        Spinner sMedName = (Spinner) findViewById(R.id.medName);
        ArrayAdapter<String> adapterMedName = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, meditationNameSpinner);

        sMedName.setAdapter(adapterMedName);

        sMedName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner sMedName = (Spinner) findViewById(R.id.medName);
                Spinner sDosage = (Spinner) findViewById(R.id.medName);
                Medication m = getMedication((String) sMedName.getSelectedItem());
/*                String[] dosages = new String[m.dosages.length];
                for(int i=0; i<dosages.length; i++)
                    dosages[i] = m.dosages[i].value + " "+m.dosages[i].unit;
                ArrayAdapter<String> adapterDosage = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, dosages);
                sDosage.setAdapter(adapterDosage);*/
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
    }
    private Medication getMedication(String selectedItem) {
        for (int i=0; i<medicationList.medications.length; i++)
            if(medicationList.medications[i].generic_name.equals(selectedItem))
                return medicationList.medications[i];
        return null;
    }
    public void createMedication(View view) {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }
}
