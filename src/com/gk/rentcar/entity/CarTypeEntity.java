package com.gk.rentcar.entity;


import android.os.Parcel;
import android.os.Parcelable;
/**
 * 实现Parcelable接口和Serializable接口都能提供序列化的作用，
 * Parcelable实现内存里的序列化，不能硬盘序列化。Serializable实现硬盘和网络序列化，效率低。
 * 重写writeToParcel和Parcelable.Creator<T>时，注意两点
 * 1，写入顺序必须和读取顺序一致
 * 2，Parcelable.Creator<T>的关键字是 public static final
 * @author guokai
 *
 */
public class CarTypeEntity implements Parcelable{
	public String carType;
	public String carInfo;
	public String carImgUrl;
	
	public CarTypeEntity(String carType, String carInfo, String carImgUrl) {
		super();
		this.carType = carType;
		this.carInfo = carInfo;
		this.carImgUrl = carImgUrl;
	}

	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
	public String getCarImgUrl() {
		return carImgUrl;
	}
	public void setCarImgUrl(String carImgUrl) {
		this.carImgUrl = carImgUrl;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(carType);
		dest.writeString(carInfo);
		dest.writeString(carImgUrl);
	}
	
	 public static final Parcelable.Creator<CarTypeEntity> creator = new Creator<CarTypeEntity>() {
		
		@Override
		public CarTypeEntity[] newArray(int size) {
			return new CarTypeEntity[size];
		}
		
		@Override
		public CarTypeEntity createFromParcel(Parcel source) {
			CarTypeEntity entity = new CarTypeEntity(source.readString(),source.readString(),source.readString());
			return entity;
		}
	};
}
