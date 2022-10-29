package com.example.realestatemanager.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestatemanager.PropertyViewModel;
import com.example.realestatemanager.R;
import com.example.realestatemanager.injection.Injection;
import com.example.realestatemanager.injection.ViewModelFactory;


public class LoanSimulatorFragment extends Fragment {

    private Button calculatorButton;
    private EditText loanAmount;
    private EditText loanTerm;
    private EditText interestRate;
    private EditText contribution;
    private TextView paymentEveryMonth;
    private TextView totalInterest;
    private TextView totalPayment;

    private String loanAmountListener;
    private String loanTermListener;
    private String interestRateListener;
    private String contributionListener;

    private PropertyViewModel propertyViewModel;


    public LoanSimulatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_simulator, container, false);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loanAmountListener();
        contributionListener();
        loanTermListener();
        interestRateListener();
        calculateButton();

    }
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PropertyViewModel.class);

    }
    public void init(View view) {
        calculatorButton = view.findViewById(R.id.button_calculate);
        loanAmount = view.findViewById(R.id.loan_amount_value);
        loanTerm = view.findViewById(R.id.loan_term_value);
        interestRate = view.findViewById(R.id.interest_rate_value);
        contribution = view.findViewById(R.id.contribution_value);
        paymentEveryMonth = view.findViewById(R.id.payment_every_month_value);
        totalInterest = view.findViewById(R.id.total_interest_value);
        totalPayment = view.findViewById(R.id.total_payments_value);
    }

    public void loanAmountListener() {
        loanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > -1) {
                    loanAmountListener = String.valueOf(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void contributionListener(){
        contribution.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > -1) {
                    contributionListener = String.valueOf(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loanTermListener() {
        loanTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > -1) {
                    loanTermListener = String.valueOf(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void interestRateListener() {
        interestRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > -1) {
                    interestRateListener = String.valueOf(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void calculateButton() {
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loanAmountListener != null && loanTermListener != null
                        && interestRateListener != null && contributionListener != null && !loanAmountListener.equals("") && !loanTermListener.equals("")
                        && !interestRateListener.equals("") && !contributionListener.equals("")) {
                    String monthly = propertyViewModel.calculateMonthly(loanAmountListener, interestRateListener, loanTermListener, contributionListener);
                    paymentEveryMonth.setText(monthly);
                    String totalInterestValue = propertyViewModel.calculateTotalInterest(loanAmountListener, monthly, loanTermListener, contributionListener);
                    totalInterest.setText(totalInterestValue);
                    String totalPaymentValue = propertyViewModel.calculateTotalPayment(loanAmountListener, totalInterestValue, contributionListener);
                    totalPayment.setText(totalPaymentValue);
                } else {
                    Toast.makeText(getContext(), "Fill up Amount, Contribution, Term and Interest Fields", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void calculate(String amount, String interestRate, String years) {
        double yearsValue = Double.valueOf(years);
        double interestRateValue = Double.valueOf(interestRate) / 100;
        double amountValue = Double.valueOf(amount);

        double monthValue = (amountValue * interestRateValue /12) / (1-(Math.pow((1+interestRateValue/12),(-12*yearsValue))));
        double monthValueFormat = (int)(Math.round(monthValue * 100))/100.0;
        paymentEveryMonth.setText(Double.toString(monthValueFormat));

        double totalInterestValue = 12 * yearsValue * monthValue - amountValue;
        double totalInterestValueFormat = (int)(Math.round(totalInterestValue * 100))/100.0;
        totalInterest.setText(Double.toString(totalInterestValueFormat));

        double totalPaymentValue = totalInterestValue + amountValue;
        double totalPaymentValueFormat = (int)(Math.round(totalPaymentValue * 100))/100.0;
        totalPayment.setText(Double.toString(totalPaymentValueFormat));
    }


}