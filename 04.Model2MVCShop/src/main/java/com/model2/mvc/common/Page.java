package com.model2.mvc.common;

public class Page {
	
	///Field
	private int currentPage;	// 현재페이지
	private int totalCount;		// 총 게시물 수
	private int pageUnit;		// 하단 페이지 번호 화면에 보여지는 수 ==> 5
	private int pageSize;		// 한 페이지당 보여지는 게시물수      ==> 3
	private int maxPage;		// 최대 페이지 번호(전체 페이지)
	private int beginUnitPage;	// 화면에 보여지는 페이지 번호의 최소수
	private int endUnitPage;	// 화면에 보여지는 페이지 번호의 최대수
	
	///Constructor
	public Page() {
	}
	//생성자를 통해 필드값 초기화
	public Page( int currentPage, int totalCount,	int pageUnit, int pageSize ) {
		this.totalCount = totalCount;
		this.pageUnit   = pageUnit; //5
		this.pageSize   = pageSize; //3
		
		//1. 게시물 수가 0일 경우     ==> maxPage(마지막 페이지번호) = totalCount(총 게시물 수)
		//2. 게시물 수가 0이 아닐 경우 ==> maxPage(마지막 페이지번호) = ((totalCount-1)/3) + 1
		this.maxPage 	 = (pageSize == 0) ? totalCount :  (totalCount-1)/pageSize +1;
		
		//1. 현재페이지가 마지막페이지번호 보다 클 경우  ==> currentPage(현재페이지) = maxPage(마지막페이지번호)
		//2. 현재페이지가 마지막페이지번호 보다 작을 경우 ==> currentPage(현재페이지) = currentPage(현재페이지) 
		this.currentPage = (currentPage > maxPage) ? maxPage : currentPage;
		
		//화면에 보여지는 페이지 번호의 최소수를 초기화
		this.beginUnitPage = ( (currentPage-1) / pageUnit ) * pageUnit +1 ;
		
		//화면에 보여지는 페이지 번호의 최대수를 초기화
		//1. 마지막 페이지번호가 5보다 작거나 같을 경우 ==> endUnitPage = maxPage
		//2. 마지막 페이지번호가 5보다 클 경우        ==> endUnitPage = beginUnitPage + 4
		//2. 그리고 마지막 페이지번호가 endUnitPage보다 작거나 같을 경우 ==> endUnitPage = 마지막 페이지번호
		if( maxPage <= pageUnit ){
			this.endUnitPage = maxPage;
		}else{
			this.endUnitPage = beginUnitPage + (pageUnit -1);
			if( maxPage <= endUnitPage){
				this.endUnitPage = maxPage;
			}
		}
	}
	
	///Mehtod
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getBeginUnitPage() {
		return beginUnitPage;
	}
	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}
	public int getEndUnitPage() {
		return endUnitPage;
	}
	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", totalCount="
				+ totalCount + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", maxPage=" + maxPage + ", beginUnitPage="
				+ beginUnitPage + ", endUnitPage=" + endUnitPage + "]";
	}
}