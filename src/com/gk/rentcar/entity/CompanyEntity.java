package com.gk.rentcar.entity;

import java.util.ArrayList;

public class CompanyEntity {
	private ArrayList<CarTypeEntity> carlist;
	private String name;
	private double lng;
	private double lat;
	
	
	public CompanyEntity(ArrayList<CarTypeEntity> carlist, String name,
			double lng, double lat) {
		this.carlist = carlist;
		this.name = name;
		this.lng = lng;
		this.lat = lat;
	}
	
	public ArrayList<CarTypeEntity> getCarlist() {
		return carlist;
	}
	public void setCarlist(ArrayList<CarTypeEntity> carlist) {
		this.carlist = carlist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	
}
