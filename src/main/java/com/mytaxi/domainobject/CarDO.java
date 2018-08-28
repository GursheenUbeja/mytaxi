package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;

@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "licenseplate", columnNames = { "licenseplate" }))
public class CarDO {

	public  CarDO() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime datecreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "license plate can not be null!")
	private String licenseplate;

	@Enumerated(EnumType.STRING)
	@Column
	private SeatCount seatcount;

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column
	private Long driverid;

	@Column
	private Boolean selected;

	@Column
	private String manufacturer;

	@Enumerated(EnumType.STRING)
	@Column
	private Rating rating;

	@Enumerated(EnumType.STRING)
	@Column
	private EngineType enginetype;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

	public Long getId() {
		return id;
	}

	public ZonedDateTime getDateCreated() {
		return datecreated;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public SeatCount getSeatcount() {
		return seatcount;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public Long getDriverid() {
		return driverid;
	}

	public Boolean getSelected() {
		return selected;
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

	public ZonedDateTime getDateCoordinateUpdated() {
		return dateCoordinateUpdated;
	}

	public void setEnginetype(EngineType enginetype) {
		this.enginetype = enginetype;
	}

	public void setDateCoordinateUpdated(ZonedDateTime dateCoordinateUpdated) {
		this.dateCoordinateUpdated = dateCoordinateUpdated;
	}

	public ZonedDateTime getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(ZonedDateTime datecreated) {
		this.datecreated = datecreated;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}

	public void setSeatcount(SeatCount seatcount) {
		this.seatcount = seatcount;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setDriverid(Long driverid) {
		this.driverid = driverid;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	private CarDO(ZonedDateTime datecreated, String licenseplate, SeatCount seatcount, Boolean deleted, Long driverid,
			Boolean selected, String manufacturer, Rating rating, EngineType enginetype,
			ZonedDateTime dateCoordinateUpdated) {

		this.datecreated = datecreated;
		this.licenseplate = licenseplate;
		this.seatcount = seatcount;
		this.deleted = deleted;
		this.driverid = driverid;
		this.selected = selected;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.enginetype = enginetype;
		this.dateCoordinateUpdated = dateCoordinateUpdated;
	}

	public static CarDOBuilder newBuilder() {
		return new CarDOBuilder();
	}

	public static class CarDOBuilder {

		private String licenseplate;
		private Long id;
		private ZonedDateTime datecreated = ZonedDateTime.now();

		private SeatCount seatcount;

		private Boolean deleted = false;

		private Long driverid;

		private Boolean selected;

		private String manufacturer;

		private Rating rating;

		private EngineType enginetype;

		public CarDOBuilder setLicenseplate(String licenseplate) {
			this.licenseplate = licenseplate;
			return this;
		}

		public CarDOBuilder setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public CarDOBuilder setSeatcount(SeatCount seatcount) {
			this.seatcount = seatcount;
			return this;
		}

		public CarDOBuilder setRating(Rating rating) {
			this.rating = rating;
			return this;
		}

		public CarDOBuilder setEnginetype(EngineType enginetype) {
			this.enginetype = enginetype;
			return this;
		}

		public CarDO createCarDO() {

			return new CarDO(datecreated, licenseplate, seatcount, deleted, driverid, selected, manufacturer, rating,
					enginetype, null);
		}

	}

}
