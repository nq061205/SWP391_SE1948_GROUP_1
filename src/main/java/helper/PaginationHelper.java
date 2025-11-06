package helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for pagination logic
 */
public class PaginationHelper<T> {
    
    // Default values
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MIN_PAGE_SIZE = 5;
    public static final int MAX_PAGE_SIZE = 100;
    
    private List<T> fullList;
    private int currentPage;
    private int pageSize;
    private int totalItems;
    private int totalPages;
    
    public PaginationHelper(List<T> fullList, int currentPage, int pageSize) {
        this.fullList = (fullList != null) ? fullList : new ArrayList<>();
        this.totalItems = this.fullList.size();
        this.pageSize = validatePageSize(pageSize);
        this.totalPages = calculateTotalPages();
        this.currentPage = validateCurrentPage(currentPage);
    }
    
    /**
     * Validate and normalize page size
     */
    private int validatePageSize(int pageSize) {
        if (pageSize < MIN_PAGE_SIZE) return MIN_PAGE_SIZE;
        if (pageSize > MAX_PAGE_SIZE) return MAX_PAGE_SIZE;
        return pageSize;
    }
    
    /**
     * Calculate total pages based on total items and page size
     */
    private int calculateTotalPages() {
        if (totalItems == 0) return 1;
        return (int) Math.ceil((double) totalItems / pageSize);
    }
    
    /**
     * Validate current page number
     */
    private int validateCurrentPage(int page) {
        if (page < 1) return 1;
        if (page > totalPages) return totalPages;
        return page;
    }
    
    /**
     * Get paginated sublist
     */
    public List<T> getPageData() {
        if (fullList.isEmpty()) {
            return new ArrayList<>();
        }
        
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);
        
        return fullList.subList(startIndex, endIndex);
    }
    
    /**
     * Get start index for display (1-based)
     */
    public int getStartIndex() {
        return totalItems > 0 ? (currentPage - 1) * pageSize + 1 : 0;
    }
    
    /**
     * Get end index for display
     */
    public int getEndIndex() {
        return Math.min((currentPage - 1) * pageSize + getPageData().size(), totalItems);
    }
    
    // Getters
    public int getCurrentPage() {
        return currentPage;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public int getTotalItems() {
        return totalItems;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public boolean hasPrevious() {
        return currentPage > 1;
    }
    
    public boolean hasNext() {
        return currentPage < totalPages;
    }
    
    public boolean isEmpty() {
        return totalItems == 0;
    }
}
