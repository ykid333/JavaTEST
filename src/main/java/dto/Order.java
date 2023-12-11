package dto;

public class Order {
	private String odcode;
	private String mid;
	private String prcode;
	private int odcount;
	private String oddate;
	public String getOdcode() {
		return odcode;
	}
	public void setOdcode(String odcode) {
		this.odcode = odcode;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPrcode() {
		return prcode;
	}
	public void setPrcode(String prcode) {
		this.prcode = prcode;
	}
	public int getOdcount() {
		return odcount;
	}
	public void setOdcount(int odcount) {
		this.odcount = odcount;
	}
	public String getOddate() {
		return oddate;
	}
	public void setOddate(String oddate) {
		this.oddate = oddate;
	}
	@Override
	public String toString() {
		return "Order [odcode=" + odcode + ", mid=" + mid + ", prcode=" + prcode + ", odcount=" + odcount + ", oddate="
				+ oddate + "]";
	}

}
