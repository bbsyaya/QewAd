package com.guang.client.mode;

public class GUser {
	private int id;
	private String name;
	private String password;
	// �豸���
	private String deviceId;// imei
	private String phoneNumber;// �ֻ�����
	private String networkOperatorName;// ��Ӫ������
	private String simSerialNumber;// sim�����к�
	private String networkCountryIso;// sim�����ڹ���
	private String networkOperator;// ��Ӫ�̱��
	private String networkType;// ��������
	private String location;// �ƶ��ն˵�λ��
	/**
	 * �ƶ��ն˵����� PHONE_TYPE_CDMA �ֻ���ʽΪCDMA������ 2 PHONE_TYPE_GSM �ֻ���ʽΪGSM���ƶ�����ͨ 1
	 * PHONE_TYPE_NONE �ֻ���ʽδ֪ 0
	 */
	private int phoneType;//
	private String model;// �ֻ��ͺ�
	private String release;// ϵͳ�汾
	private String province;// ʡ��
	private String city;// ����
	private String district;// ����
	private String street;// �ֵ�

	public GUser() {
	}

	public GUser(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNetworkOperatorName() {
		return networkOperatorName;
	}

	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}

	public String getSimSerialNumber() {
		return simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public String getNetworkCountryIso() {
		return networkCountryIso;
	}

	public void setNetworkCountryIso(String networkCountryIso) {
		this.networkCountryIso = networkCountryIso;
	}

	public String getNetworkOperator() {
		return networkOperator;
	}

	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
