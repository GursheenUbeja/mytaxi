package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.Rating;


public class SearchDTO {

	@NotNull(message = "Username can not be null!")
	private String username;

	@NotNull(message = "licenseplate can not be null!")
	private String licenseplate;

	private String manufacturer;

	private Rating rating;

	private EngineType enginetype;
	private OnlineStatus onlineStatus;

	private SearchDTO() {

	}

	public SearchDTO(String username, String licenseplate, String manufacturer, Rating rating,
			EngineType enginetype, OnlineStatus onlineStatus) {

		this.username = username;
		this.licenseplate = licenseplate;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.enginetype = enginetype;
		this.onlineStatus = onlineStatus;
	}

	public String getUsername() {
		return username;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public Rating getRating() {
		return rating;
	}

	public EngineType getEnginetype() {
		return enginetype;
	}

	public OnlineStatus getOnlineStatus() {
		return onlineStatus;
	}

	public static SearchDTOBuilder newBuilder() {
		return new SearchDTOBuilder();
	}

	public static class SearchDTOBuilder {

		private String username;

		private String licenseplate;

		private String manufacturer;
		

		private Rating rating;

		private EngineType enginetype;
		private OnlineStatus onlineStatus;

		public SearchDTO createSearchDTO() {
			return new SearchDTO(username, licenseplate, manufacturer, rating, enginetype, onlineStatus);
		}

		public SearchDTOBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public SearchDTOBuilder setLicenseplate(String licenseplate) {
			this.licenseplate = licenseplate;
			return this;
		}

		public SearchDTOBuilder setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}


		public SearchDTOBuilder setRating(Rating rating) {
			this.rating = rating;
			return this;
		}

		public SearchDTOBuilder setEnginetype(EngineType enginetype) {
			this.enginetype = enginetype;
			return this;
		}

		public SearchDTOBuilder setOnlineStatus(OnlineStatus onlineStatus) {
			this.onlineStatus = onlineStatus;
			return this;
		}

	}

}
