package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewsDaoImpl extends ReadWriteDaoImpl<Review,Long> implements ReviewDao {
}
