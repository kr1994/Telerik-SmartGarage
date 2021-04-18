package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CurrencyRepository;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.exceptions.NoConnectionWithTheUrlException;
import com.java.smart_garage.models.Currency;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyMultiplierServiceImpl implements CurrencyMultiplierService {

    public static final int START_OF_CURRENCY_LIST = 81;
    public static final int END_OF_CURRENCY_LIST = 2;
    public static final int BEGIN_INDEX = 1;
    public static final int END_INDEX = 4;
    private final CurrencyRepository repository;

    @Autowired
    public CurrencyMultiplierServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    public List<Currency> getAllCurrency(){
        return repository.getAllCurrencies();
    }

    @Override
    public double getCurrency(String value) {
        repository.getCurrencyByName(value);
        Map<String, Double> currencyValues;
        try{
        currencyValues = currencyValues();
        }catch (Exception e){
            throw new NoConnectionWithTheUrlException("http://data.fixer.io/api/latest?access_key=86c41c08bbf223a557ca111f81ec673e");
        }
        double currency = 0;
        for (String s : currencyValues.keySet()) {
            if(value.equals(s)){
                currency = currencyValues.get(s);
            }
        }return currency;
    }

    private Map<String, Double> currencyValues() throws Exception {
        URL url = new URL("http://data.fixer.io/api/latest?access_key=86c41c08bbf223a557ca111f81ec673e");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String rates = content.substring(START_OF_CURRENCY_LIST, content.toString().length() - END_OF_CURRENCY_LIST);

        Map<String, Double> currencyRates = new HashMap<>();


        String[] pairs = rates.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            currencyRates.put(keyValue[0].substring(BEGIN_INDEX, END_INDEX), Double.valueOf(keyValue[1]));
        }
        in.close();
        return currencyRates;
    }



}
