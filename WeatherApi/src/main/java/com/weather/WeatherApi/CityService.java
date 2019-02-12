package com.weather.WeatherApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public void addCity(City city) {
		cityRepository.save(city);
	}

	public List<City> getAllCities() {
		return cityRepository.findAll();
	}
}
