package me.dionclei.url_shortener.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public class GenericPage<T> {
	
	private List<T> content;
	private Boolean hasNext;
	private Integer size;
	private Integer totalPages;
	
	public GenericPage(Page<T> page) {
		this.content = page.getContent();
		this.hasNext = page.hasNext();
		this.size = page.getSize();
		this.totalPages = page.getTotalPages();
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
