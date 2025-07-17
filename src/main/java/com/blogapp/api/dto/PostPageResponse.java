package com.blogapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPageResponse {
    private List<PostResponse> content;  // List of posts on the current page
    private int pageNo;  // Current page number
    private int pageSize;  // Number of posts per page
    private long totalElements;  // Total number of posts
    private int totalPages;  // Total number of pages
    private boolean last;  // Indicates if this is the last page

    /*
    * Explanation:
List<PostDto> content: This holds the actual list of blog posts returned on that page.

pageNo, pageSize: Pagination metadata about the current page.

totalElements, totalPages: For UI use (e.g., to create page buttons).

last: Indicates if this is the last page.
    */

}
