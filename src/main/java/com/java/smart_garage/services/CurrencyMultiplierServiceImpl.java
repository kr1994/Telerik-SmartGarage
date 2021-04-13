package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CurrencyRepository;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyMultiplierServiceImpl implements CurrencyMultiplierService {

    private final CurrencyRepository repository;

    @Autowired
    public CurrencyMultiplierServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public double getCurrency(String value) throws Exception {
        repository.getCurrencyByName(value);
        Map<String, Double> myMap = currencyValues();
        double currency = 0;
        for (String s : myMap.keySet()) {
            if(value.equals(s)){
                currency = myMap.get(s);
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
        String someString = content.substring(81, content.toString().length() - 2);

        Map<String, Double> myMap = new HashMap<String, Double>();
        String[] pairs = someString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            myMap.put(keyValue[0].substring(1, 4), Double.valueOf(keyValue[1]));
        }
        in.close();
        return myMap;
    }



}
