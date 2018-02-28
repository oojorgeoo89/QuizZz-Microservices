package jorge.rv.quizzz.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestPage<T> extends PageImpl<T> {

	private static final long serialVersionUID = -2558682610336069367L;
	
	private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean previousPage;
    private boolean first;
    private boolean nextPage;
    private boolean last;
    private List<T> content;
    private Sort sort;

    public RestPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RestPage(List<T> content) {
        super(content);
    }
    
    public RestPage() {
        super(new ArrayList<T>());
    }
    
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public int getNumberOfElements() {
		return numberOfElements;
	}
	
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	
	public boolean isPreviousPage() {
		return previousPage;
	}
	
	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}
	
	public boolean isFirst() {
		return first;
	}
	
	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public boolean isNextPage() {
		return nextPage;
	}
	
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	
	public boolean isLast() {
		return last;
	}
	
	public void setLast(boolean last) {
		this.last = last;
	}
	
	public List<T> getContent() {
		return content;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public Sort getSort() {
		return sort;
	}
	
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	  
}