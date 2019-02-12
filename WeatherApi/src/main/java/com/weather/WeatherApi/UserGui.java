package com.weather.WeatherApi;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class UserGui extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField tfCity;
	private Button buttonSend;
	@Autowired
	private CityService cityService;
	private TextArea taCities;
	@Autowired
	private RestTemplate restTemplate;

	public UserGui() {
		this.tfCity = new TextField("city");
		this.buttonSend = new Button("send");
		taCities = new TextArea("cities with temperatures");
		buttonSend.addClickListener(clickEvent -> {
			addCity(tfCity.getValue());
			showCitiesWithTemperatures();
		});
		add(tfCity);
		add(buttonSend);
		add(taCities);
	}

	public void addCity(String city) {
		
		String [] cities = city.split(",");
		for (String string : cities) {
			cityService.addCity(new City(string.trim()));
		}
	}

	public void showCitiesWithTemperatures() {
		List<City> cities = cityService.getAllCities();

		StringBuffer sb = new StringBuffer();

		cities.forEach(x -> {
			ResponseEntity<String> cityResponse = restTemplate.exchange(
					"https://openweathermap.org/data/2.5/find?q=" + x.getCityName()
							+ "&units=metric&appid=b6907d289e10d714a6e88b30761fae22",
					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
					});
			sb.append(x.getCityName() + " -> "
					+ kelvinToCelcius(cityResponse.getBody().substring(cityResponse.getBody().indexOf("\"temp\":") + 7,
							cityResponse.getBody().indexOf("\"temp\":") + 13))
					+ "\n");
		});

		taCities.setValue(sb.toString());
	}
	
	private String kelvinToCelcius(String kel) {

		return String.valueOf(Math.floor(Double.valueOf(kel) - 272.15));
	}

}
