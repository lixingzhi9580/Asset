package com.cn.hnust.pojo;


/**
 * 
 * null
 * 
 **/
public class CompXmlTable {

	/****/
	private Integer ID;

	/****/
	private String FILE;

	/****/
	private String FLG;

	/****/
	private String MSG;

	/****/
	private String TABLESLIST;

	/****/
	private Integer NUM;

	/****/
	private String SQLTXT;

	/****/
	private String SQLTXT1;

	/****/
	private String SQLTXT2;



	public void setID(Integer ID){
		this.ID = ID;
	}

	public Integer getID(){
		return this.ID;
	}

	public void setFILE(String FILE){
		this.FILE = FILE;
	}

	public String getFILE(){
		return this.FILE;
	}

	public void setFLG(String FLG){
		this.FLG = FLG;
	}

	public String getFLG(){
		return this.FLG;
	}

	public void setMSG(String MSG){
		this.MSG = MSG;
	}

	public String getMSG(){
		return this.MSG;
	}

	public void setTABLESLIST(String TABLESLIST){
		this.TABLESLIST = TABLESLIST;
	}

	public String getTABLESLIST(){
		return this.TABLESLIST;
	}

	public void setNUM(Integer NUM){
		this.NUM = NUM;
	}

	public Integer getNUM(){
		return this.NUM;
	}

	public void setSQLTXT(String SQLTXT){
		this.SQLTXT = SQLTXT;
	}

	public String getSQLTXT(){
		return this.SQLTXT;
	}

	public void setSQLTXT1(String SQLTXT1){
		this.SQLTXT1 = SQLTXT1;
	}

	public String getSQLTXT1(){
		return this.SQLTXT1;
	}

	public void setSQLTXT2(String SQLTXT2){
		this.SQLTXT2 = SQLTXT2;
	}

	public String getSQLTXT2(){
		return this.SQLTXT2;
	}

	@Override
	public String toString() {
		return "CompXmlTable [ID=" + ID + ", FILE=" + FILE + ", FLG=" + FLG + ", MSG=" + MSG + ", TABLESLIST="
				+ TABLESLIST + ", NUM=" + NUM + ", SQLTXT=" + SQLTXT + ", SQLTXT1=" + SQLTXT1 + ", SQLTXT2=" + SQLTXT2
				+ "]";
	}

}
