package tech.adelemphii.arma3calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.snackbar.Snackbar;

import tech.adelemphii.arma3calculator.objects.Elevation;
import tech.adelemphii.arma3calculator.utility.RangeUtility;

public class ArtilleryComputerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artillery_computer);

        findViewById(R.id.calculateButton).setOnClickListener(this);
        findViewById(R.id.advancedSwitch).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.calculateButton) {
            calculate(view);
        }

        if(view.getId() == R.id.advancedSwitch) {
            Snackbar.make(view, R.string.unsupported, 5000)
                    .setBackgroundTint(getResources().getColor(R.color.black))
                    .setTextColor(getResources().getColor(R.color.white))
                    .show();
            SwitchCompat switchCompat = findViewById(R.id.advancedSwitch);
            switchCompat.setChecked(false);
        }
    }

    private void calculate(View view) {
        EditText rangeText = findViewById(R.id.rangeText);
        EditText heightDiffText = findViewById(R.id.heightDifferenceText);

        TextView rangeResult = findViewById(R.id.rangeResultView);
        TextView elevationResult = findViewById(R.id.elevationResultView);
        TextView chargeResult = findViewById(R.id.chargeResultView);
        TextView flightResult = findViewById(R.id.flightTimeView);
        TextView elevationDiffResult = findViewById(R.id.elevationDiffView);
        TextView elevationCalcResult = findViewById(R.id.elevationDiffCalculatedView);

        int range;
        int heightDifference;

        try {
            range = Integer.parseInt(rangeText.getText().toString());
        } catch(NumberFormatException e) {
            Snackbar.make(view, R.string.range_error, 5000)
                    .setBackgroundTint(getResources().getColor(R.color.black))
                    .setTextColor(getResources().getColor(R.color.white))
                    .show();
            setVisibilities(rangeResult, elevationResult, chargeResult, flightResult, elevationCalcResult, elevationDiffResult, View.GONE, null);
            return;
        }

        if(range < 150 || range > 2600) {
            Snackbar.make(view, R.string.range_error, 5000)
                    .setBackgroundTint(getResources().getColor(R.color.black))
                    .setTextColor(getResources().getColor(R.color.white))
                    .show();
            setVisibilities(rangeResult, elevationResult, chargeResult, flightResult, elevationCalcResult, elevationDiffResult, View.GONE, null);
            return;
        }

        try {
            heightDifference = Integer.parseInt(heightDiffText.getText().toString());
        } catch(NumberFormatException e) {
            Snackbar.make(view, R.string.height_diff_error, 5000)
                    .setBackgroundTint(getResources().getColor(R.color.black))
                    .setTextColor(getResources().getColor(R.color.white))
                    .show();
            setVisibilities(rangeResult, elevationResult, chargeResult, flightResult, elevationCalcResult, elevationDiffResult, View.GONE, null);
            return;
        }

        Elevation elevation = RangeUtility.calculateElevation(range);
        if(elevation != null) {
            double flightTime = RangeUtility.calculateFlightTime(elevation);
            elevation.setFlightTime(flightTime);

            double elevationDiff = RangeUtility.calculateElevationDiff(elevation, heightDifference);

            elevation.setElevationDiff((int) Math.ceil(elevationDiff));

            setVisibilities(rangeResult, elevationResult, chargeResult, flightResult, elevationCalcResult, elevationDiffResult, View.VISIBLE, elevation);
        }
    }

    private void setVisibilities(TextView rangeResult, TextView elevationResult, TextView chargeResult, TextView flightResult, TextView elevationCalcResult,
                                 TextView elevationDiffResult, int status, @Nullable  Elevation elevation) {

        if(elevation != null) {
            rangeResult.setText(getString(R.string.range_result, elevation.getRange() + ""));
            elevationResult.setText(getString(R.string.elevation_result, elevation.getElevation() + ""));
            chargeResult.setText(getString(R.string.charge_result, elevation.getCharge() + ""));
            flightResult.setText(getString(R.string.flight_time_result, elevation.getFlightTime() + ""));

            int diffCalc = elevation.getElevation() - elevation.getElevationDiff();
            elevationCalcResult.setText(getString(R.string.elevation_calculation_result, diffCalc + ""));
            elevationDiffResult.setText(getString(R.string.elevation_diff_result, elevation.getElevationDiff() + ""));
        }

        rangeResult.setVisibility(status);
        elevationResult.setVisibility(status);
        chargeResult.setVisibility(status);
        flightResult.setVisibility(status);
        elevationCalcResult.setVisibility(status);
        elevationDiffResult.setVisibility(status);
    }
}