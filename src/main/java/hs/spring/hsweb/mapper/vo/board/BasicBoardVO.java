package hs.spring.hsweb.mapper.vo.board;

import java.text.SimpleDateFormat;
import java.util.Date;

/* 게시판-게시물 정보 VO */
public class BasicBoardVO {

	private int bId;
	private int bGroup;
	private int bOrder;
	private int bIndent;
	private String bTitle;
	private String bContent;
	private String bUserId;
	private String bUsername;
	private Date bDate;
	private int bHit;
	private int bGood;
	private int bHate;

	public BasicBoardVO(int bId, int bGroup, int bOrder, int bIndent, String bTitle,
			String bContent, String bUserId, String bUsername, Date bDate, int bGood, int bHate) {
		super();
		this.bId = bId;
		this.bGroup = bGroup;
		this.bOrder = bOrder;
		this.bIndent = bIndent;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bUserId = bUserId;
		this.bUsername = bUsername;
		this.bDate = bDate;
		this.bGood = bGood;
		this.bHate = bHate;
	}

	public BasicBoardVO() {

	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public int getbGroup() {
		return bGroup;
	}

	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}

	public int getbOrder() {
		return bOrder;
	}

	public void setbOrder(int bOrder) {
		this.bOrder = bOrder;
	}

	public int getbIndent() {
		return bIndent;
	}

	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public String getbUserId() {
		return bUserId;
	}

	public void setbUserId(String bUserId) {
		this.bUserId = bUserId;
	}

	public String getbUsername() {
		return bUsername;
	}

	public void setbUsername(String bUsername) {
		this.bUsername = bUsername;
	}

	public String getbDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		return sdf.format(bDate);
	}

	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}

	public int getbHit() {
		return bHit;
	}

	public void setbHit(int bHit) {
		this.bHit = bHit;
	}

	public int getbGood() {
		return bGood;
	}

	public void setbGood(int bGood) {
		this.bGood = bGood;
	}

	public int getbHate() {
		return bHate;
	}

	public void setbHate(int bHate) {
		this.bHate = bHate;
	}

}