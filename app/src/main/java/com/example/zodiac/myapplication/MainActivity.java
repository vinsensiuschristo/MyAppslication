package com.example.zodiac.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DataHelper myDb;
    TextView etPrice;
    EditText berat, etName, etAlamat, etNameTo, etPanjang, etLebar, etTinggi; //id
    Integer kurir;


    private Spinner country_Spinner;
    private Spinner state_Spinner;
    private Spinner city_Spinner;
    private Spinner TOcountry_Spinner;
    private Spinner TOstate_Spinner;
    private Spinner TOcity_Spinner;
    private Button btnProcess, btnAll, btnSimpan;
    //Button btnSimpan;

    private ArrayAdapter<Country> countryArrayAdapter;
    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<City> cityArrayAdapter;
    private ArrayAdapter<TOCountry> TOcountryArrayAdapter;
    private ArrayAdapter<TOState> TOstateArrayAdapter;
    private ArrayAdapter<TOCity> TOcityArrayAdapter;

    private ArrayList<Country> countries;
    private ArrayList<State> states;
    private ArrayList<City> cities;
    private ArrayList<TOCountry> TOcountries;
    private ArrayList<TOState> TOstates;
    private ArrayList<TOCity> TOcities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataHelper(this);
        etName = (EditText)findViewById(R.id.etName);
        country_Spinner = (Spinner) findViewById(R.id.Country);
        state_Spinner = (Spinner) findViewById(R.id.States);
        city_Spinner = (Spinner) findViewById(R.id.City);
        etNameTo = (EditText)findViewById(R.id.etNameTo);
        etAlamat = (EditText)findViewById(R.id.etAlamat);
        TOcountry_Spinner = (Spinner) findViewById(R.id.CountryTo);
        TOstate_Spinner = (Spinner) findViewById(R.id.StatesTo);
        TOcity_Spinner = (Spinner) findViewById(R.id.CityTo);
        berat = (EditText)findViewById(R.id.etWeight);
        etPanjang = (EditText)findViewById(R.id.etPanjang);
        etLebar = (EditText)findViewById(R.id.etLebar);
        etTinggi = (EditText)findViewById(R.id.etTinggi);
        etPrice = (TextView)findViewById(R.id.etPrice);

        btnProcess = (Button)findViewById(R.id.btnProcess);
        btnSimpan = (Button)findViewById(R.id.btnSimpan);
        btnAll = (Button)findViewById(R.id.btnAll);




        //id = (EditText)findViewById(R.id.idKirim);

        initializeUI();
        cekHarga();
        viewAll();
        addData();
    }

    private void initializeUI() {
        country_Spinner = (Spinner) findViewById(R.id.Country);
        state_Spinner = (Spinner) findViewById(R.id.States);
        city_Spinner = (Spinner) findViewById(R.id.City);
        TOcountry_Spinner = (Spinner) findViewById(R.id.CountryTo);
        TOstate_Spinner = (Spinner) findViewById(R.id.StatesTo);
        TOcity_Spinner = (Spinner) findViewById(R.id.CityTo);

        countries = new ArrayList<>();
        states = new ArrayList<>();
        cities = new ArrayList<>();
        TOcountries = new ArrayList<>();
        TOstates = new ArrayList<>();
        TOcities = new ArrayList<>();


        createLists();
        createLists1();

        countryArrayAdapter = new ArrayAdapter<Country>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, countries);
        countryArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        country_Spinner.setAdapter(countryArrayAdapter);

        stateArrayAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, states);
        stateArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        state_Spinner.setAdapter(stateArrayAdapter);

        cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, cities);
        cityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        city_Spinner.setAdapter(cityArrayAdapter);

        TOcountryArrayAdapter = new ArrayAdapter<TOCountry>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, TOcountries);
        TOcountryArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        TOcountry_Spinner.setAdapter(TOcountryArrayAdapter);

        TOstateArrayAdapter = new ArrayAdapter<TOState>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, TOstates);
        TOstateArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        TOstate_Spinner.setAdapter(TOstateArrayAdapter);

        TOcityArrayAdapter = new ArrayAdapter<TOCity>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, TOcities);
        TOcityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        TOcity_Spinner.setAdapter(TOcityArrayAdapter);

        TOcountry_Spinner.setOnItemSelectedListener(TOcountry_listener);
        TOstate_Spinner.setOnItemSelectedListener(TOstate_listener);
        TOcity_Spinner.setOnItemSelectedListener(TOcity_listener);

        country_Spinner.setOnItemSelectedListener(country_listener);
        state_Spinner.setOnItemSelectedListener(state_listener);
        city_Spinner.setOnItemSelectedListener(city_listener);
    }

    private AdapterView.OnItemSelectedListener country_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final Country country = (Country) country_Spinner.getItemAtPosition(position);
                Log.d("SpinnerCountry", "onItemSelected: country: "+country.getCountryID());
                ArrayList<State> tempStates = new ArrayList<>();

                tempStates.add(new State(0, new Country(0, "Choose a Country"), "Pilih Kota"));

                for (State singleState : states) {
                    if (singleState.getCountry().getCountryID() == country.getCountryID()) {
                        tempStates.add(singleState);
                    }
                }

                stateArrayAdapter = new ArrayAdapter<State>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, tempStates);
                stateArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                state_Spinner.setAdapter(stateArrayAdapter);
            }

            cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<City>());
            cityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            city_Spinner.setAdapter(cityArrayAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };





    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final State state = (State) state_Spinner.getItemAtPosition(position);
                Log.d("SpinnerCountry", "onItemSelected: state: "+state.getStateID());
                ArrayList<City> tempCities = new ArrayList<>();

                Country country = new Country(0, "Pilih Provinsi");
                State firstState = new State(0, country, "Pilih Kota");
                tempCities.add(new City(0, country, firstState, "Pilih Kecamatan"));

                for (City singleCity : cities) {
                    if (singleCity.getState().getStateID() == state.getStateID()) {
                        tempCities.add(singleCity);
                    }
                }

                cityArrayAdapter = new ArrayAdapter<City>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, tempCities);
                cityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                city_Spinner.setAdapter(cityArrayAdapter);
            }
        }




        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };





    private AdapterView.OnItemSelectedListener city_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private AdapterView.OnItemSelectedListener TOcountry_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final TOCountry country = (TOCountry) TOcountry_Spinner.getItemAtPosition(position);
                Log.d("SpinnerTOCountry", "onItemSelected: country: "+country.getTOCountryID());
                ArrayList<TOState> tempTOStates = new ArrayList<>();

                tempTOStates.add(new TOState(0, new TOCountry(0, "Choose a TOCountry"), "Pilih Kota"));

                for (TOState singleTOState : TOstates) {
                    if (singleTOState.getTOCountry().getTOCountryID() == country.getTOCountryID()) {
                        tempTOStates.add(singleTOState);
                    }
                }

                TOstateArrayAdapter = new ArrayAdapter<TOState>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, tempTOStates);
                TOstateArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                TOstate_Spinner.setAdapter(TOstateArrayAdapter);
            }

            TOcityArrayAdapter = new ArrayAdapter<TOCity>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<TOCity>());
            TOcityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            TOcity_Spinner.setAdapter(TOcityArrayAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };





    private AdapterView.OnItemSelectedListener TOstate_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                final TOState state = (TOState) TOstate_Spinner.getItemAtPosition(position);
                Log.d("SpinnerTOCountry", "onItemSelected: state: "+state.getTOStateID());
                ArrayList<TOCity> tempCities = new ArrayList<>();

                TOCountry country = new TOCountry(0, "Pilih Provinsi");
                TOState firstTOState = new TOState(0, country, "Pilih Kota");
                tempCities.add(new TOCity(0, country, firstTOState, "Pilih Kecamatan"));

                for (TOCity singleTOCity : TOcities) {
                    if (singleTOCity.getTOState().getTOStateID() == state.getTOStateID()) {
                        tempCities.add(singleTOCity);
                    }
                }

                TOcityArrayAdapter = new ArrayAdapter<TOCity>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, tempCities);
                TOcityArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                TOcity_Spinner.setAdapter(TOcityArrayAdapter);
            }
        }




        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };





    private AdapterView.OnItemSelectedListener TOcity_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void createLists() {
        Country country0 = new Country(0, "Pilih Provinsi");
        Country country1 = new Country(1, "Jawa Barat");
        Country country2 = new Country(2, "Jakarta");
        Country country3 = new Country(3, "Jawa Tengah");

        countries.add(new Country(0, "Pilih Provinsi"));
        countries.add(new Country(1, "Jawa Barat"));
        countries.add(new Country(2, "Jakarta"));
        countries.add(new Country(3, "Jawa Tengah"));

        State state0 = new State(0, country0, "Pilih Kota");
        State state1 = new State(1, country1, "Bandung");
        State state2 = new State(2, country1, "Bogor");
        State state3 = new State(3, country2, "Jakarta Selatan");
        State state4 = new State(4, country2, "Jakarta Utara");
        State state5 = new State(5, country3, "Demak");
        State state6 = new State(6, country3, "Boyolali");

        states.add(state0);
        states.add(state1);
        states.add(state2);
        states.add(state3);
        states.add(state4);
        states.add(state5);
        states.add(state6);

        cities.add(new City(0, country0, state0, "Pilih Kecamatan"));
        cities.add(new City(1, country1, state1, "Cipaganti"));//Bandung
        cities.add(new City(2, country1, state1, "Dago"));//Bandung
        cities.add(new City(3, country1, state2, "Caringin"));//Bogor
        cities.add(new City(4, country2, state2, "Puncak"));//Bogor
        cities.add(new City(5, country2, state3, "Gandaria Utara"));//Jaksel
        cities.add(new City(6, country2, state3, "Cipete Utara"));//Jaksel
        cities.add(new City(7, country2, state4, "Koja Utara"));//Jakut
        cities.add(new City(7, country2, state4, "Koja Selatan"));//Jakut
        cities.add(new City(8, country3, state5, "Guntur"));//Demak
        cities.add(new City(9, country3, state5, "Genuk"));//Demak
        cities.add(new City(10, country3, state6, "Ampel"));//Boyolali
        cities.add(new City(11, country2, state6, "Andong"));//Boyolali
    }


    private void createLists1() {
        TOCountry country0 = new TOCountry(0, "Pilih Provinsi");
        TOCountry country1 = new TOCountry(1, "Jawa Barat");
        TOCountry country2 = new TOCountry(2, "Jakarta");
        TOCountry country3 = new TOCountry(3, "Jawa Tengah");

        TOcountries.add(new TOCountry(0, "Pilih Provinsi"));
        TOcountries.add(new TOCountry(1, "Jawa Barat"));
        TOcountries.add(new TOCountry(2, "Jakarta"));
        TOcountries.add(new TOCountry(3, "Jawa Tengah"));

        TOState state0 = new TOState(0, country0, "Pilih Kota");
        TOState state1 = new TOState(1, country1, "Bandung");
        TOState state2 = new TOState(2, country1, "Bogor");
        TOState state3 = new TOState(3, country2, "Jakarta Selatan");
        TOState state4 = new TOState(4, country2, "Jakarta Utara");
        TOState state5 = new TOState(5, country3, "Demak");
        TOState state6 = new TOState(6, country3, "Boyolali");

        TOstates.add(state0);
        TOstates.add(state1);
        TOstates.add(state2);
        TOstates.add(state3);
        TOstates.add(state4);
        TOstates.add(state5);
        TOstates.add(state6);

        TOcities.add(new TOCity(0, country0, state0, "Pilih Kecamatan"));
        TOcities.add(new TOCity(1, country1, state1, "Cipaganti"));//Bandung
        TOcities.add(new TOCity(2, country1, state1, "Dago"));//Bandung
        TOcities.add(new TOCity(3, country1, state2, "Caringin"));//Bogor
        TOcities.add(new TOCity(4, country2, state2, "Puncak"));//Bogor
        TOcities.add(new TOCity(5, country2, state3, "Gandaria Utara"));//Jaksel
        TOcities.add(new TOCity(6, country2, state3, "Cipete Utara"));//Jaksel
        TOcities.add(new TOCity(7, country2, state4, "Koja Utara"));//Jakut
        TOcities.add(new TOCity(7, country2, state4, "Koja Selatan"));//Jakut
        TOcities.add(new TOCity(8, country3, state5, "Guntur"));//Demak
        TOcities.add(new TOCity(9, country3, state5, "Genuk"));//Demak
        TOcities.add(new TOCity(10, country3, state6, "Ampel"));//Boyolali
        TOcities.add(new TOCity(11, country2, state6, "Andong"));//Boyolali
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class Country implements Comparable<Country> {

        private int countryID;
        private String countryName;


        public Country(int countryID, String countryName) {
            this.countryID = countryID;
            this.countryName = countryName;
        }

        public int getCountryID() {
            return countryID;
        }

        public String getCountryName() {
            return countryName;
        }

        @Override
        public String toString() {
            return countryName;
        }


        @Override
        public int compareTo(Country another) {
            return this.getCountryID() - another.getCountryID();//ascending order
//            return another.getCountryID()-this.getCountryID();//descending  order
        }
    }



    private class State implements Comparable<State> {

        private int stateID;
        private Country country;
        private String stateName;

        public State(int stateID, Country country, String stateName) {
            this.stateID = stateID;
            this.country = country;
            this.stateName = stateName;
        }

        public int getStateID() {
            return stateID;
        }

        public Country getCountry() {
            return country;
        }

        public String getStateName() {
            return stateName;
        }

        @Override
        public String toString() {
            return stateName;
        }

        @Override
        public int compareTo(State another) {
            return this.getStateID() - another.getStateID();//ascending order
//            return another.getStateID()-this.getStateID();//descending order
        }

    }



    private class City implements Comparable<City> {

        private int cityID;
        private Country country;
        private State state;
        private String cityName;

        public City(int cityID, Country country, State state, String cityName) {
            this.cityID = cityID;
            this.country = country;
            this.state = state;
            this.cityName = cityName;
        }


        public int getCityID() {
            return cityID;
        }

        public Country getCountry() {
            return country;
        }

        public State getState() {
            return state;
        }

        public String getCityName() {
            return cityName;
        }

        @Override
        public String toString() {
            return cityName;
        }

        @Override
        public int compareTo(City another) {
            return this.cityID - another.getCityID();//ascending order
//            return another.getCityID() - this.cityID;//descending order
        }
    }



    private class TOCountry implements Comparable<TOCountry> {

        private int countryID;
        private String countryName;


        public TOCountry(int countryID, String countryName) {
            this.countryID = countryID;
            this.countryName = countryName;
        }

        public int getTOCountryID() {
            return countryID;
        }

        public String getTOCountryName() {
            return countryName;
        }

        @Override
        public String toString() {
            return countryName;
        }


        @Override
        public int compareTo(TOCountry another) {
            return this.getTOCountryID() - another.getTOCountryID();//ascending order
//            return another.getTOCountryID()-this.getTOCountryID();//descending  order
        }
    }



    private class TOState implements Comparable<TOState> {

        private int stateID;
        private TOCountry country;
        private String stateName;

        public TOState(int stateID, TOCountry country, String stateName) {
            this.stateID = stateID;
            this.country = country;
            this.stateName = stateName;
        }

        public int getTOStateID() {
            return stateID;
        }

        public TOCountry getTOCountry() {
            return country;
        }

        public String getTOStateName() {
            return stateName;
        }

        @Override
        public String toString() {
            return stateName;
        }

        @Override
        public int compareTo(TOState another) {
            return this.getTOStateID() - another.getTOStateID();//ascending order
//            return another.getTOStateID()-this.getTOStateID();//descending order
        }

    }



    private class TOCity implements Comparable<TOCity> {

        private int cityID;
        private TOCountry country;
        private TOState state;
        private String cityName;

        public TOCity(int cityID, TOCountry country, TOState state, String cityName) {
            this.cityID = cityID;
            this.country = country;
            this.state = state;
            this.cityName = cityName;
        }


        public int getTOCityID() {
            return cityID;
        }

        public TOCountry getTOCountry() {
            return country;
        }

        public TOState getTOState() {
            return state;
        }

        public String getTOCityName() {
            return cityName;
        }

        @Override
        public String toString() {
            return cityName;
        }

        @Override
        public int compareTo(TOCity another) {
            return this.cityID - another.getTOCityID();//ascending order
//            return another.getTOCityID() - this.cityID;//descending order
        }
    }


    public void cekHarga(){
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringBeratHarga = berat.getText().toString();
                String stringDimensiPanjang = etPanjang.getText().toString();
                String stringDimensiLebar = etLebar.getText().toString();
                String stringDimensiTinggi = etTinggi.getText().toString();

                Integer IntegerDimensiHarga = Integer.parseInt(stringDimensiPanjang) * Integer.parseInt(stringDimensiLebar) * Integer.parseInt(stringDimensiTinggi) / 2000 * 2000;

                Integer IntergerBerat = Integer.parseInt(stringBeratHarga);
                Integer IntegerBeratHarga = Integer.parseInt(stringBeratHarga) * 2000;

                if (IntegerDimensiHarga > IntergerBerat){
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 1) { // JawaBarat ke JawaBarat
                        kurir = 2000 + IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // JawaBarat ke Jakarta
                        kurir = 2500+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // JawaBarat ke JawaTengah
                        kurir = 4000 + IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 1) { // Jakarta ke JawaBarat
                        kurir = 2500+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // Jakarta ke Jakarta
                        kurir = 2000+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jakarta ke Jawa Tengah
                        kurir = 4000+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jawa Tengah ke JawaBarat
                        kurir = 4000+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // Jawa Tengah ke Jakarta
                        kurir = 4000+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 3 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jawa Tengah ke Jawa Tengah
                        kurir = 2000+ IntegerDimensiHarga;
                        etPrice.setText("Harga Total Diambil Dari Dimensi Barang : RP. "+kurir);
                    }

                }else {

                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 1) { // JawaBarat ke JawaBarat
                        kurir = 2000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // JawaBarat ke Jakarta
                        kurir = 2500 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // JawaBarat ke JawaTengah
                        kurir = 4000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 1) { // Jakarta ke JawaBarat
                        kurir = 2500 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // Jakarta ke Jakarta
                        kurir = 2000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 2 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jakarta ke Jawa Tengah
                        kurir = 4000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jawa Tengah ke JawaBarat
                        kurir = 4000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 1 && TOcountry_Spinner.getSelectedItemPosition() == 2) { // Jawa Tengah ke Jakarta
                        kurir = 4000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                    if (country_Spinner.getSelectedItemPosition() == 3 && TOcountry_Spinner.getSelectedItemPosition() == 3) { // Jawa Tengah ke Jawa Tengah
                        kurir = 2000 + IntegerBeratHarga;
                        etPrice.setText("Harga Total Diambil Dari Berat Barang : RP. " + kurir);
                    }
                }
            }
        });
    }

    public void addData() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etName.getText().toString(),
                        country_Spinner.getSelectedItem().toString(),
                        state_Spinner.getSelectedItem().toString(),
                        city_Spinner.getSelectedItem().toString(),
                        etNameTo.getText().toString(),
                        etAlamat.getText().toString(),
                        TOcountry_Spinner.getSelectedItem().toString(),
                        TOstate_Spinner.getSelectedItem().toString(),
                        TOcity_Spinner.getSelectedItem().toString(),
                        berat.getText().toString(),
                        etPanjang.getText().toString(),
                        etLebar.getText().toString(),
                        etTinggi.getText().toString(),
                        etPrice.getText().toString());

