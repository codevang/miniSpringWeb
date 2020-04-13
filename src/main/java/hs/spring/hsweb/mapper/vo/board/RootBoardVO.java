package hs.spring.hsweb.mapper.vo.board;

import java.util.Date;

public class RootBoardVO {

	private int bOrder;
	private int bId;
	private int bParent;
	private int bTableName;
	private int bBoardName;
	private Date bDate;

	public RootBoardVO(int bOrder, int bId, int bParent, int bTableName, int bBoardName,
			Date bDate) {
		super();
		this.bOrder = bOrder;
		this.bId = bId;
		this.bParent = bParent;
		this.bTableName = bTableName;
		this.bBoardName = bBoardName;
		this.bDate = bDate;
	}

	public RootBoardVO() {
	}

	public int getbOrder() {
		return bOrder;
	}

	public void setbOrder(int bOrder) {
		this.bOrder = bOrder;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public int getbParent() {
		return bParent;
	}

	public void setbParent(int bParent) {
		this.bParent = bParent;
	}

	public int getbTableName() {
		return bTableName;
	}

	public void setbTableName(int bTableName) {
		this.bTableName = bTableName;
	}

	public int getbBoardName() {
		return bBoardName;
	}

	public void setbBoardName(int bBoardName) {
		this.bBoardName = bBoardName;
	}

	public Date getbDate() {
		return bDate;
	}

	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}
}