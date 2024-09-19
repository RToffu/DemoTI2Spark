package model;

public class Produto {
//--> ATRIBUTOS
	private int id;
	private String device_name;
	private String device_category;
	private int qnt;
//--> CONSTRUCTOR'S
	public Produto () {
		this.id = 0;
		this.device_name = "null";
		this.device_category = "null";
		this.qnt = 0;
		}
	public Produto(int id) {
		this.id = id;
		this.device_name = "null";
		this.device_category = "null";
		this.qnt = 0;
	}
	public Produto(int id, String device_name, String device_category, int qnt) {
		super();
		this.id = id;
		this.device_name = device_name;
		this.device_category = device_category;
		this.qnt = qnt;
	}
//--> GETTERS AND SETTERS
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_category() {
		return device_category;
	}
	public void setDevice_category(String device_category) {
		this.device_category = device_category;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
//--> TO STRING
	@Override
	public String toString() {
		return "Usuario [ID=" + id + ", Nome aparelho=" + device_name + ", Categoria aparelho=" + device_category + ", Quantidade=" + qnt + "]";
	}	
	
}
