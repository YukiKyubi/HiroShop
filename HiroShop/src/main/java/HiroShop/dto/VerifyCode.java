package HiroShop.dto;

public class VerifyCode {

	private String code;
	private String username;

	public VerifyCode() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
