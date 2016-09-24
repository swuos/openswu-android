package com.swuos.ALLFragment.charge;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mran.polylinechart.ChargeBean;
import com.mran.polylinechart.LineChartView;
import com.swuos.ALLFragment.BaseFragment;
import com.swuos.ALLFragment.charge.presenter.IChagerPresenterCompl;
import com.swuos.ALLFragment.charge.presenter.IChargePresenetr;
import com.swuos.ALLFragment.charge.view.IChargeFragment;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ChargeFragment extends BaseFragment implements IChargeFragment, View.OnClickListener, AdapterView.OnItemSelectedListener, DialogInterface.OnCancelListener {
    LineChartView lineChartView;
    private Spinner buildingSpinner;
    private EditText roomEditText;
    private Button querybutton;
    private RelativeLayout charg_relativelayout;
    private Button balanceButton;
    private IChargePresenetr iChargePresenetr;
    private String building;
    private String room;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View chargeLayout = inflater.inflate(R.layout.charge_layout, container, false);
        iChargePresenetr = new IChagerPresenterCompl(getActivity(), this);
        bindview(chargeLayout);
        initview();

        return chargeLayout;
    }

    private void bindview(View view) {
        buildingSpinner = (Spinner) view.findViewById(R.id.build_spinner);
        roomEditText = (EditText) view.findViewById(R.id.room_editText);
        querybutton = (Button) view.findViewById(R.id.charg_query_button);
        charg_relativelayout = (RelativeLayout) view.findViewById(R.id.charg_relativelayout);
        lineChartView = (LineChartView) view.findViewById(R.id.linechartview);
        balanceButton = (Button) view.findViewById(R.id.charg_balance);
        dynamicAddView(charg_relativelayout, "background", R.color.colorPrimary);
        progressDialog = new ProgressDialog(getActivity());
    }

    private void initview() {
        ArrayAdapter<CharSequence> arrayAdapterBuilding = ArrayAdapter.createFromResource(getActivity(), R.array.building, R.layout.grades_spinner_layout);
        arrayAdapterBuilding.setDropDownViewResource(R.layout.grades_spinnerdown_layout);
        buildingSpinner.setAdapter(arrayAdapterBuilding);
        buildingSpinner.setOnItemSelectedListener(this);
        querybutton.setOnClickListener(this);
        progressDialog.setTitle("水电费查询");
        progressDialog.setCancelable(true);
        progressDialog.setMessage("正在查询");
        progressDialog.setOnCancelListener(this);
    }

    @Override
    public void showBalance(String balance) {
        balanceButton.setText(balance);
    }

    @Override
    public void showDailyconsume(List<ChargeBean> chargeBeanList) {
        progressDialog.cancel();
        lineChartView.addData(chargeBeanList);
    }

    @Override
    public void showError(String s) {
        progressDialog.cancel();
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.charg_query_button:
                progressDialog.show();
                iChargePresenetr.query(building, roomEditText.getText().toString());
                break;
            default: break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        building = String.valueOf(Constant.BUILDING[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        iChargePresenetr.cancle();
    }
}
