package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
	@JsonIgnore
	private Long id;

	@NotNull(message = "licenseplate can not be null!")
	private String licenseplate;

	private String manufacturer;
	private SeatCount seatcount;

	private Rating rating;

	private EngineType enginetype;

	public CarDTO(Long id, @NotNull(message = "licenseplate can not be null!") String licenseplate, String manufacturer,
			SeatCount seatcount, Rating rating, EngineType enginetype) {
		
		this.id = id;
		this.licenseplate = licenseplate;
		this.manufacturer = manufacturer;
		this.seatcount = seatcount;
		this.rating = rating;
		this.enginetype = enginetype;
	}

	private CarDTO() {
	}

	public static CarDTOBuilder newBuilder() {
		return new CarDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public SeatCount getSeatcount() {
		return seatcount;
	}

	public Rating getRating() {
		return rating;
	}

	public EngineType getEnginetype() {
		return enginetype;
	}

	public static class CarDTOBuilder {

		private String licenseplate;
		private Long id;
		private String manufacturer;
		private SeatCount seatcount;

		private Rating rating;

		private EngineType enginetype;

		public CarDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public CarDTOBuilder setLicenseplate(String licenseplate) {
			this.licenseplate = licenseplate;
			return this;
		}

		public CarDTOBuilder setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public CarDTOBuilder setSeatcount(SeatCount seatcount) {
			this.seatcount = seatcount;
			return this;
		}

		public CarDTOBuilder setRating(Rating rating) {
			this.rating = rating;
			return this;
		}

		public CarDTOBuilder setEnginetype(EngineType enginetype) {
			this.enginetype = enginetype;
			return this;
		}

		public CarDTO createCarDTO() {
			
			return new CarDTO( id, licenseplate,  manufacturer,
					 seatcount,  rating,  enginetype);
		}

	}

}
