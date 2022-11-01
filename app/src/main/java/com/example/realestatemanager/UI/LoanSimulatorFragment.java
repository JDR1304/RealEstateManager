package com.example.realestatemanager.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestatemanager.R;


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



    public LoanSimulatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    String monthly = calculateMonthly(loanAmountListener, interestRateListener, loanTermListener, contributionListener);
                    paymentEveryMonth.setText(monthly);
                    String totalInterestValue = calculateTotalInterest(loanAmountListener, monthly, loanTermListener, contributionListener);
                    totalInterest.setText(totalInterestValue);
                    String totalPaymentValue = calculateTotalPayment(loanAmountListener, totalInterestValue, contributionListener);
                    totalPayment.setText(totalPaymentValue);
                } else {
                    Toast.makeText(getContext(), "Fill in Amount, Contribution, Term and Interest Fields", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public String calculateMonthly(String amount, String interestRate, String years, String contribution) {
        double yearsValue = Double.valueOf(years);
        double interestRateValue = Double.valueOf(interestRate) / 100;
        double amountValue = Double.valueOf(amount);
        double contributionValue = Double.valueOf(contribution);
        double amountValueMinusContribution = amountValue - contributionValue;

        double monthValue = (amountValueMinusContribution * interestRateValue /12) / (1-(Math.pow((1+interestRateValue/12),(-12*yearsValue))));
        double monthValueFormat = (int)(Math.round(monthValue * 100))/100.0;
        return Double.toString(monthValueFormat);
    }

    public String calculateTotalInterest(String amount, String monthValue, String years, String contribution) {
        double yearsValue = Double.valueOf(years);
        double amountValue = Double.valueOf(amount);
        double monthly = Double.valueOf(monthValue);
        double contributionValue = Double.valueOf(contribution);
        double amountValueMinusContribution = amountValue - contributionValue;

        double totalInterestValue = 12 * yearsValue * monthly - amountValueMinusContribution;
        double totalInterestValueFormat = (int)(Math.round(totalInterestValue * 100))/100.0;
        return Double.toString(totalInterestValueFormat);
    }

    public String calculateTotalPayment(String amount, String totalInterest, String contribution) {

        double amountValue = Double.valueOf(amount);
        double contributionValue = Double.valueOf(contribution);
        double amountValueMinusContribution = amountValue - contributionValue;
        double totalInterestValue = Double.valueOf(totalInterest);
        double totalPaymentValue = totalInterestValue + amountValueMinusContribution;
        double totalPaymentValueFormat = (int)(Math.round(totalPaymentValue * 100))/100.0;
        return Double.toString(totalPaymentValueFormat);

    }


}