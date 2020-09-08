package com.ryan.dynamicsql.mapper;

import com.ryan.dynamicsql.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface BlogMapper {

    int addBlog(Blog blog);

    List<Blog> getAll();

    /**
     * 模糊查询
     *
     * @param text
     * @return
     */
    List<Blog> fuzzyQuery(@Param("name") String name, @Param("text") String text);


    List<Blog> fuzzyQueryPro(@Param("name") String name, @Param("text") String text, @Param("views") int views);

    List<Blog> chooseQuery(@Param("name") String name, @Param("text") String text, @Param("views") int views);

    int update(@Param("id") String id, @Param("name") String name, @Param("text") String text);


    int bulkAdd(@Param("blog") List<Blog> blog);
}
