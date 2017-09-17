package com.emay.lnglatoffset;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table(value="\"offset\"")
@PK(value={"lng","lat"})
public class Offset {

	@Column(value="id")
	private Integer id;	
	
	@Column(value="lng")
	private Integer lng;
	
	@Column(value="lat")
	private Integer lat;
	
	@Column(value="lngoffset")
	private Integer LngOffset;
	
	@Column(value="latoffset")
	private Integer LatOffset;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLng() {
		return lng;
	}

	public void setLng(Integer lng) {
		this.lng = lng;
	}

	public Integer getLat() {
		return lat;
	}

	public void setLat(Integer lat) {
		this.lat = lat;
	}

	public Integer getLngOffset() {
		return LngOffset;
	}

	public void setLngOffset(Integer lngOffset) {
		LngOffset = lngOffset;
	}

	public Integer getLatOffset() {
		return LatOffset;
	}

	public void setLatOffset(Integer latOffset) {
		LatOffset = latOffset;
	}
	
	
	
}
