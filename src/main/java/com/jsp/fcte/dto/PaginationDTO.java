package com.jsp.fcte.dto;

import java.util.List;

public class PaginationDTO<T> {
	
    private List<T> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int itemsPerPage;
    
    // Constructor
    public PaginationDTO(List<T> data, int totalItems, int totalPages, int currentPage, int itemsPerPage) {
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
    }

    // Getters and Setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

}
