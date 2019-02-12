package com.weather.WeatherApi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class City {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public City() {
	}

	public City(String cityName) {

		this.name = cityName;
	}

	public String getCityName() {
		return name;
	}

	public void setCityName(String cityName) {
		this.name = cityName;
	}

}