//                etName = (EditText)findViewById(R.id.etName);
//                country_Spinner = (Spinner) findViewById(R.id.Country);
//                state_Spinner = (Spinner) findViewById(R.id.States);
//                city_Spinner = (Spinner) findViewById(R.id.City);
//                etNameTo = (EditText)findViewById(R.id.etNameTo);
//                etAlamat = (EditText)findViewById(R.id.etAlamat);
//                TOcountry_Spinner = (Spinner) findViewById(R.id.CountryTo);
//                TOstate_Spinner = (Spinner) findViewById(R.id.StatesTo);
//                TOcity_Spinner = (Spinner) findViewById(R.id.CityTo);
//                berat = (EditText)findViewById(R.id.etWeight);
//                etPanjang = (EditText)findViewById(R.id.etPanjang);
//                etLebar = (EditText)findViewById(R.id.etLebar);
//                etTinggi = (EditText)findViewById(R.id.etTinggi);
//                etPrice = (TextView)findViewById(R.id.etPrice);

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll() {
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Dari Provinsi: "+res.getString(2)+"\n");
                    buffer.append("Dari Kota: "+res.getString(3)+"\n");
                    buffer.append("Dari Kecamatan: "+res.getString(4)+"\n");
                    buffer.append("Nama Penerima: "+res.getString(5)+"\n");
                    buffer.append("Alamat: "+res.getString(6)+"\n");
                    buffer.append("Ke Provinsi: "+res.getString(7)+"\n");
                    buffer.append("Ke Kota: "+res.getString(8)+"\n");
                    buffer.append("Ke Kecamatan: "+res.getString(9)+"\n");
                    buffer.append("Berat : "+res.getString(10)+"Kg"+"\n");
                    buffer.append("Panjang : "+res.getString(11)+"Cm"+"\n");
                    buffer.append("Lebar : "+res.getString(12)+"Cm"+"\n");
                    buffer.append("Tinggi : "+res.getString(13)+"Cm"+"\n");
                    buffer.append(res.getString(14)+"\n"+"\n");
                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}

