package dto;

public class Product {
	private String prcode;		//상품코드
	private String prname;		//상품이름
	private int prprice;		//상품가격
	private int prstock;        //상품재고
	private String prcom;		//상품회사
	private String prtype;      //상품종류
	public String getPrcode() {
		return prcode;
	}
	public void setPrcode(String prcode) {
		this.prcode = prcode;
	}
	public String getPrname() {
		return prname;
	}
	public void setPrname(String prname) {
		this.prname = prname;
	}
	public int getPrprice() {
		return prprice;
	}
	public void setPrprice(int prprice) {
		if(prprice < 0) {
			prprice = 0;
		}
		this.prprice = prprice;
	}
	public int getPrstock() {
		return prstock;
	}
	public void setPrstock(int prstock) {
		if(prstock < 0) {
			prstock = 0;
		}
		this.prstock = prstock;
	}
	public String getPrcom() {
		return prcom;
	}
	public void setPrcom(String prcom) {
		this.prcom = prcom;
	}
	public String getPrtype() {
		return prtype;
	}
	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}
	@Override
	public String toString() {
		return "Product [prcode=" + prcode + ", prname=" + prname + ", prprice=" + prprice + ", prstock=" + prstock
				+ ", prcom=" + prcom + ", prtype=" + prtype + "]";
	}
}
